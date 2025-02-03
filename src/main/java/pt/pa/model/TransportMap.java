package pt.pa.model;

import com.brunomnsilva.smartgraph.graph.Edge;
import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.GraphEdgeList;
import com.brunomnsilva.smartgraph.graph.Vertex;
import pt.pa.exception.MissingTransportException;
import pt.pa.pattern.observer.Subject;
import pt.pa.pattern.strategy.path.LargestPathStrategy;
import pt.pa.pattern.strategy.path.PathStrategy;
import pt.pa.pattern.strategy.path.ShortestPathStrategy;
import pt.pa.pattern.strategy.transport.FixedTransportStrategy;
import pt.pa.pattern.strategy.transport.LowestDurationStrategy;
import pt.pa.pattern.strategy.transport.TransportStrategy;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents the map of transports in the context of the problem (model)
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public class TransportMap extends Subject {
    private Graph<Stop, Route> graph;
    private Set<String> availableTransports;
    private Set<Stop> userStops;

    private TransportStrategy transportStrategy;
    private PathStrategy pathStrategy;


    /**
     * Class constructor
     */
    public TransportMap() {
        super();
        graph = new GraphEdgeList<>();
        availableTransports = new TreeSet<>();
        userStops = new HashSet<>();
        transportStrategy = new LowestDurationStrategy(availableTransports); //Default strategy
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
     * Removes the given Stop from the map (user stop)
     * @param stop to remove
     */
    public void removeUserStop(Stop stop) {
        if(getUserStops().contains(stop)) {
            getGraph().removeVertex(getVertexOfStop(Objects.requireNonNull(stop)));
            getUserStops().remove(stop);
            notifyObservers(null);
        }
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
    public void setAvailableTransports(Set<String> availableTransports) {
        this.availableTransports = Objects.requireNonNull(availableTransports);
    }

    /**
     * Returns the Collection of Available Transports
     * @return Collection of Available Transports
     */
    public Collection<String> getAvailableTransports() {
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

    /**
     * Sets the strategy used to find the shortest path
     * @param pathStrategy new strategy
     */
    public void setPathStrategy(PathStrategy pathStrategy) {
        this.pathStrategy = Objects.requireNonNull(pathStrategy);
    }

    /**
     * Sets the strategy used to select the transport in the path finding
     * @param transportStrategy new strategy
     */
    public void setTransportStrategy(TransportStrategy transportStrategy) {
        this.transportStrategy = Objects.requireNonNull(transportStrategy);
    }

    /**
     * Variance of dijsksta or A* algorithm (priority queue-based pathfinding algorithm) that supports negative weights
     * Uses the pathStrategy to determine the priority of the path (asc = shortest paths, desc = bigger paths)
     * Uses transportMapStrategy to determine the transport to choose in a route
     * @param start path begin
     * @param end path end
     * @return path from the start to the end stop, null if impossible
     */
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

                        final String usedTransport = transportStrategy.getTransport(edge.element());
                        newPath.addTransport(usedTransport);
                        newPath.addCostsInformation(edge.element().getTransportInformation(usedTransport));

                        bestPaths.add(newPath);
                    } catch (MissingTransportException e) {/* Next Iteration */}
                }
            });
        }

        return bestPaths.peek();
    }

    /**
     * Returns the most expensive path (in terms of distance) possible using only a transport
     * @param transport to find the path from
     * @return biggest path, null if impossible
     */
    public Path getLongestPathOfTransport(String transport) {
        setTransportStrategy(new FixedTransportStrategy(transport));
        Set<Path> paths = new TreeSet<>(new LargestPathStrategy());

        List<Stop> stops = getStops().stream().toList();
        for (int i = 0; i < stops.size(); i++) {
            for (int j = i + 1; j < stops.size(); j++) {
                final Path path = findPath(stops.get(i), stops.get(j));
                if (path != null) {
                    paths.add(path);
                }
            }
        }

        return paths.stream().findFirst().orElse(null);
    }

    /**
     * Returns the route of the given stops
     * @param stop to find
     * @param stop1 to find
     * @return Route of the given stops, null if it doesn't exist
     */
    public Route getRouteWithStops(Stop stop, Stop stop1) {
        Objects.requireNonNull(stop);
        Objects.requireNonNull(stop1);
        return getRoutes().stream()
                .filter(route -> (route.getStart().equals(stop) || route.getStart().equals(stop1)) && (route.getDestination().equals(stop) || route.getDestination().equals(stop1)))
                .findFirst()
                .orElse(null);
    }

    /**
     * Removes the given route from the map
     * @param route to remove
     */
    public void removeRoute(Route route) {
        graph.removeEdge(getEdgeOfRoute(route));
        notifyObservers(null);
    }

    /**
     * Replaces the contents of this Map With another one
     * @param transportMap another
     */
    public void replaceWith(TransportMap transportMap) {
        Objects.requireNonNull(transportMap);
        this.graph = transportMap.graph;
        this.transportStrategy = transportMap.transportStrategy;
        this.pathStrategy = transportMap.pathStrategy;
        this.userStops = transportMap.userStops;
        notifyObservers(null);
    }
}
