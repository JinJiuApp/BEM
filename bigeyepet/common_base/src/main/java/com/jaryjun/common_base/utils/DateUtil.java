/*
 *
 * Copyright 2015 TedXiong xiong-wei@hotmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jaryjun.common_base.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    public static final String TIMEZONE_CN = "Asia/Shanghai";
    /**
     * ********************SIMPLEFORMATTYPE对应的字串*********************
     */
    /**
     * SIMPLEFORMATTYPESTRING1 对应类型：yyyyMMddHHmmss
     */
    public final static String SIMPLEFORMATTYPESTRING1 = "yyyyMMddHHmmss";

    /**
     * SIMPLEFORMATTYPESTRING2 对应的类型：yyyy-MM-dd HH:mm:ss
     */
    public final static String SIMPLEFORMATTYPESTRING2 = "yyyy-MM-dd HH:mm:ss";

    /**
     * SIMPLEFORMATTYPESTRING3 对应的类型：yyyy-M-d HH:mm:ss
     */
    public final static String SIMPLEFORMATTYPESTRING3 = "yyyy-M-d HH:mm:ss";

    /**
     * SIMPLEFORMATTYPESTRING4对应的类型：yyyy-MM-dd HH:mm
     */
    public final static String SIMPLEFORMATTYPESTRING4 = "yyyy-MM-dd HH:mm";

    /**
     * SIMPLEFORMATTYPESTRING5 对应的类型：yyyy-M-d HH:mm
     */
    public final static String SIMPLEFORMATTYPESTRING5 = "yyyy-M-d HH:mm";

    /**
     * SIMPLEFORMATTYPESTRING6对应的类型：yyyyMMdd
     */
    public final static String SIMPLEFORMATTYPESTRING6 = "yyyyMMdd";

    /**
     * SIMPLEFORMATTYPESTRING7对应的类型：yyyy-MM-dd
     */
    public final static String SIMPLEFORMATTYPESTRING7 = "yyyy-MM-dd";

    /**
     * SIMPLEFORMATTYPESTRING8对应的类型： yyyy-M-d
     */
    public final static String SIMPLEFORMATTYPESTRING8 = "yyyy-M-d";

    /**
     * SIMPLEFORMATTYPESTRING9对应的类型：yyyy年MM月dd日
     */
    public final static String SIMPLEFORMATTYPESTRING9 = "yyyy年MM月dd日";

    /**
     * SIMPLEFORMATTYPESTRING10对应的类型：yyyy年M月d日
     */
    public final static String SIMPLEFORMATTYPESTRING10 = "yyyy年M月d日";

    /**
     * SIMPLEFORMATTYPESTRING11对应的类型：M月d日
     */
    public final static String SIMPLEFORMATTYPESTRING11 = "M月d日";

    /**
     * SIMPLEFORMATTYPESTRING12对应的类型：HH:mm:ss
     */
    public final static String SIMPLEFORMATTYPESTRING12 = "HH:mm:ss";

    /**
     * SIMPLEFORMATTYPESTRING13对应的类型：HH:mm
     */
    public final static String SIMPLEFORMATTYPESTRING13 = "HH:mm";
    /**
     * SIMPLEFORMATTYPESTRING14对应的类型：yyyy-MM-dd
     */
    public final static String SIMPLEFORMATTYPESTRING14 = "yyyy/MM/dd";
    /**
     * SIMPLEFORMATTYPESTRING15对应的类型：MM/dd
     */
    public final static String SIMPLEFORMATTYPESTRING15 = "MM/dd";
    /**
     * SIMPLEFORMATTYPESTRING16对应的类型：MM月dd日
     */
    public final static String SIMPLEFORMATTYPESTRING16 = "MM月dd日";
    /**
     * SIMPLEFORMATTYPESTRING17对应的类型：MM月dd日 HH:mm
     */
    public final static String SIMPLEFORMATTYPESTRING17 = "MM月dd日 HH:mm";
    /**
     * SIMPLEFORMATTYPESTRING18对应的类型：yyyy年MM月dd日 HH:mm
     */
    public final static String SIMPLEFORMATTYPESTRING18 = "yyyy年MM月dd日 HH:mm";

    /**
     * SIMPLEFORMATTYPESTRING7对应的类型： yy-MM-dd
     */
    public final static String SIMPLEFORMATTYPESTRING19 = "MM-dd";

    /**
     * 将时间转换为simpleFormatString对应的格式输出
     *
     * @param calendar
     * @param simpleFormatString
     * @return
     */
    public static String getCalendarStrBySimpleDateFormat(Calendar calendar, String simpleFormatString) {
        String str = "";
        if (!TextUtils.isEmpty(simpleFormatString) && calendar != null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat(simpleFormatString);
            dateFormat.setTimeZone(TimeZone.getTimeZone(TIMEZONE_CN));
            str = (dateFormat).format(calendar.getTime());
        }
        return str;
    }

    /**
     * 将时间转换为simpleFormatString对应的格式输出
     *
     * @param time
     * @param simpleFormatString
     * @return
     */
    public static String getCalendarStrBySimpleDateFormat(Long time, String simpleFormatString) {
        Date date = new Date(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat(simpleFormatString);
        return dateFormat.format(date);
    }

    /**
     * 将时间字串#yyyyMMddHHmmss转为 Calendar
     *
     * @param dateStr 小于8位时返回null，不足14位补0
     * @return
     */
    @SuppressWarnings("ResourceType")
    public static Calendar getCalendarByDateTimeStr(String dateStr) {
        if (TextUtils.isEmpty(dateStr) || dateStr.length() < 8)
            return null;
        while (dateStr.length() < 14) {
            dateStr += "0";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(TIMEZONE_CN));
        int year = Integer.parseInt(dateStr.substring(0, 4));
        int month = Integer.parseInt(dateStr.substring(4, 6));
        int day = Integer.parseInt(dateStr.substring(6, 8));
        int hour = Integer.parseInt(dateStr.substring(8, 10));
        int min = Integer.parseInt(dateStr.substring(10, 12));
        int second = 0;
        if (dateStr.length() >= 14)
            second = Integer.parseInt(dateStr.substring(12, 14));
        calendar.set(year, month - 1, day, hour, min, second);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * 获取手机系统日期
     *
     * @return
     */
    public static Calendar getCalendarTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(TIMEZONE_CN));
        return calendar;
    }

    /**
     * 获取服务器系统时间
     *
     * @return
     */
    public static Calendar getCurrentTime() {
        return DateUtil.getCalendarTime();
    }

    private final static long toDay = 1000 * 60 * 60 * 24; // 将毫秒数转换为天
    private final static long toHour = 1000 * 60 * 60; // 将毫秒数转换为小时
    private final static long toMinute = 1000 * 60; // 将毫秒数转换为分钟

    /**
     * 返回两个日期的小时差，异常返回-1；
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long caculateHour(Date date1, Date date2) {

        long result = -1;
        if (date1 != null && date2 != null) {
            long maxDay = date1.getTime() / toHour;
            long minDay = date2.getTime() / toHour;
            result = maxDay > minDay ? maxDay - minDay : minDay - maxDay;
        }
        return result;

    }


    /**
     * 返回两个日期的天数差，同一天返回0
     *
     * @param fromDate
     * @param desDate
     * @return fromDate - desDate
     */
    public static long caculateDay(Date fromDate, Date desDate) {
        try {
            System.out.println("fromDate:" + fromDate.toString());
            System.out.println("desDate:" + desDate.toString());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date from = format.parse(format.format(fromDate));
            Date dest = format.parse(format.format(desDate));
            System.out.println("from:" + from.toString());
            System.out.println("dest:" + dest.toString());
            System.out.println("from.getTime() - dest.getTime():" + (from.getTime() - dest.getTime()));
            System.out.println("from.getTime() - dest.getTime()) / toDay:" + ((from.getTime() - dest.getTime()) / toDay));
            return (from.getTime() - dest.getTime()) / toDay;
        } catch (Exception e) {
            return Long.MAX_VALUE;
        }
    }

    /**
     * 返回目标日期同今天的天数差;
     *
     * @param desDate
     * @return [今天]-desDate
     */
    public static long caculateDayFromToday(Date desDate) {
        return caculateDay(getCurrentTime().getTime(), desDate);
    }


    /**
     * ryan
     * 只有日期
     * D日 0点0分0秒 到 23点59分59秒 ： 今天
     * D - 1+日 0点0分0秒 到 23点59分59秒 ： 昨天
     * D - 2+日 0点0分0秒 到 23点59分59秒 ： yy-MM-dd
     * D + 1+日 0点0分0秒 到 23点59分59秒 ： 明天
     * D + 2+日 0点0分0秒 到 23点59分59秒 ： yy-MM-dd
     */
    public static String displayDate(Date date) {
        StringBuilder dateFormart = new StringBuilder();
        if (date != null) {
            int day = (int) caculateDayFromToday(date); // 天
            switch (day) {
                case -1:
                    dateFormart.append("明天");
                    break;
                case 0:
                    dateFormart.append("今天");
                    break;
                case 1:
                    dateFormart.append("昨天");
                    break;
                default:
                    SimpleDateFormat sdf = new SimpleDateFormat(SIMPLEFORMATTYPESTRING11);
                    dateFormart.append(sdf.format(date));
                    break;
            }
        }
        return dateFormart.toString();
    }


}
