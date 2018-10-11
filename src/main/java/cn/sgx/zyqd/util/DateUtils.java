package cn.sgx.zyqd.util;

import java.text.SimpleDateFormat;

/**
 * 重要说明
 * 此处书写方法时 请调用 LocalDateTimeUtils类中的方法 或 直接在LocalDateTimeUtils类中实现
 * 本类只做类型转换和格式化
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static final String DATE_SHORT_FORMAT = "yyyyMMdd";
    public static final String DATE_TIME_SHORT_FORMAT = "yyyyMMddHHmm";
    public static final String DATE_TIMESTAMP_SHORT_FORMAT = "yyyyMMddHHmmss";
    public static final String DATE_TIMESTAMP_LONG_FORMAT = "yyyyMMddHHmmssS";
    public static final String DATE_CH_FORMAT = "yyyy年MM月dd日";

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String TIME_SHORT_FORMAT = "HHmmss";

    public static final String DAYTIME_START = "00:00:00";
    public static final String DAYTIME_END = "23:59:59";

    private DateUtils() {
    }

    private static final String[] FORMATS = {
            "yyyy-MM-dd",
            "yyyy-MM-dd HH:mm",
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd HH:mm:ss.S",
            "HH:mm",
            "HH:mm:ss",
            "yyyy-MM",
            "yyyyMMddHHmmss",
            "yyyy-MM-dd'T'HH:mm:ss",
            "yyyy-MM-dd'T'HH:mm:ss.SSS",
            "yyyy-MM-dd'T'HH:mm:ss.SSSSSS",
            "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS",
            "yyyy-MM-dd'T'HH:mm:ss.SSSz"
    };

    /**
     * 对象 转指定格式 String日期
     *
     * @param date
     * @param format
     * @return
     */
    public static String getFormatDateTime(Object date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }
}
