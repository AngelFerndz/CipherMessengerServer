package server.tools;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AdvancedClock {
    
    public static int getRawMinutes(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("mm");
        LocalDateTime now = LocalDateTime.now();
        String minutes = dtf.format(now);
        return Integer.parseInt(minutes);
    }
    
    public static int getRawHours(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH");
        LocalDateTime now = LocalDateTime.now();
        String minutes = dtf.format(now);
        return Integer.parseInt(minutes);
    }
    
}
