package pt.pa.model;

import pt.pa.exception.ErrorMessages;

import java.security.InvalidParameterException;
import java.util.Objects;

/**
 * Contains the information about the transport in a route
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public class TransportInformation {
    private static double HOUR_MINS = 60.0;

    private double cost, distance, duration;

    /**
     * Class constructor
     */
    public TransportInformation() {
        this(0.0, 0.0, 0.0);
    }

    /**
     * Class copy constructor
     * @param transportInformation source to copy from
     */
    public TransportInformation(TransportInformation transportInformation) {
        this(transportInformation.getCost(), transportInformation.getDistance(), transportInformation.getDuration());
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

    /**
     * Returns the cost (Environmental impact)
     * @return cost
     */
    public double getCost() {
        return cost;
    }

    /**
     * Returns the distance (In meters)
     * @return distance
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Returns the duration (In minutes)
     * @return duration
     */
    public double getDuration() {
        return duration;
    }

    /**
     * Returns a TransportInformation with the sum of the fields of both (java doesn't have operator overloading)
     * @param transportInformation other
     * @return TransportInformation with the sum of the fields from this and the given transportInformation
     */
    public TransportInformation sum(TransportInformation transportInformation) {
        Objects.requireNonNull(transportInformation);
        return new TransportInformation(
            getCost() + transportInformation.getCost(),
            getDistance() + transportInformation.getDistance(),
            getDuration() + transportInformation.getDuration()
        );
    }

    /**
     * Formats the cost value to a String
     * @param cost value
     * @return string with the format NNN.N
     */
    public static String formatCost(double cost) {
        return String.format("%.1f", cost);
    }

    /**
     * Formats the distance value to a String
     * @param distance value
     * @return string with the format NNN.Nkm
     */
    public static String formatDistance(double distance) {
        return String.format("%.1fkm", distance);
    }

    /**
     * Formats the duration value to a String
     * @param duration value
     * @return string with the format NNh NNm
     */
    public static String formatDuration(double duration) {
        return String.format("%dh %dm", (int)(duration / HOUR_MINS), Math.round(duration % HOUR_MINS));
    }
}
