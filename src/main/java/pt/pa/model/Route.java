package pt.pa.model;

import java.util.*;

/**
 * Represents a route in the context of the problem (can be done by many transports)
 *
 * @author Pedro Marteleira (20230334@estudantes.ips.pt)
 */
public class Route {
    private Stop start, destination;
    private Map<String, TransportInformation> transports;
    private Set<String> disabledTransports;
    private boolean isActive;

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
        this.disabledTransports = new HashSet<>();
        isActive = true;
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

    /**
     * Returns the map with the Pair -> transport and the information
     * @return Map [transport, information]
     */
    public Map<String, TransportInformation> getTransportsWithInformation() {
        return transports;
    }

    /**
     * Removes the given transport from the route
     * @param transport to remove
     */
    public void removeTransport(String transport) {
        transports.remove(transport);
    }

    /**
     * Returns only the transports allowed in this route
     * @return only the transports allowed in this route
     */
    public Collection<String> getAllowedTransports() {
        return isActive() ?
                getAvailableTransports().stream().filter(t -> !getDisabledTransports().contains(t)).toList()
                : new ArrayList<>();
    }

    /**
     * Disables the given transport in this route
     * @param transport to disable
     */
    public void disableTransport(String transport) {
        disabledTransports.add(transport);
    }

    /**
     * Enables the given transport in this route
     * @param transport to enable
     */
    public void enableTransport(String transport) {
        disabledTransports.remove(transport);
    }

    /**
     * Returns the transports that are disabled
     * @return transports that are disabled
     */
    public Collection<String> getDisabledTransports() {
        return disabledTransports;
    }

    /**
     * Disables this route (all transports)
     */
    public void disable() {
        isActive = false;
    }

    /**
     * Enables this route
     */
    public void enable() {
        this.isActive = true;
    }

    /**
     * Returns If the route is active
     * @return true = active, false = disabled
     */
    public boolean isActive() {
        return isActive;
    }

    @Override
    public String toString() {
        return getStart() + " ↔ " + getDestination();
    }
}
