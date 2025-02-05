package pt.pa.pattern.command;

import pt.pa.model.Route;

import java.util.Objects;

/**
 * Command do disable a route
 */
public class EnableRouteCommand implements Command {
    private Route route;

    /**
     * Class constructor
     * @param route to operate
     */
    public EnableRouteCommand(Route route) {
        this.route = Objects.requireNonNull(route);
    }

    @Override
    public void execute() {
        route.enable();
    }

    @Override
    public void unExecute() {
        route.disable();
    }

    @Override
    public String toString() {
        return "Ativou a rota " + route;
    }
}
