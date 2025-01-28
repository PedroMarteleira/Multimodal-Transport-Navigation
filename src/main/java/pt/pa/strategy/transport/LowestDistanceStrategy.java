package pt.pa.strategy.transport;

import pt.pa.model.Route;
import pt.pa.model.TransportInformation;

import java.util.Objects;

public class LowestDistanceStrategy implements  TransportStrategy {
    @Override
    public double getWeight(Route route) {
        return Objects.requireNonNull(route).getTransportInformation(getTransport(route)).getDistance();
    }

    @Override
    public String getTransport(Route route) {
        return route.getAvailableTransports().stream().min((a, b) -> {
            final TransportInformation ta = route.getTransportInformation(a);
            final TransportInformation tb = route.getTransportInformation(b);
            return (int) (ta.getDistance() * FACTOR - tb.getDistance() * FACTOR);
        }).orElseThrow();
    }
}
