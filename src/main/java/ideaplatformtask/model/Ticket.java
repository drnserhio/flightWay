package ideaplatformtask.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Data
public class Ticket implements Serializable {

    private String origin;
    private String origin_name;
    private String destination;
    private String destination_name;
    private String departure_date;
    private String departure_time;
    private String arrival_date;
    private String arrival_time;
    private String carrier;
    private int stops;
    private long price;

    public Duration getDuration() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yy H:mm");
        LocalDateTime dep = LocalDateTime.parse(departure_date + " " + departure_time, dateTimeFormatter);
        LocalDateTime arrvl = LocalDateTime.parse(arrival_date + " " + arrival_time, dateTimeFormatter);
        return Duration.between(dep, arrvl);
    }

    public Duration getTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("H:mm");
        LocalTime dep = LocalTime.parse(departure_time, dateTimeFormatter);
        LocalTime arrvl = LocalTime.parse(arrival_time, dateTimeFormatter);
        return Duration.between(dep, arrvl);

    }
}
