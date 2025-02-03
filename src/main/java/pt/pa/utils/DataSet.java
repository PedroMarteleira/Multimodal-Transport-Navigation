package pt.pa.utils;

import java.util.Objects;

/**
 * Data Class used to store the dataset file paths
 */
public class DataSet {
    private String stopsFilePath, routesFilePath;

    /**
     * Class constructor
     * @param stopsFilePath stops file path
     * @param routesFilePath routes file path
     */
    public DataSet(String stopsFilePath, String routesFilePath) {
        this.stopsFilePath = Objects.requireNonNull(stopsFilePath);
        this.routesFilePath = Objects.requireNonNull(routesFilePath);
    }

    /**
     * Returns the routes file path
     * @return routes file path
     */
    public String getRoutesFilePath() {
        return routesFilePath;
    }

    /**
     * Returns the stops file path
     * @return stops file path
     */
    public String getStopsFilePath() {
        return stopsFilePath;
    }
}
