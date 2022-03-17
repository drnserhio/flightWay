package ideaplatformtask.util;

import ideaplatformtask.model.Resultes;
import ideaplatformtask.model.Ticket;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.OptionalDouble;
import java.util.concurrent.atomic.AtomicReference;

public final class CalculateHelper {

    private CalculateHelper() {
    }

    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("H:mm", Locale.UK);


    public static void avgFlights(Resultes resultes, String from, String to) throws ParseException {
        OptionalDouble average = resultes
                .getTickets()
                .stream()
                .filter(ticket -> {
                    if (ticket.getOrigin_name().equals(from) &&
                            ticket.getDestination_name().equals(to)) {
                        return true;
                    } else {
                        return ticket.getOrigin_name().equals(to) &&
                                ticket.getDestination_name().equals(from);
                    }
                }).map(Ticket::getDuration)
                .mapToDouble(Duration::getSeconds)
                .average();

        OptionalDouble averageLocalTime = resultes
                .getTickets()
                .stream()
                .filter(ticket -> {
                    if (ticket.getOrigin_name().equals(from) &&
                            ticket.getDestination_name().equals(to)) {
                        return true;
                    } else {
                        return ticket.getOrigin_name().equals(to) &&
                                ticket.getDestination_name().equals(from);
                    }
                }).map(Ticket::getTime)
                .mapToLong(Duration::getSeconds)
                .average();
        Duration duration = Duration.ofSeconds((long) average.getAsDouble());
        double asDouble = averageLocalTime.getAsDouble();
        String s = String.format("%06d", (int)asDouble);
        DateFormat format = new SimpleDateFormat("HHmmss");
        Date date = format.parse(s);
        System.out.println("Average time between city " + from + " and " + to + " -> : " + duration);
        System.out.println("Average time between city " + from + " and " + to + " -> : " + timeFormat.format(date));
    }

    public static void procentil(Resultes resultes, String from, String to, double valPercent) {
        double[] doubles = resultes
                .getTickets()
                .stream()
                .filter(ticket -> {
                    if (ticket.getOrigin_name().equals(from) &&
                            ticket.getDestination_name().equals(to)) {

                        return true;
                    } else {
                        return ticket.getOrigin_name().equals(to) &&
                                ticket.getDestination_name().equals(from);
                    }
                }).map(Ticket::getDuration).mapToDouble(Duration::getSeconds).toArray();
        Percentile percentile = new Percentile();
        percentile.setData(doubles);
        Duration duration = Duration.ofSeconds((long) percentile.evaluate(valPercent));

        double[] localtimeMiddle = resultes
                .getTickets()
                .stream()
                .filter(ticket -> {
                    if (ticket.getOrigin_name().equals(from) &&
                            ticket.getDestination_name().equals(to)) {

                        return true;
                    } else {
                        return ticket.getOrigin_name().equals(to) &&
                                ticket.getDestination_name().equals(from);
                    }
                }).map(Ticket::getTime).mapToDouble(Duration::getSeconds).toArray();

        Percentile pr = new Percentile();
        pr.setData(doubles);
        Duration dr = Duration.ofSeconds((long) pr.evaluate(valPercent));
        Date middle = new Date(dr.getSeconds());
        System.out.println("90 Procentil time flight between city " + from + " and " + to + " -> : " + duration);
        System.out.println("90 Procentil time flight between city " + from + " and " + to + " -> : " + timeFormat.format(middle));


    }


}
