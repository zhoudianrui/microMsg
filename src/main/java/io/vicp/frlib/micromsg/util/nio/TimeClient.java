package io.vicp.frlib.micromsg.util.nio;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Created by zhoudr on 2017/1/13.
 */
public class TimeClient {

    public static void main(String[] args) {
        int port = 6667;
        if (!ArrayUtils.isEmpty(args)) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }
        }
        Thread clientThread = new Thread(new TimeClientHandler("127.0.0.1", port), "TimeClient-001");
        clientThread.start();
    }
}
