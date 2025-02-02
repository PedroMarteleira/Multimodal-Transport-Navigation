package pt.pa.pattern.command;

import pt.pa.model.Route;
import pt.pa.model.TransportInformation;

import java.util.Objects;

/**
 * Command used to update a route of a transport
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public class UpdateRouteCommand implements Command {
    private Route route;
    private String transport;
    private TransportInformation transportInfo, oldTransportInfo;

    /**
     * Class constructor
     * @param route route to apply the command
     * @param transport transport to use
     * @param transportInfo information to put on the route
     */
    public UpdateRouteCommand(Route route, String transport, TransportInformation transportInfo) {
        this.route = Objects.requireNonNull(route);
        this.transport = Objects.requireNonNull(transport);
        this.transportInfo = Objects.requireNonNull(transportInfo);
        this.oldTransportInfo = route.getTransportInformation(transport);
    }

    @Override
    public void execute() {
        route.putTransport(transport, transportInfo);
    }

    @Override
    public void unExecute() {
        route.putTransport(transport, oldTransportInfo);
    }

    @Override
    public String toString() {
        return String.format("Atualizou rota de %s em %s-%s", transport, route.getStart(), route.getDestination());
    }
}
