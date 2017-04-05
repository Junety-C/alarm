package cn.junety.alarm.base.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by caijt on 2017/1/28.
 */
public class DateUtil {

    public static final SimpleDateFormat YYYYMMDD = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat MM_DD_HH_MM = new SimpleDateFormat("MM-dd HH:mm");

    /**
     * 通过指定的Format生成日期字符串，如果日期为空的话，则返回空串
     */
    public static String formatDate(SimpleDateFormat format, Date date) {
        if (date == null) {
            return null;
        }
        return format.format(date);
    }
}
