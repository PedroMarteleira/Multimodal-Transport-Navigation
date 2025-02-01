package pt.pa.model;
import pt.pa.pattern.command.ActionsManager;
import pt.pa.pattern.command.AddStopCommand;
import pt.pa.pattern.command.Command;
import pt.pa.pattern.command.RemoveStopCommand;
import pt.pa.pattern.factory.TransportSelectionFactory;
import pt.pa.view.MainViewInterface;

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
        this.actionsManager.undo();
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
}
