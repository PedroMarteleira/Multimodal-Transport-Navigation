package pt.pa.model;

import com.brunomnsilva.smartgraph.graph.Edge;
import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.GraphEdgeList;
import com.brunomnsilva.smartgraph.graph.Vertex;
import pt.pa.exception.MissingTransportException;
import pt.pa.observer.Subject;
import pt.pa.strategy.path.LargestPathStrategy;
import pt.pa.strategy.path.PathStrategy;
import pt.pa.strategy.path.ShortestPathStrategy;
import pt.pa.strategy.transport.FixedTransportStrategy;
import pt.pa.strategy.transport.LowestDistanceStrategy;
import pt.pa.strategy.transport.LowestDurationStrategy;
import pt.pa.strategy.transport.TransportStrategy;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents the map of transports in the context of the problem (model)
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public class TransportMap extends Subject {
    private Graph<Stop, Route> graph;
    private List<String> availableTransports;
    private Set<Stop> userStops;

    private TransportStrategy transportStrategy;
    private PathStrategy pathStrategy;


    /**
     * Class constructor
     */
    public TransportMap() {
        super();
        graph = new GraphEdgeList<>();
        availableTransports = new ArrayList<>();
        userStops = new HashSet<>();
        transportStrategy = new LowestDurationStrategy(); //Default strategy
        pathStrategy = new ShortestPathStrategy();
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
     * Adds a new Stop to the map (counting as a user stop)
     * @param stop to add
     */
    public void addUserStop(Stop stop) {
        getGraph().insertVertex(Objects.requireNonNull(stop));
        getUserStops().add(stop);
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
     * Returns the stops added by the user
     * @return stops added by the user
     */
    public Collection<Stop> getUserStops() {
        return userStops;
    }

    /**
     * Returns all the routes in the map
     * @return all the routes in the map
     */
    public Collection<Route> getRoutes() {
        return getGraph().edges().stream().map(Edge::element).toList();
    }

    /**
     * Returns the vertex containing the stop
     * @param stop to find
     * @return vertex containing the stop, null if it doesn't exist
     */
    public Vertex<Stop> getVertexOfStop(Stop stop) {
        return getGraph().vertices().stream().filter(v -> v.element().equals(stop)).findFirst().orElse(null);
    }

    /**
     * Returns the edge containing the route
     * @param route to find
     * @return edge containing the route, null if it doesn't exist
     */
    public Edge<Route, Stop> getEdgeOfRoute(Route route) {
        return getGraph().edges().stream().filter(e -> e.element().equals(route)).findFirst().orElse(null);
    }

    /**
     * Returns the amount of stops adjacent to the provided one
     * @param stop to count adjacent stops from
     * @return number of stops adjacent stops
     */
    private int getNumberOfAdjacentStops(Stop stop) {
        Vertex<Stop> stopVertex = getVertexOfStop(stop);
        if(stopVertex == null) return 0;
        return getGraph().incidentEdges(stopVertex).size();
    }

    /**
     * Returns the amount of stops in the map
     * @return Number of stops in the map
     */
    public int getNumberOfStops() {
        return getStops().size();
    }

    /**
     * Returns the total amount of routes (counting all trasnports each route)
     * @return total amount of routes
     */
    public int getNumberOfRoutes() {
        return getRoutes().size();
    }

    /**
     * Returns the amount of isolated stops in the map
     * @return number of isolated stops in the map
     */
    public int getNumberOfIsolatedStops() {
        return (int) getStops().stream().filter(stop -> getNumberOfAdjacentStops(stop) == 0).count();
    }

    /**
     * Returns the amount of stops inserted by the user
     * @return number of stops inserted by the user
     */
    public int getNumberOfUserStops() {
        return userStops.size();
    }

    /**
     * Returns the amount of routes per transport
     * @return Map [Transport, Number of routes] (sorted by transport name asc)
     */
    public Map<String, Integer> getNumberOfRoutesPerTransport() {
        Map<String, Integer> map = new TreeMap<>();
        getAvailableTransports().forEach(transport -> {
            map.put(transport, (int) getRoutes().stream().filter(route -> route.getAvailableTransports().contains(transport)).count());
        });
        return map;
    }

    /**
     * Returns the list of stops that support all transports
     * @return stops that support all transports
     */
    public Collection<Stop> getStopsWithAllTransports() {
        return getStops().stream().filter(stop -> {
            final Set<String> stopTransports = getGraph().incidentEdges(getVertexOfStop(stop)).stream().map(Edge::element).map(Route::getAvailableTransports).flatMap(Collection::stream).collect(Collectors.toSet());
            return stopTransports.size() == getAvailableTransports().size();
        }).sorted().toList();
    }

    public void setPathStrategy(PathStrategy pathStrategy) {
        this.pathStrategy = Objects.requireNonNull(pathStrategy);
    }

    public void setTransportStrategy(TransportStrategy transportStrategy) {
        this.transportStrategy = Objects.requireNonNull(transportStrategy);
    }

    public Path findPath(Stop start, Stop end) {
        Vertex<Stop> startVertex = getVertexOfStop(start);
        Vertex<Stop> endVertex = getVertexOfStop(end);

        final Queue<Path> bestPaths = new PriorityQueue<>(pathStrategy);
        final Set<Vertex<Stop>> visited = new HashSet<>();

        bestPaths.add(new Path(Collections.singletonList(startVertex)));

        while (!bestPaths.isEmpty() && !bestPaths.peek().getLastVertex().equals(endVertex)) {
            Path current = bestPaths.remove();
            visited.add(current.getLastVertex());

            getGraph().incidentEdges(current.getLastVertex()).forEach(edge -> {
                final Vertex<Stop> vertex = getGraph().opposite(current.getLastVertex(), edge);
                if(!visited.contains(vertex)) {
                    try {
                        //Add the path:
                        Path newPath = new Path(current);
                        newPath.addVertex(vertex);
                        newPath.addEdge(edge);
                        newPath.addCost(transportStrategy.getWeight(edge.element()));
                        newPath.addTransport(transportStrategy.getTransport(edge.element()));
                        bestPaths.add(newPath);
                    } catch (MissingTransportException e) {/* Next Iteration */}
                }
            });
        }

        return bestPaths.peek();
    }

    public Path getLongestPathOfTransport(String transport) {
        setTransportStrategy(new FixedTransportStrategy(transport));
        Set<Path> paths = new TreeSet<>(new LargestPathStrategy());

        getStops().forEach(stop -> {
            getStops().forEach(stop1 -> {
                final Path path = findPath(stop, stop1);
                if(path != null)
                    paths.add(path);
            });
        });

        return paths.stream().findFirst().orElse(null);
    }
}
