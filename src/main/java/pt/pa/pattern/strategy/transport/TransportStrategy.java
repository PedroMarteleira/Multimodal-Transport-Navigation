package pt.pa.pattern.strategy.transport;

import pt.pa.exception.MissingTransportException;
import pt.pa.model.Route;

/**
 * Strategy used to select the best transport from a route
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public interface TransportStrategy {
    static final double FACTOR = 1000.0; //Keep decimal numbers using the comparator

    /**
     * Returns the value of the field (should multiply with the factor before comparing)
     * @param route Route to extract from
     * @return value of the best field os the strategy
     */
    double getWeight(Route route) throws MissingTransportException;

    /**
     * Returns the transport with the best score of the strategy
     * @param route Route to extract from
     * @return the transport with the best score of the strategy
     */
    String getTransport(Route route) throws MissingTransportException;
}
