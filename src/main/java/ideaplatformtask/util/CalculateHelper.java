package ideaplatformtask.util;

import ideaplatformtask.model.Resultes;
import ideaplatformtask.model.Ticket;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;

import java.time.Duration;
import java.util.OptionalDouble;

public final class CalculateHelper {

    private CalculateHelper() {}

    public static void avgFlights(Resultes resultes, String from, String to) {
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
        Duration duration = Duration.ofSeconds((long) average.getAsDouble());
        System.out.println("Average time between city " + from + " and " + to + " -> : " + duration);
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
        System.out.println("90 Procentil time flight between city " + from + " and " + to + " -> : "  + duration);
    }


}
