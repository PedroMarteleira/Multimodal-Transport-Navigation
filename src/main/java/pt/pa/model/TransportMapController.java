package pt.pa.model;
import javafx.stage.FileChooser;
import pt.pa.pattern.command.*;
import pt.pa.pattern.factory.TransportSelectionFactory;
import pt.pa.utils.DataSet;
import pt.pa.utils.TransportMapLoaderUtil;
import pt.pa.view.MainViewInterface;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

/**
 * TransportMap Controller (mvc)
 */
public class TransportMapController {
    private TransportMap model;
    private MainViewInterface view;

    private ActionsManager actionsManager;

    /**
     * Class Constructor
     * @param model (TransportMap)
     * @param view Any compatible view (implementing the Interface)
     */
    public TransportMapController(TransportMap model, MainViewInterface view) {
        this.model = Objects.requireNonNull(model);
        this.view = Objects.requireNonNull(view);

        this.actionsManager = new ActionsManager();
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

    /**
     * Displays the information about the selected stop
     * @param stop selected stop
     */
    public void doShowStopInformation(Stop stop) {
        Objects.requireNonNull(stop); //Explode if null
        view.showStopInformation(stop);
    }

    /**
     * Displays the information about the selected route
     * @param route selected route
     */
    public void doShowRouteInformation(Route route) {
        Objects.requireNonNull(route); //Explode if null
        view.showRouteInformation(route);
    }

    /**
     * Shows the shortest path between two stops requested from the user
     */
    public void doShowShortestPath(Stop start, Stop end, Collection<String> allowedTransports, CostField field) {
        final String messageTitle = "Erro ao procurar caminho";

        Objects.requireNonNull(allowedTransports);

        if(start == null) {
            view.displayError(messageTitle, "Selecione uma paragem de partida!");
            return;
        }

        if(end == null) {
            view.displayError(messageTitle, "Selecione uma paragem de destino!");
            return;
        }

        if(field == null) {
            view.displayError(messageTitle, "Selecione um campo para otimizar!");
            return;
        }

        model.setTransportStrategy(TransportSelectionFactory.createTransportSelection(field, allowedTransports));
        view.displayShortestPath(model.findPath(start, end));
    }

    /**
     * Adds a new stop to the map
     * @param stop stop to add
     */
    public void doAddStop(Stop stop) {
        this.actionsManager.execute(new AddStopCommand(model, stop));
    }

    /**
     * Removes the stop from the map
     * @param stop stop to remove
     */
    public void doRemoveStop(Stop stop) {
        this.actionsManager.execute(new RemoveStopCommand(model, stop));
    }

    /**
     * Undo the last supported action
     */
    public void doUndo() {
        try {
            this.actionsManager.undo();
        } catch (Exception e) {
            view.displayError("Erro ao Reverter", "Não há ações para reverter!");
        }
    }

    /**
     * Returns the history of the performed actions
     * @return performed actions
     */
    public Collection<Command> getActionHistory() {
        return actionsManager.getActionHistory();
    }

    /**
     * Returns the available transports in the model
     * @return available transports in the model
     */
    public Collection<String> getAvailableTransports() {
        return model.getAvailableTransports();
    }

    /**
     * Returns the stops stored in the model
     * @return stops stored in the model
     */
    public Collection<Stop> getStops() {
        return model.getStops().stream().sorted().toList();
    }

    /**
     * Returns the stops stored in the model added by the user
     * @return stops stored in the model that are added by the user
     */
    public Collection<Stop> getUserStops() {
        return model.getUserStops().stream().sorted().toList();
    }

    /**
     * Puts (Adds if it doesn't exist, update if exist) a route with the given parameters
     * @param stop stop to use
     * @param stop1 stop to use
     * @param transport transport to put the route
     * @param transportInformation specifications of that transport in the route
     */
    private void doPutTransportInformation(Stop stop, Stop stop1, String transport, TransportInformation transportInformation) {
        Objects.requireNonNull(stop);
        Objects.requireNonNull(stop1);
        Objects.requireNonNull(transport);
        Objects.requireNonNull(transportInformation);

        Route route = model.getRouteWithStops(stop, stop1);
        if(route == null) {
            actionsManager.execute(new InsertRouteCommand(stop, stop1, model, transport, transportInformation));
        } else {
            if (route.getAvailableTransports().contains(transport)) {
                actionsManager.execute(new UpdateRouteCommand(route, transport, transportInformation));
            } else {
                actionsManager.execute(new InsertRouteCommand(stop, stop1, model, transport, transportInformation));
            }
        }
        view.update(null);
    }

    /**
     * Puts (Adds if it doesn't exist, update if exist) a route of walk with the given parameters
     * @param stop stop to use
     * @param stop1 stop to use
     * @param transportInformation specifications of that transport in the route
     */
    public void doPutWalkTransportInformation(Stop stop, Stop stop1, TransportInformation transportInformation) {
        this.doPutTransportInformation(stop, stop1, "walk", transportInformation);
    }

    /**
     * Returns the information of a walking route in a given transport
     * @param stop stop to find
     * @param stop1 stop to find
     * @return information of a walking route in a given transport, null if it doesn't exist
     */
    public TransportInformation getInformationOfWalkingRoute(Stop stop, Stop stop1) {
        final Route route = model.getRouteWithStops(stop, stop1);
        if(route == null) return null;
        return route.getTransportInformation("walk");
    }

    /**
     * Loads the dataSet from the user
     */
    public void doLoadDataSet() {
        DataSet dataSet = view.requestDataSet();
        if(dataSet != null) {
            try {
                TransportMapLoaderUtil loader = new TransportMapLoaderUtil(dataSet);
                actionsManager.reset();
                model.replaceWith(loader.getLoadedTransportMap());
            } catch (Exception e) {
                view.displayError("Erro ao carregar dataset", "Formato de ficheiro invalido!");
            }
        }
    }
}
