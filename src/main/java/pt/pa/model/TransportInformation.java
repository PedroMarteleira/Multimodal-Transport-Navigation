package pt.pa.model;

import pt.pa.exception.ErrorMessages;

import java.security.InvalidParameterException;

/**
 * Contains the information about the transport in a route
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public class TransportInformation {
    private double cost, distance, duration;

    /**
     * Class constructor
     */
    public TransportInformation() {
        this(0.0, 0.0, 0.0);
    }

    /**
     * Class constructor
     * @param cost environmental impact
     * @param distance distance between stops
     * @param duration duration in minutes
     */
    public TransportInformation(double cost, double distance, double duration) {
        setCost(cost);
        setDistance(distance);
        setDuration(duration);
    }

    /**
     * Sets the environmental impact
     * @param cost environmental impact
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Sets the distance between the stops
     * @param distance between the stops
     */
    public void setDistance(double distance) {
        if(distance < 0.0)
            throw new InvalidParameterException(ErrorMessages.NEGATIVE_DISTANCE.toString());
        this.distance = distance;
    }

    /**
     * Sets the duration in minutes
     * @param duration in minutes
     */
    public void setDuration(double duration) {
        if(duration < 0.0)
            throw new InvalidParameterException(ErrorMessages.NEGATIVE_DURATION.toString());
        this.duration = duration;
    }

    public double getCost() {
        return cost;
    }

    public double getDistance() {
        return distance;
    }

    public double getDuration() {
        return duration;
    }
}
