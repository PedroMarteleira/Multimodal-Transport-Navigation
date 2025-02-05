package pt.pa.pattern.command;

import pt.pa.model.Route;

import java.util.Objects;

/**
 * Command do disable a route
 */
public class EnableTransportOnRouteCommand implements Command {
    private Route route;
    private String transport;

    /**
     * Class constructor
     * @param route to operate
     * @param transport to operate with
     */
    public EnableTransportOnRouteCommand(Route route, String transport) {
        this.route = Objects.requireNonNull(route);
        this.transport = Objects.requireNonNull(transport);
    }

    @Override
    public void execute() {
        route.enableTransport(transport);
    }

    @Override
    public void unExecute() {
        route.disableTransport(transport);
    }

    @Override
    public String toString() {
        return "Ativou o transporte \"" + transport + "\" na rota " + route;
    }
}