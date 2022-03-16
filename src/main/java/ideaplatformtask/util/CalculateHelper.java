package ideaplatformtask.util;

import ideaplatformtask.model.Resultes;
import ideaplatformtask.model.Ticket;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.OptionalDouble;
import java.util.concurrent.atomic.AtomicReference;

public final class CalculateHelper {

    private CalculateHelper() {
    }

    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("H:mm", Locale.UK);
    private static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.UK);


    public static void avgFlights(Resultes resultes, String from, String to) {
        AtomicReference<LocalTime> localDateDep = new AtomicReference<>();
        AtomicReference<LocalTime> localDateArr = new AtomicReference<>();
        AtomicReference<LocalTime> localTime = new AtomicReference<>();
        OptionalDouble average = resultes
                .getTickets()
                .stream()
                .filter(ticket -> {
                    if (ticket.getOrigin_name().equals(from) &&
                            ticket.getDestination_name().equals(to)) {
                         localDateDep.set(LocalTime.parse(ticket.getDeparture_time(), dateFormat));
                         localDateArr.set(LocalTime.parse(ticket.getArrival_time(), dateFormat));
                        localTime.set(localDateDep.get().minusNanos(localDateArr.get().getNano()));
                        return true;
                    } else {
                        localDateDep.set(LocalTime.parse(ticket.getDeparture_time(), dateFormat));
                        localDateArr.set(LocalTime.parse(ticket.getArrival_time(), dateFormat));
                        localTime.set(localDateDep.get().minusNanos(localDateArr.get().getNano()));
                        return ticket.getOrigin_name().equals(to) &&
                                ticket.getDestination_name().equals(from);
                    }
                }).map(Ticket::getDuration)
                .mapToDouble(Duration::getSeconds)
                .average();
        Duration duration = Duration.ofSeconds((long) average.getAsDouble());
        localTime.set(localDateDep.get().minusNanos(localDateArr.get().getNano()));
        System.out.println("Average time between city " + from + " and " + to + " -> : " + duration);
        System.out.println("Average time between city " + from + " and " + to + " -> : " + timeFormat.format(localTime.get()));
    }

    public static void procentil(Resultes resultes, String from, String to, double valPercent) {
        AtomicReference<LocalTime> localDateDep = new AtomicReference<>();
        AtomicReference<LocalTime> localDateArr = new AtomicReference<>();
        AtomicReference<LocalTime> localTime = new AtomicReference<>();
        double[] doubles = resultes
                .getTickets()
                .stream()
                .filter(ticket -> {
                    if (ticket.getOrigin_name().equals(from) &&
                            ticket.getDestination_name().equals(to)) {
                        localDateDep.set(LocalTime.parse(ticket.getDeparture_time(), dateFormat));
                        localDateArr.set(LocalTime.parse(ticket.getArrival_time(), dateFormat));
                        localTime.set(localDateDep.get().minusNanos(localDateArr.get().getNano()));
                        return true;
                    } else {
                        localDateDep.set(LocalTime.parse(ticket.getDeparture_time(), dateFormat));
                        localDateArr.set(LocalTime.parse(ticket.getArrival_time(), dateFormat));
                        localTime.set(localDateDep.get().minusNanos(localDateArr.get().getNano()));
                        return ticket.getOrigin_name().equals(to) &&
                                ticket.getDestination_name().equals(from);
                    }
                }).map(Ticket::getDuration).mapToDouble(Duration::getSeconds).toArray();
        Percentile percentile = new Percentile();
        percentile.setData(doubles);
        Duration duration = Duration.ofSeconds((long) percentile.evaluate(valPercent));

        System.out.println("90 Procentil time flight between city " + from + " and " + to + " -> : " + duration);
        System.out.println("90 Procentil time flight between city " + from + " and " + to + " -> : " + timeFormat.format(localTime.get()));

    }


}
