package pt.pa.view;

import pt.pa.model.Path;
import pt.pa.model.Stop;

import java.util.Collection;

/**
 * This interface provides the required methods to interact with the controller
 * Very usefull if needed to change the View
 */
public interface MainViewInterface {
    /**
     * Display the given stops on the map with a menu
     * @param stops to mark
     */
    public void displayMarkedStops(Collection<Stop> stops);

    /**
     * Displays the provided path to the user
     * @param path to show
     */
    public void displayPath(Path path);

    /**
     * Displays the shortest path to the user
     * Note: if you want to use the same menu, just call the other function
     * @param path to show
     */
    public void displayShortestPath(Path path);

    /**
     * Displays the information about the provided stop
     * @param stop to show info
     */
    public void showStopInformation(Stop stop);

    /**
     * Shows an error to the user
     * @param title message title
     * @param caption message body
     */
    public void displayError(String title, String caption);
}
