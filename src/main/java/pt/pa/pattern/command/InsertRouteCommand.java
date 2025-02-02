package pt.pa.pattern.command;

import pt.pa.model.Route;
import pt.pa.model.Stop;
import pt.pa.model.TransportInformation;
import pt.pa.model.TransportMap;

import java.util.Objects;

/**
 * Command used to insert a route of a transport
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public class InsertRouteCommand implements Command {
    private Route route;
    private String transport;
    private TransportInformation transportInfo;
    private boolean hadStop;
    private TransportMap model;

    /**
     * Class constructor
     * @param stop route stop
     * @param stop1 route stop
     * @param transport transport to use
     * @param transportInfo information to put on the route
     */
    public InsertRouteCommand(Stop stop, Stop stop1, TransportMap model, String transport, TransportInformation transportInfo) {
        this.transport = Objects.requireNonNull(transport);
        this.transportInfo = Objects.requireNonNull(transportInfo);
        this.model = Objects.requireNonNull(model);
        this.route = model.getRouteWithStops(stop, stop1);
        this.hadStop = route != null;

        if(!hadStop) {
            this.route = new Route(stop, stop1);
        }
    }

    @Override
    public void execute() {
        if(!hadStop) model.addRoute(route);
        route.putTransport(transport, transportInfo);
    }

    @Override
    public void unExecute() {
        if(!hadStop) model.removeRoute(route);
        route.removeTransport(transport);

    }

    @Override
    public String toString() {
        return String.format("Adicionou rota de %s em %s-%s", transport, route.getStart(), route.getDestination());
    }
}
