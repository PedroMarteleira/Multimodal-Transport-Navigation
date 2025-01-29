package pt.pa.pattern.strategy.transport;

import pt.pa.exception.MissingTransportException;
import pt.pa.model.Route;
import pt.pa.model.TransportInformation;

import java.util.Collection;
import java.util.Objects;

/**
 * Strategy that chooses the transport with the lowest Distance in the available ones
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public class LowestDistanceStrategy implements  TransportStrategy {
    private Collection<String> availableTransports;

    /**
     * Class constructor
     * @param availableTransports transports available/allowed by the user
     */
    public LowestDistanceStrategy(Collection<String> availableTransports) {
        this.availableTransports = Objects.requireNonNull(availableTransports);
    }

    @Override
    public double getWeight(Route route) throws MissingTransportException {
        return Objects.requireNonNull(route).getTransportInformation(getTransport(route)).getDistance();
    }

    @Override
    public String getTransport(Route route) throws MissingTransportException {
        return route.getAvailableTransports().stream().filter(availableTransports::contains).min((a, b) -> {
            final TransportInformation ta = route.getTransportInformation(a);
            final TransportInformation tb = route.getTransportInformation(b);
            return (int) (ta.getDistance() * FACTOR - tb.getDistance() * FACTOR);
        }).orElseThrow(MissingTransportException::new);
    }
}
