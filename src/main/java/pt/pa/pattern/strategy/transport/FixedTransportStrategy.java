package pt.pa.pattern.strategy.transport;

import pt.pa.exception.MissingTransportException;
import pt.pa.model.Route;

import java.util.Objects;

/**
 * Strategy that only chooses the specified transport
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public class FixedTransportStrategy implements TransportStrategy {
    private String transport;

    /**
     * Class constructor
     * @param transport the only allowed transport
     */
    public FixedTransportStrategy(String transport) {
        this.transport = Objects.requireNonNull(transport);
    }

    @Override
    public double getWeight(Route route) throws MissingTransportException {
        if(!route.getAllowedTransports().contains(transport))
            throw new MissingTransportException();
        return route.getTransportInformation(getTransport(route)).getDistance();
    }

    @Override
    public String getTransport(Route route) throws MissingTransportException  {
        return transport;
    }
}
