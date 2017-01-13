package io.vicp.frlib.micromsg.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.SocketAddress;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * 流工具类
 * Created by zhoudr on 2016/12/29.
 */
public class IOUtil {

    private static final Logger logger = LoggerFactory.getLogger(IOUtil.class);

    public static void main(String[] args) {
        Set<URL> urls = new HashSet<>();
        try {
            urls.add(new URL("http://www.baidu.com/"));
            urls.add(new URL("http://www.iwjw.com/"));
        } catch (MalformedURLException e) {
            logger.error("", e);
        }
        resolveWebPagesUseSelector(urls);

    }

    /**
     * 使用选择器处理文件（多路套接字复用）
     * @param urls
     */
    public static void resolveWebPagesUseSelector(Set<URL> urls) {
        try {
            Map<SocketAddress, String> map = urlToSocketAddress(urls);
            Selector selector = Selector.open();
            for (SocketAddress socketAddress : map.keySet()) {
                register(selector, socketAddress);
            }
            int finished = 0;
            int total = map.size();
            ByteBuffer byteBuffer = ByteBuffer.allocate(32 * 1024);
            int len = -1;
            while (finished < total) {
                selector.select(); // 选择准备io对应操作(connect, read)的channel
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    iter.remove();
                    if (key.isValid() && key.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        InetSocketAddress socketAddress = (InetSocketAddress) socketChannel.getRemoteAddress();
                        String fileName = socketAddress.getHostName() + ".txt";
                        FileChannel dest = FileChannel.open(Paths.get(fileName), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
                        byteBuffer.clear();
                        len = socketChannel.read(byteBuffer);
                        while (len > 0 || byteBuffer.position() != 0) {
                            byteBuffer.flip();
                            dest.write(byteBuffer);
                            byteBuffer.compact();
                            len = socketChannel.read(byteBuffer);
                        }
                        if (len == -1) { // 读取完成
                            finished++;
                            key.cancel();
                        }
                    } else if (key.isValid() && key.isConnectable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        boolean success = socketChannel.finishConnect();
                        if (!success) { // 表示连接不通
                            logger.warn("can not connect: " + socketChannel.getRemoteAddress());
                            finished++;
                            key.cancel();
                        } else {
                            InetSocketAddress address = (InetSocketAddress)socketChannel.getRemoteAddress();
                            String path = map.get(address);
                            String request = "GET "  + path + " HTTP/1.1\r\n\r\nHost: " + address.getHostString() + "\r\n\r\n";
                            logger.info(request);
                            ByteBuffer header = ByteBuffer.wrap(request.getBytes("UTF-8"));
                            socketChannel.write(header);
                        }
                    }
                }
            }
        } catch (IOException e) {
            logger.error("", e);
        }
    }

    /**
     * 注册访问地址至selector
     * @param selector
     * @param socketAddress
     * @throws IOException
     */
    private static void register(Selector selector, SocketAddress socketAddress) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false); // 使用非阻塞模式
        socketChannel.connect(socketAddress);
        socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ);
    }

    private static Map<SocketAddress, String> urlToSocketAddress(Set<URL> urls) {
        Map<SocketAddress, String> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(urls)) {
            for (URL url : urls) {
                int port = url.getPort() != -1 ? url.getPort() : url.getDefaultPort();
                SocketAddress socketAddress = new InetSocketAddress(url.getHost(), port);
                String path = url.getPath();
                if (StringUtils.isNotEmpty(url.getQuery())) {
                    path = path + "?" + url.getQuery();
                }
                map.put(socketAddress, path);
            }
        }
        return map;
    }

    /**
     * 文件复制
     * @param bytes
     * @param destFile
     */
    public static void copy(byte[] bytes, String destFile) throws IOException {
        FileChannel desc = null;
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
            desc = FileChannel.open(Paths.get(destFile), StandardOpenOption.WRITE, StandardOpenOption.CREATE);
            desc.write(byteBuffer);
            desc.force(true);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (desc != null) {
                desc.close();
            }
        }
    }
}
