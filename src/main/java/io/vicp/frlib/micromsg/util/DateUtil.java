package io.vicp.frlib.micromsg.util;

import java.util.Date;

/**
 * 时间服务类
 * Created by zhoudr on 2016/12/27.
 */
public class DateUtil {

    public static long getSecondsFromTimeToNow(Date date) {
        if (date == null) {
            return 0 ;
        }
        Date now = new Date();
        long millSeconds = now.getTime() - date.getTime();
        return millSeconds / 1000;
    }
}
