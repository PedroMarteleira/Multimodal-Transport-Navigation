package pt.pa.pattern.command;

import pt.pa.model.Route;

import java.util.Objects;

/**
 * Command do disable a route
 */
public class DisableRouteCommand implements Command {
    private Route route;

    /**
     * Class constructor
     * @param route to operate
     */
    public DisableRouteCommand(Route route) {
        this.route = Objects.requireNonNull(route);
    }

    @Override
    public void execute() {
        route.disable();
    }

    @Override
    public void unExecute() {
        route.enable();
    }

    @Override
    public String toString() {
        return "Desativou a rota " + route;
    }
}
