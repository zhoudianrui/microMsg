package io.vicp.frlib.micromsg.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.util.locale.provider.DateFormatSymbolsProviderImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间服务类
 * Created by zhoudr on 2016/12/27.
 */
public class DateUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 获取指定时间与当前时间相隔的秒数
     * @param date
     * @return
     */
    public static long getSecondsFromTimeToNow(Date date) {
        if (date == null) {
            return 0 ;
        }
        Date now = new Date();
        long millSeconds = now.getTime() - date.getTime();
        return millSeconds / 1000;
    }

    /**
     * 将日期字符串转换成指定格式的日期，默认返回当前日期
     * @param date
     * @param simpleDateFormat
     * @return
     */
    public static Date convertStringDateWithDefault(String date, SimpleDateFormat simpleDateFormat) {
        Date defaultDate = new Date();
        try {
            if (simpleDateFormat == null) {
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            }
            if (StringUtils.isBlank(date)) {
                date = simpleDateFormat.format(defaultDate);
            }
            return convertStringTimeWithDefault(date, simpleDateFormat);
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    /**
     * 将时间字符串转换成指定格式的时间，默认返回当前时间
     * @param dateTime
     * @param simpleDateFormat
     * @return
     */
    public static Date convertStringTimeWithDefault(String dateTime, SimpleDateFormat simpleDateFormat) {
        Date defaultDate = new Date();
        try {
            if (simpleDateFormat == null) {
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            }
            if (StringUtils.isBlank(dateTime)) {
                dateTime = simpleDateFormat.format(defaultDate);
            }
            return simpleDateFormat.parse(dateTime);
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    /**
     * 获取一天中最早的时间
     * @param date
     * @return
     */
    public static Date getEarlliestTime(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 设置一天当中最晚时间
     * @param date
     * @return
     */
    public static Date getLatestTime(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    public static void main(String[] args) {
        String dateString = "2016-12-29 12:00:00";
        Date date = convertStringDateWithDefault(dateString, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        System.out.println(date);
    }
}
