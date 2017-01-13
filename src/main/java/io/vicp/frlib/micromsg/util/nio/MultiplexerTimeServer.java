package io.vicp.frlib.micromsg.util.nio;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by zhoudr on 2017/1/13.
 */
public class MultiplexerTimeServer implements Runnable{

    /**
     * 选择器
     */
    private Selector selector;

    /**
     * 服务端channel
     */
    private ServerSocketChannel serverSocketChannel;

    /**
     * 程序结束标志
     */
    private volatile boolean stop;

    /**
     * 初始化多路复用器、绑定监听端口
     * @param port
     */
    public MultiplexerTimeServer(int port) {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false); // 非阻塞模式
            serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024); // 设置backlog，最大等待连接数
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);  // 注册channel至选择器并且监听SelectionKey.OP_ACCEPT操作位
            System.out.println("The time server is start in port : " + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * 停止运行服务
     */
    public void stop() {
        this.stop = true;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                // selector.select()方法的阻塞唤醒方式：有socket事件，超时或者selector.warkup()中断发生
                // selector.select()方法，只有在收到已经选择的channel或者被中断，或者超出时间时才返回
                int selectedCount = selector.select(1000);//相当于Object.wait(1000)
                if (selectedCount > 0) {
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
                    SelectionKey selectionKey = null;
                    while (keyIterator.hasNext()) {
                        selectionKey = keyIterator.next();
                        keyIterator.remove();
                        try {
                            handleInput(selectionKey);
                        } catch (Exception e) {
                            // 从选择器上取消注册该key下的channel
                            if (selectionKey != null) {
                                selectionKey.cancel();
                                SelectableChannel channel = selectionKey.channel();
                                if ( channel != null) {
                                    channel.close();
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 多路复用器关闭后，所有注册在上面的channel和pipe等资源都会被自动去注册关闭，所以不需要重复释放资源
            if (selector != null) {
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 处理新接入的请求消息
     * @param selectionKey
     * @throws IOException
     */
    private void handleInput(SelectionKey selectionKey) throws IOException{
        if (selectionKey.isValid()) {
            // 首先判断网络事件的类型
            if (selectionKey.isAcceptable()) {
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                SocketChannel socketChannel = serverSocketChannel.accept();
                socketChannel.configureBlocking(false);
                socketChannel.register(selector, SelectionKey.OP_READ);
            }
            if (selectionKey.isReadable()) {
                // read data
                SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = socketChannel.read(readBuffer);
                if (readBytes > 0) {
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    String body = new String(bytes, "UTF-8");
                    System.out.println("The time server receive order : " + body);
                    String currentTime = "Query Time Order".equalsIgnoreCase(body) ? new Date().toString() : "Bad Order";
                    doWrite(socketChannel, currentTime);
                } else if (readBytes < 0) {
                    // 链路关闭
                    selectionKey.cancel();
                    socketChannel.close();
                } else {
                    // 读到0字节，忽略
                }
            }
        }
    }

    private void doWrite(SocketChannel socketChannel, String data) throws IOException{
        if (StringUtils.isNotEmpty(data)) {
            byte[] bytes = data.getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            socketChannel.write(writeBuffer);
        }
    }
}
