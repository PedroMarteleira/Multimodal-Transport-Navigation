package pt.pa.model;

import com.brunomnsilva.smartgraph.graph.Edge;
import com.brunomnsilva.smartgraph.graph.Vertex;

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
     * @param vertices list of vertices in the path
     */
    public Path(List<Vertex<Stop>> vertices) {
        this.vertices = Objects.requireNonNull(vertices);
        this.totalCost = 0.0;
        this.transports = new ArrayList<>();
        this.edges = new ArrayList<>();
        this.costsInformation = new TransportInformation();
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
     * Adds a new vertex to the path
     * @param vertex to add
     */
    public void addVertex(Vertex<Stop> vertex) {
        this.vertices.add(Objects.requireNonNull(vertex));
    }

    /**
     * Adds a new edge to the path
     * @param edge to add
     */
    public void addEdge(Edge<Route, Stop> edge) {
        this.edges.add(Objects.requireNonNull(edge));
    }

    /**
     * Adds the given costs (transport information) to the path
     */
    public void addCostsInformation(TransportInformation costsInformation) {
        this.costsInformation = getCostsInformation().sum(costsInformation);
    }

    /**
     * Add the cost to the path (Accumulated)
     * @param cost to add
     */
    public void addCost(double cost) {
        this.totalCost += cost;
    }

    /**
     * Adds transport to the path
     * @param transport to add
     */
    public void addTransport(String transport) {
        this.transports.add(Objects.requireNonNull(transport));
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
