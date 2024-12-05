package com.lyflexi.genv1.utils;

/**
 * @Description:
 * @Author: lyflexi
 * @project: gen-receipt-number
 * @Date: 2024/12/5 14:00
 */

import com.lyflexi.genv1.enums.DateStyleEnum;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 日期工具类
 *
 * @since 1.0.0
 */
public class DateUtil {

    /**
     * 日期格式
     */
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 日期格式
     */
    public static final String YYYYMMDD = "yyyyMMdd";

    /**
     * 日期格式
     */
    public static final String YYYY_MM_DD_HH_MM_SS = YYYY_MM_DD + " HH:mm:ss";

    /**
     * 日期格式，到小时
     */
    public static final String YYYYMMDDHH = "yyyyMMddHH";

    /**
     * 一小时的毫秒数
     */
    public static final long ONE_HOUR_MILLIS = 60 * 60 * 1000;

    /**
     * 获取SimpleDateFormat
     *
     * @param parttern
     *            日期格式
     * @return SimpleDateFormat对象
     * @throws RuntimeException
     *             异常：非法日期格式
     */
    public static SimpleDateFormat getDateFormat(String parttern) throws RuntimeException {
        return new SimpleDateFormat(parttern);
    }

    /**
     * 获取日期中的某数值。如获取月份
     *
     * @param date
     *            日期
     * @param dateType
     *            日期格式
     * @return 数值
     */
    public static int getInteger(Date date, int dateType) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(dateType);
    }

    /**
     * 增加日期中某类型的某数值。如增加日期
     *
     * @param date
     *            日期字符串
     * @param dateType
     *            类型
     * @param amount
     *            数值
     * @return 计算后日期字符串
     */
    public static String addInteger(String date, int dateType, int amount) {
        String dateString = null;
        DateStyleEnum dateStyleEnum = getDateStyle(date);
        if (dateStyleEnum != null) {
            Date myDate = stringToDate(date, dateStyleEnum);
            myDate = addInteger(myDate, dateType, amount);
            dateString = dateToString(myDate, dateStyleEnum);
        }
        return dateString;
    }

    /**
     * 增加日期中某类型的某数值。如增加日期
     *
     * @param date
     *            日期
     * @param dateType
     *            类型
     * @param amount
     *            数值
     * @return 计算后日期
     */
    public static Date addInteger(Date date, int dateType, int amount) {
        Date myDate = null;
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(dateType, amount);
            myDate = calendar.getTime();
        }
        return myDate;
    }

    /**
     * 获取精确的日期
     *
     * @param timestamps
     *            时间long集合
     * @return 日期
     */
    public static Date getAccurateDate(List<Long> timestamps) {
        Date date = null;
        long timestamp = 0;
        Map<Long, long[]> map = new HashMap<Long, long[]>(512);
        List<Long> absoluteValues = new ArrayList<Long>();

        if (timestamps != null && timestamps.size() > 0) {
            if (timestamps.size() > 1) {
                for (int i = 0; i < timestamps.size(); i++) {
                    for (int j = i + 1; j < timestamps.size(); j++) {
                        long absoluteValue = Math.abs(timestamps.get(i) - timestamps.get(j));
                        absoluteValues.add(absoluteValue);
                        long[] timestampTmp = { timestamps.get(i), timestamps.get(j) };
                        map.put(absoluteValue, timestampTmp);
                    }
                }

                long minAbsoluteValue = -1;
                if (!absoluteValues.isEmpty()) {
                    minAbsoluteValue = absoluteValues.get(0);
                }
                for (int i = 0; i < absoluteValues.size(); i++) {
                    for (int j = i + 1; j < absoluteValues.size(); j++) {
                        if (absoluteValues.get(i) > absoluteValues.get(j)) {
                            minAbsoluteValue = absoluteValues.get(j);
                        } else {
                            minAbsoluteValue = absoluteValues.get(i);
                        }
                    }
                }

                if (minAbsoluteValue != -1) {
                    long[] timestampsLastTmp = map.get(minAbsoluteValue);
                    if (absoluteValues.size() > 1) {
                        timestamp = Math.max(timestampsLastTmp[0], timestampsLastTmp[1]);
                    } else if (absoluteValues.size() == 1) {
                        long dateOne = timestampsLastTmp[0];
                        long dateTwo = timestampsLastTmp[1];
                        long max = 100000000000L;
                        if ((Math.abs(dateOne - dateTwo)) < max) {
                            timestamp = Math.max(timestampsLastTmp[0], timestampsLastTmp[1]);
                        } else {
                            long now = System.currentTimeMillis();
                            if (Math.abs(dateOne - now) <= Math.abs(dateTwo - now)) {
                                timestamp = dateOne;
                            } else {
                                timestamp = dateTwo;
                            }
                        }
                    }
                }
            } else {
                timestamp = timestamps.get(0);
            }
        }

        if (timestamp != 0) {
            date = new Date(timestamp);
        }
        return date;
    }

    /**
     * 判断字符串是否为日期字符串
     *
     * @param date
     *            日期字符串
     * @return true or false
     */
    public static boolean isDate(String date) {
        boolean isDate = false;
        if (date != null) {
            if (stringToDate(date) != null) {
                isDate = true;
            }
        }
        return isDate;
    }

    /**
     * 获取日期字符串的日期风格。失敗返回null。
     *
     * @param date
     *            日期字符串
     * @return 日期风格
     */
    public static DateStyleEnum getDateStyle(String date) {
        DateStyleEnum dateStyleEnum = null;
        Map<Long, DateStyleEnum> map = new HashMap<Long, DateStyleEnum>(512);
        List<Long> timestamps = new ArrayList<Long>();
        for (DateStyleEnum style : DateStyleEnum.values()) {
            Date dateTmp = stringToDate(date, style.getValue());
            if (dateTmp != null) {
                timestamps.add(dateTmp.getTime());
                map.put(dateTmp.getTime(), style);
            }
        }
        dateStyleEnum = map.get(getAccurateDate(timestamps).getTime());
        return dateStyleEnum;
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     *
     * @param date
     *            日期字符串
     * @return 日期
     */
    public static Date stringToDate(String date) {
        DateStyleEnum dateStyleEnum = null;
        return stringToDate(date, dateStyleEnum);
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     *
     * @param date
     *            日期字符串
     * @param parttern
     *            日期格式
     * @return 日期
     */
    public static Date stringToDate(String date, String parttern) {
        Date myDate = null;
        if (date != null) {
            try {
                myDate = getDateFormat(parttern).parse(date);
            } catch (Exception e) {
            }
        }
        return myDate;
    }

    /**
     * 将日期字符串转化为日期。失败返回null。
     *
     * @param date
     *            日期字符串
     * @param dateStyleEnum
     *            日期风格
     * @return 日期
     */
    public static Date stringToDate(String date, DateStyleEnum dateStyleEnum) {
        Date myDate = null;
        if (dateStyleEnum == null) {
            List<Long> timestamps = new ArrayList<Long>();
            for (DateStyleEnum style : DateStyleEnum.values()) {
                Date dateTmp = stringToDate(date, style.getValue());
                if (dateTmp != null) {
                    timestamps.add(dateTmp.getTime());
                }
            }
            myDate = getAccurateDate(timestamps);
        } else {
            myDate = stringToDate(date, dateStyleEnum.getValue());
        }
        return myDate;
    }

    /**
     * 将日期转化为日期字符串。失败返回null。
     *
     * @param date
     *            日期
     * @param parttern
     *            日期格式
     * @return 日期字符串
     */
    public static String dateToString(Date date, String parttern) {
        String dateString = null;
        if (date != null) {
            try {
                dateString = getDateFormat(parttern).format(date);
            } catch (Exception e) {
            }
        }
        return dateString;
    }

    /**
     * 将日期转化为日期字符串。失败返回null。
     *
     * @param date
     *            日期
     * @param dateStyleEnum
     *            日期风格
     * @return 日期字符串
     */
    public static String dateToString(Date date, DateStyleEnum dateStyleEnum) {
        String dateString = null;
        if (dateStyleEnum != null) {
            dateString = dateToString(date, dateStyleEnum.getValue());
        }
        return dateString;
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date
     *            旧日期字符串
     * @param parttern
     *            新日期格式
     * @return 新日期字符串
     */
    public static String stringToString(String date, String parttern) {
        return stringToString(date, null, parttern);
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date
     *            旧日期字符串
     * @param dateStyleEnum
     *            新日期风格
     * @return 新日期字符串
     */
    public static String stringToString(String date, DateStyleEnum dateStyleEnum) {
        return stringToString(date, null, dateStyleEnum);
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date
     *            旧日期字符串
     * @param olddParttern
     *            旧日期格式
     * @param newParttern
     *            新日期格式
     * @return 新日期字符串
     */
    public static String stringToString(String date, String olddParttern, String newParttern) {
        String dateString = null;
        if (olddParttern == null) {
            DateStyleEnum style = getDateStyle(date);
            if (style != null) {
                Date myDate = stringToDate(date, style.getValue());
                dateString = dateToString(myDate, newParttern);
            }
        } else {
            Date myDate = stringToDate(date, olddParttern);
            dateString = dateToString(myDate, newParttern);
        }
        return dateString;
    }

    /**
     * 将日期字符串转化为另一日期字符串。失败返回null。
     *
     * @param date
     *            旧日期字符串
     * @param olddDteStyle
     *            旧日期风格
     * @param newDateStyleEnum
     *            新日期风格
     * @return 新日期字符串
     */
    public static String stringToString(String date, DateStyleEnum olddDteStyle, DateStyleEnum newDateStyleEnum) {
        String dateString = null;
        if (olddDteStyle == null) {
            DateStyleEnum style = getDateStyle(date);
            dateString = stringToString(date, style.getValue(), newDateStyleEnum.getValue());
        } else {
            dateString = stringToString(date, olddDteStyle.getValue(), newDateStyleEnum.getValue());
        }
        return dateString;
    }

    /**
     * 增加日期的年份。失败返回null。
     *
     * @param date
     *            日期
     * @param yearAmount
     *            增加数量。可为负数
     * @return 增加年份后的日期字符串
     */
    public static String addYear(String date, int yearAmount) {
        return addInteger(date, Calendar.YEAR, yearAmount);
    }

    /**
     * 增加日期的年份。失败返回null。
     *
     * @param date
     *            日期
     * @param yearAmount
     *            增加数量。可为负数
     * @return 增加年份后的日期
     */
    public static Date addYear(Date date, int yearAmount) {
        return addInteger(date, Calendar.YEAR, yearAmount);
    }

    /**
     * 增加日期的月份。失败返回null。
     *
     * @param date
     *            日期
     * @param yearAmount
     *            增加数量。可为负数
     * @return 增加月份后的日期字符串
     */
    public static String addMonth(String date, int yearAmount) {
        return addInteger(date, Calendar.MONTH, yearAmount);
    }

    /**
     * 增加日期的月份。失败返回null。
     *
     * @param date
     *            日期
     * @param yearAmount
     *            增加数量。可为负数
     * @return 增加月份后的日期
     */
    public static Date addMonth(Date date, int yearAmount) {
        return addInteger(date, Calendar.MONTH, yearAmount);
    }

    /**
     * 增加日期的天数。失败返回null。
     *
     * @param date
     *            日期字符串
     * @param dayAmount
     *            增加数量。可为负数
     * @return 增加天数后的日期字符串
     */
    public static String addDay(String date, int dayAmount) {
        return addInteger(date, Calendar.DATE, dayAmount);
    }

    /**
     * 增加日期的天数。失败返回null。
     *
     * @param date
     *            日期
     * @param dayAmount
     *            增加数量。可为负数
     * @return 增加天数后的日期
     */
    public static Date addDay(Date date, int dayAmount) {
        return addInteger(date, Calendar.DATE, dayAmount);
    }

    /**
     * 增加日期的小时。失败返回null。
     *
     * @param date
     *            日期字符串 dayAmount 增加数量。可为负数
     * @return 增加小时后的日期字符串
     */
    public static String addHour(String date, int hourAmount) {
        return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
    }

    /**
     * 增加日期的小时。失败返回null。
     *
     * @param date
     *            日期 dayAmount 增加数量。可为负数
     * @return 增加小时后的日期
     */
    public static Date addHour(Date date, int hourAmount) {
        return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
    }

    /**
     * 增加日期的分钟。失败返回null。
     *
     * @param date
     *            日期字符串 dayAmount 增加数量。可为负数
     * @return 增加分钟后的日期字符串
     */
    public static String addMinute(String date, int hourAmount) {
        return addInteger(date, Calendar.MINUTE, hourAmount);
    }

    /**
     * 增加日期的分钟。失败返回null。
     *
     * @param date
     *            日期 dayAmount 增加数量。可为负数
     * @return 增加分钟后的日期
     */
    public static Date addMinute(Date date, int hourAmount) {
        return addInteger(date, Calendar.MINUTE, hourAmount);
    }

    /**
     * 增加日期的秒钟。失败返回null。
     *
     * @param date
     *            日期字符串 dayAmount 增加数量。可为负数
     * @return 增加秒钟后的日期字符串
     */
    public static String addSecond(String date, int hourAmount) {
        return addInteger(date, Calendar.SECOND, hourAmount);
    }

    /**
     * 增加日期的秒钟。失败返回null。
     *
     * @param date
     *            日期 dayAmount 增加数量。可为负数
     * @return 增加秒钟后的日期
     */
    public static Date addSecond(Date date, int hourAmount) {
        return addInteger(date, Calendar.SECOND, hourAmount);
    }

    /**
     * 获取日期的年份。失败返回0。
     *
     * @param date
     *            日期字符串
     * @return 年份
     */
    public static int getYear(String date) {
        return getYear(stringToDate(date));
    }

    /**
     * 获取日期的年份。失败返回0。
     *
     * @param date
     *            日期
     * @return 年份
     */
    public static int getYear(Date date) {
        return getInteger(date, Calendar.YEAR);
    }

    /**
     * 获取日期的月份。失败返回0。
     *
     * @param date
     *            日期字符串
     * @return 月份
     */
    public static int getMonth(String date) {
        return getMonth(stringToDate(date));
    }

    /**
     * 获取日期的月份。失败返回0。
     *
     * @param date
     *            日期
     * @return 月份
     */
    public static int getMonth(Date date) {
        return getInteger(date, Calendar.MONTH);
    }

    /**
     * 获取日期的天数。失败返回0。
     *
     * @param date
     *            日期字符串
     * @return 天
     */
    public static int getDay(String date) {
        return getDay(stringToDate(date));
    }

    /**
     * 获取日期的天数。失败返回0。
     *
     * @param date
     *            日期
     * @return 天
     */
    public static int getDay(Date date) {
        return getInteger(date, Calendar.DATE);
    }

    /**
     * 获取日期的小时。失败返回0。
     *
     * @param date
     *            日期字符串
     * @return 小时
     */
    public static int getHour(String date) {
        return getHour(stringToDate(date));
    }

    /**
     * 获取日期的小时。失败返回0。
     *
     * @param date
     *            日期
     * @return 小时
     */
    public static int getHour(Date date) {
        return getInteger(date, Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取日期的分钟。失败返回0。
     *
     * @param date
     *            日期字符串
     * @return 分钟
     */
    public static int getMinute(String date) {
        return getMinute(stringToDate(date));
    }

    /**
     * 获取日期的分钟。失败返回0。
     *
     * @param date
     *            日期
     * @return 分钟
     */
    public static int getMinute(Date date) {
        return getInteger(date, Calendar.MINUTE);
    }

    /**
     * 获取日期的秒钟。失败返回0。
     *
     * @param date
     *            日期字符串
     * @return 秒钟
     */
    public static int getSecond(String date) {
        return getSecond(stringToDate(date));
    }

    /**
     * 获取日期的秒钟。失败返回0。
     *
     * @param date
     *            日期
     * @return 秒钟
     */
    public static int getSecond(Date date) {
        return getInteger(date, Calendar.SECOND);
    }

    /**
     * 获取日期 。默认yyyy-MM-dd格式。失败返回null。
     *
     * @param date
     *            日期字符串
     * @return 日期
     */
    public static String getDate(String date) {
        return stringToString(date, DateStyleEnum.YYYY_MM_DD);
    }

    /**
     * 获取日期。默认yyyy-MM-dd格式。失败返回null。
     *
     * @param date
     *            日期
     * @return 日期
     */
    public static String getDate(Date date) {
        return dateToString(date, DateStyleEnum.YYYY_MM_DD);
    }

    /**
     * 获取日期。默认yyyy年MM月dd日格式。失败返回null。
     * @param date
     * @return
     */
    public static String getDateFormat(Date date) {
        return dateToString(date, DateStyleEnum.YYYY_MM_DD_CC);
    }

    /**
     * 获取日期。默认yyyy-MM-dd HH:mm:ss格式。失败返回null。
     *
     * @param date
     *            日期
     * @return 日期
     */
    public static String getDateTime(Date date) {
        return dateToString(date, DateStyleEnum.YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取往年年份。默认yyyy格式。失败返回null。
     *
     * @param date
     * @param num
     * @return
     */
    public static Integer getDateYear(Date date, Integer num) {

        return Integer.parseInt(dateToString(date, DateStyleEnum.YYYY)) + num;
    }

    /**
     * 获取日期的时间。默认HH:mm:ss格式。失败返回null。
     *
     * @param date
     *            日期字符串
     * @return 时间
     */
    public static String getTime(String date) {
        return stringToString(date, DateStyleEnum.HH_MM_SS);
    }

    /**
     * 获取日期的时间。默认HH:mm:ss格式。失败返回null。
     *
     * @param date
     *            日期
     * @return 时间
     */
    public static String getTime(Date date) {
        return dateToString(date, DateStyleEnum.HH_MM_SS);
    }





    /**
     * 获取两个日期相差的天数
     *
     * @param date
     *            日期字符串
     * @param otherDate
     *            另一个日期字符串
     * @return 相差天数
     */
    public static int getIntervalDays(String date, String otherDate) {
        return getIntervalDays(stringToDate(date), stringToDate(otherDate));
    }

    /**
     * @param date
     *            日期
     * @param otherDate
     *            另一个日期
     * @return 相差天数
     */
    public static int getIntervalDays(Date date, Date otherDate) {
        date = DateUtil.stringToDate(DateUtil.getDate(date));
        long time = Math.abs(date.getTime() - otherDate.getTime());
        return (int) (time / (24 * 60 * 60 * 1000));
    }

    /**
     * @param
     * @return nameEnShort
     */
    public static Date parse(String dateStr) {
        return parse(dateStr, YYYY_MM_DD, null);
    }

    /**
     * @param
     * @return nameEnShort
     */
    public static Date parse(String dateStr, String pattern) {
        return parse(dateStr, pattern, null);
    }

    /**
     * @param
     * @return nameEnShort
     */
    public static Date parse(String dateStr, String pattern, Date defaultValue) {
        if (dateStr == null || "".equals(dateStr)) {
            return defaultValue;
        }

        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            return defaultValue;
        }
    }

    /**
     * @param
     * @return nameEnShort
     */
    public static String format(Date date) {
        return format(date, YYYY_MM_DD);
    }

    /**
     * @param
     * @return nameEnShort
     */
    public static String format(Date date, String pattern) {
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return format.format(date);
        } else {
            return null;
        }
    }

    /**
     * @param
     * @return nameEnShort
     */
    public static Date addDay1(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, day);

        return c.getTime();
    }

    /**
     * @param date
     * @return nameEnShort
     */
    public static Date addMonth1(Date date, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, month);

        return c.getTime();
    }

    /**
     * 获取昨天的日期
     *
     * @return Date 返回类型
     * @Title getYesterday
     */
    public static Date getYesterday() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return date;
    }

    /**
     * 获取昨天的日期
     *
     * @return Date 返回类型
     * @Title getYesterday
     */
    public static Date getSevenDaysAgo() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        date = calendar.getTime();
        return date;
    }

    /**
     * 获取n天以前的日期
     *
     * @param day
     * @return
     */
    public static Date getDaysAgo(int day) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1 * day);
        date = calendar.getTime();
        return date;
    }

    /**
     * 带服务器时区的时间日期
     *
     * @return
     */
    public static Date getLocalDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }

    /**
     * @param date1
     * @param date2
     * @Description: 比较两个时间的大小
     * Date: 2020-02-22 10:12:10
     */
    public static boolean compareDate(Date date1, Date date2) {
        if (date1.before(date2)) {
            return false;
        } else if (date1.after(date2)) {
            return true;
        } else {
            return true;
        }
    }

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime 当前时间
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return

     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime() || nowTime.getTime() == endTime.getTime()) {
            return true;
        }
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获得当前日期 yyyy-MM-dd HH:mm:ss
     *
     * @return 2019-11-19 14:12:40
     */
    public static Date getDateCurrentTime(String str) {
        // 小写的hh取得12小时，大写的HH取的是24小时
        SimpleDateFormat df = new SimpleDateFormat(str);
        Date date = new Date();
        try {
            date = df.parse(df.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 把时间格式化成long类型
     * @param time
     * @return
     */
    public static Long getLongTimestamp(Date time) {
        Long longTime  = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String newTime = sdf.format(time);
            longTime = sdf.parse(newTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return longTime;
    }

    /**
     * 获取明天零点零分零秒时间戳
     * @param date
     * @return
     */
    public static Date geNextDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }

    /**
     * 获取当周一零点零分零秒时间戳
     * @param date
     * @return
     */
    public static Date getThisWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    /**
     * 获取下周一零点零分零秒时间戳
     * @param date
     * @return
     */
    public static Date getNextWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeek(date));
        cal.add(Calendar.DATE, 7);
        return cal.getTime();
    }

    /**
     * 获取下个月1号零点零分零秒时间戳
     * @param date
     * @return
     */
    public static Date getNextMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MONDAY, cal.get(Calendar.MONDAY) + 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取下一年1月1号零点零分零秒时间戳
     * @param date
     * @return
     */
    public static Date getNextYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 1);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static LocalDateTime convertLocalDateTime(String timeStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);
        return LocalDateTime.parse(timeStr, formatter);
    }

    public static String convertLdtStr(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYY_MM_DD_HH_MM_SS);
        return localDateTime.format(formatter);
    }

    public static LocalDateTime convertTimestampStrToLocalDateTime(String timestampStr) {
        long timestamp = Long.parseLong(timestampStr);
        Instant instant = Instant.ofEpochMilli(timestamp);
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static String nowStr () {
        return convertLdtStr(LocalDateTime.now());
    }
}
