package kz.baribir.birkitap.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    private TimeUtil() {}
    public static Date format2date(String str_date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return simpleDateFormat.parse(str_date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String currentDayDate()
    {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static String timestamp_sec_to_str(long timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timestamp * 1000);
        return simpleDateFormat.format(date);
    }

    public static Date timestamp_sec_to_date(long timestamp) {
        return new Date(timestamp * 1000);
    }

    public static String timeid() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        return simpleDateFormat.format(new Date());
    }

    public static String dateFormat(String format, Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

        return simpleDateFormat.format(date);
    }
}
