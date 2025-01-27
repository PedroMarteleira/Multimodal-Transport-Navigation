package pt.pa.controller;
import pt.pa.model.TransportMap;
import pt.pa.view.MainViewInterface;

import java.util.Objects;

public class TransportMapController {
    private TransportMap model;
    private MainViewInterface view;
    public TransportMapController(TransportMap model, MainViewInterface view) {
        this.model = Objects.requireNonNull(model);
        this.view = Objects.requireNonNull(view);
    }

    public void doExit() {
        System.exit(0);
    }

    public void doShowStopsWithAllTransports() {
        view.displayStopsWithAllTransports(model.getStopsWithAllTransports());
    }
}
