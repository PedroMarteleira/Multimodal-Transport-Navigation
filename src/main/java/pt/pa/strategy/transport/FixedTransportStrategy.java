package pt.pa.strategy.transport;

import pt.pa.exception.MissingTransportException;
import pt.pa.model.Route;

import java.util.Objects;

public class FixedTransportStrategy implements TransportStrategy {
    private String transport;

    public FixedTransportStrategy(String transport) {
        this.transport = Objects.requireNonNull(transport);
    }

    @Override
    public double getWeight(Route route) throws MissingTransportException {
        if(!route.getAvailableTransports().contains(transport))
            throw new MissingTransportException();
        return route.getTransportInformation(getTransport(route)).getDistance();
    }

    @Override
    public String getTransport(Route route) throws MissingTransportException  {
        return transport;
    }
}
