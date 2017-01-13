package io.vicp.frlib.micromsg.util.nio;

import org.apache.commons.lang.ArrayUtils;

/**
 * Created by zhoudr on 2017/1/13.
 */
public class TimeServer {

    public static void main(String[] args) {
        int port = 6666; // 默认端口
        if (!ArrayUtils.isEmpty(args)) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                // 未正确获取端口号，采用默认端口
            }
        }
        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        Thread serverThread = new Thread(timeServer, "NIO-MultiplexerTimeServer-001");
        serverThread.start();
    }
}
