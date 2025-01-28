package pt.pa.controller;
import pt.pa.model.TransportMap;
import pt.pa.view.MainViewInterface;

import java.util.Objects;

/**
 * TransportMap Controller (mvc)
 */
public class TransportMapController {
    private TransportMap model;
    private MainViewInterface view;

    /**
     * Class Constructor
     * @param model (TransportMap)
     * @param view Any compatible view (implementing the Interface)
     */
    public TransportMapController(TransportMap model, MainViewInterface view) {
        this.model = Objects.requireNonNull(model);
        this.view = Objects.requireNonNull(view);
    }

    /**
     * Closes the application
     */
    public void doExit() {
        System.exit(0);
    }

    /**
     * Displays all stops with all existing transports in the network
     */
    public void doShowStopsWithAllTransports() {
        view.displayMarkedStops(model.getStopsWithAllTransports());
    }

    /**
     * Displays the biggest path in duration using only the specified transport
     * @param transport transport to find the biggest path possible
     */
    private void displayBiggestPathOfTransport(String transport) {
        view.displayPath(model.getLongestPathOfTransport(Objects.requireNonNull(transport)));
    }

    /**
     * Displays the biggest path in duration using only the train
     */
    public void doShowBiggestPathOfTrain() {
        displayBiggestPathOfTransport("train");
    }

    /**
     * Displays the biggest path in duration using only the bus
     */
    public void doShowBiggestPathOfBus() {
        displayBiggestPathOfTransport("bus");
    }

    /**
     * Displays the biggest path in duration using only the walk
     */
    public void doShowBiggestPathOfWalk() {
        displayBiggestPathOfTransport("walk");
    }
}
