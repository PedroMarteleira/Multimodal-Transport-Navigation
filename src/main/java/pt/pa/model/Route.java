package pt.pa.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a route in the context of the problem (can be done by many transports)
 */
public class Route {
    private Stop start, destination;
    private Map<String, TransportInformation> transports;

    /**
     * Class constructor
     * Note: Start and destination doesn't change anything, paths are bidirectional
     *
     * @param start stop
     * @param destination stop
     */
    public Route(Stop start, Stop destination) {
        this.start = Objects.requireNonNull(start);
        this.destination = Objects.requireNonNull(destination);
        this.transports = new HashMap<>();
    }

    /**
     * Returns the available transports in this route
     * @return available transports in this route
     */
    public Collection<String> getAvailableTransports() {
        return transports.keySet();
    }

    /**
     * Adds/Sets the transport information
     * @param transport transport field/name/key
     * @param transportInformation data
     */
    public void putTransport(String transport, TransportInformation transportInformation) {
        Objects.requireNonNull(transport);
        Objects.requireNonNull(transportInformation);
        transports.put(transport, transportInformation);
    }

    /**
     * Returns the start stop
     * @return start stop
     */
    public Stop getStart() {
        return start;
    }

    /**
     * Returns the destination stop
     * @return destination stop
     */
    public Stop getDestination() {
        return destination;
    }

    /**
     * Returns the transport information of the given transport
     * @param transport to extract the information from
     * @return given TransportInformation
     */
    public TransportInformation getTransportInformation(String transport) {
        return transports.get(transport);
    }
}
