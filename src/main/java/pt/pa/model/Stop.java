package pt.pa.model;

import java.security.InvalidParameterException;

/**
 * Represents a stop in the context of the problem
 *
 * @author Pedro Marteleira (20230334@estudantes.ips.pt)
 */
public class Stop {
    private String code, name;
    private double latitude, longitude;

    /**
     * Class constructor
     * @param code stop code (must be unique)
     * @param name stop name (shown in the map)
     * @param latitude map latitude
     * @param longitude map longitude
     */
    public Stop(String code, String name, double latitude, double longitude) {
        if(code == null || code.isEmpty())
            throw new InvalidParameterException(ErrorMessages.INVALID_STOP_CODE.toString());

        this.code = code;
        setName(name);

        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Sets the stop name
     * @param name stop name
     */
    public void setName(String name) {
        if(name == null || name.isEmpty())
            throw new InvalidParameterException(ErrorMessages.INVALID_STOP_NAME.toString());
        this.name = name;
    }

    /**
     * Returns the stop name
     * @return stop name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the stop code
     * @return stop code
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns the stop latitude
     * @return stop latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Returns the stop longitude
     * @return stop longitude
     */
    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return getName();
    }
}
