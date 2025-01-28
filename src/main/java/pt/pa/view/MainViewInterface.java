package pt.pa.view;

import pt.pa.model.Path;
import pt.pa.model.Stop;

import java.util.Collection;

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
}
