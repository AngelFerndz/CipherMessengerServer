package server.tools;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeStampGenerator {

    public static String get() {
        String date = getDate();
        String time = getTime();
        String timeStamp = "[" + date + " " + time + "]";
        return timeStamp;
    }

    public static String getDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return formatToString(dtf);
    }

    public static String getTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        return formatToString(dtf);
    }

    public static long currentTime() {
        return System.currentTimeMillis();
    }
    
    private static String formatToString(DateTimeFormatter dtf){
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

}
