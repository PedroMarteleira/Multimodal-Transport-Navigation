package pt.pa.model;

import com.brunomnsilva.smartgraph.graph.Edge;
import com.brunomnsilva.smartgraph.graph.Vertex;
import pt.pa.pattern.strategy.transport.TransportStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Represents a path in the context of the problem
 *
 * @author Pedro Marteleira (20230334@estudantes.ips.pt)
 */
public class Path {
    private List<Vertex<Stop>> vertices;
    private List<Edge<Route, Stop>> edges;
    private List<String> transports;

    private double totalCost;
    private TransportInformation costsInformation;

    /**
     * Class constructor
     * @param initialVertex begin of the path
     */
    public Path(Vertex<Stop> initialVertex) {
        this.vertices = new ArrayList<>();
        this.totalCost = 0.0;
        this.transports = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.costsInformation = new TransportInformation();

        this.vertices.add(Objects.requireNonNull(initialVertex));
    }

    /**
     * Copy constructor
     * @param path path to clone data from
     */
    public Path(Path path) {
        this.vertices = new ArrayList<>(path.getVertices());
        this.edges = new ArrayList<>(path.getEdges());
        this.totalCost = path.getTotalCost();
        this.transports = new ArrayList<>(path.getTransports());
        this.costsInformation = new TransportInformation(path.getCostsInformation());
    }

    /**
     * Registers a new movement in the path
     * @param vertex movement dest
     * @param edge edge used
     * @param cost movement cost
     * @param usedTransport transport used
     * @param costsInformation all costs of this route
     */
    public void addMovement(Vertex<Stop> vertex, Edge<Route, Stop> edge, double cost, String usedTransport, TransportInformation costsInformation) {
        Objects.requireNonNull(vertex);
        Objects.requireNonNull(edge);
        Objects.requireNonNull(usedTransport);
        Objects.requireNonNull(costsInformation);

        this.vertices.add(vertex);
        this.edges.add(edge);
        this.totalCost += cost;
        this.transports.add(usedTransport);
        this.costsInformation = this.costsInformation.sum(costsInformation);
    }

    /**
     * Returns the last vertex in the path
     * @return last vertex from the path
     */
    public Vertex<Stop> getLastVertex() {
        return vertices.get(vertices.size() - 1);
    }

    /**
     * Returns the list of vertices in the path
     * @return list of vertices in the path
     */
    public List<Vertex<Stop>> getVertices() {
        return vertices;
    }

    /**
     * Returns the list of edges in the path
     * @return list of edges in the path
     */
    public List<Edge<Route, Stop>> getEdges() {
        return edges;
    }

    /**
     * Returns the accumulated cost (by addCost)
     * @return accumulated cost
     */
    public double getTotalCost() {
        return totalCost;
    }

    /**
     * Returns the list of transports in the path
     * @return list of transports in the path
     */
    public Collection<String> getTransports() {
        return transports;
    }

    /**
     * Returns the list of stops in the path (vertexes mapped)
     * @return list of stops in the path
     */
    public Collection<Stop> getStops() {
        return getVertices().stream().map(Vertex::element).toList();
    }

    /**
     * Returns the costs information this Path
     * @return costs information about this path
     */
    public TransportInformation getCostsInformation() {
        return costsInformation;
    }
}
