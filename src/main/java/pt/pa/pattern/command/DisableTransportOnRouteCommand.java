package pt.pa.pattern.command;

import pt.pa.model.Route;

import java.util.Objects;

/**
 * Command do disable a route
 */
public class DisableTransportOnRouteCommand implements Command {
    private Route route;
    private String transport;

    /**
     * Class constructor
     * @param route to operate
     * @param transport to operate with
     */
    public DisableTransportOnRouteCommand(Route route, String transport) {
        this.route = Objects.requireNonNull(route);
        this.transport = Objects.requireNonNull(transport);
    }

    @Override
    public void execute() {
        route.disableTransport(transport);
    }

    @Override
    public void unExecute() {
        route.enableTransport(transport);
    }

    @Override
    public String toString() {
        return "Desativou o transporte \"" + transport + "\" na rota " + route;
    }
}