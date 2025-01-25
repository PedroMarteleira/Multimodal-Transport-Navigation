package pt.pa.model;

import com.brunomnsilva.smartgraph.graph.Edge;
import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.GraphEdgeList;
import com.brunomnsilva.smartgraph.graph.Vertex;
import pt.pa.observer.Subject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Represents the map of transports in the context of the problem (model)
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public class TransportMap extends Subject {
    private Graph<Stop, Route> graph;
    private List<String> availableTransports;

    /**
     * Class constructor
     */
    public TransportMap() {
        super();
        graph = new GraphEdgeList<>();
        availableTransports = new ArrayList<>();
    }

    /**
     * Adds a new Stop to the map
     * @param stop to add
     */
    public void addStop(Stop stop) {
        getGraph().insertVertex(Objects.requireNonNull(stop));
        notifyObservers(null);
    }

    /**
     * Adds a new Route to the map
     * @param route to add
     */
    public void addRoute(Route route) {
        Objects.requireNonNull(route);
        graph.insertEdge(route.getStart(), route.getDestination(), route);
        notifyObservers(null);
    }

    /**
     * Sets all the Available Transports
     * @param availableTransports new value
     */
    public void setAvailableTransports(List<String> availableTransports) {
        this.availableTransports = Objects.requireNonNull(availableTransports);
    }

    /**
     * Returns the List of Available Transports
     * @return List of Available Transports
     */
    public List<String> getAvailableTransports() {
        return availableTransports;
    }

    /**
     * Returns the internal Graph
     * @return graph
     */
    public Graph<Stop, Route> getGraph() {
        return graph;
    }

    /**
     * Returns the stop with the provided code
     * @param stopCode code to search
     * @return the stop if the code exists, null otherwise
     */
    public Stop getStopWithCode(String stopCode) {
        return getStops().stream().filter(stop -> stop.getCode().equals(stopCode)).findFirst().orElse(null);
    }

    /**
     * Returns all the stops in the map
     * @return all the stops in the map
     */
    public Collection<Stop> getStops() {
        return getGraph().vertices().stream().map(Vertex::element).toList();
    }

    /**
     * Returns all the routes in the map
     * @return all the routes in the map
     */
    public Collection<Route> getRoutes() {
        return getGraph().edges().stream().map(Edge::element).toList();
    }
}
