package pt.pa.view.Components;

import com.brunomnsilva.smartgraph.graph.Edge;
import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.Vertex;
import com.brunomnsilva.smartgraph.graphview.*;
import javafx.scene.layout.BorderPane;
import pt.pa.model.TransportMapController;
import pt.pa.model.Path;
import pt.pa.model.Route;
import pt.pa.model.Stop;
import pt.pa.pattern.observer.Observer;
import pt.pa.utils.PropertiesUtil;
import pt.pa.view.dialogs.StopInformationDialog;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Represents the graphical view of a transport map. This class is responsible for
 * rendering a transport map with vertices (stops) and edges (routes) in a graphical interface.
 * It computes the screen positions of map vertices based on geographical coordinates.
 *
 * <p>This class extends {@link BorderPane} to lay out the map visualization.</p>
 */
public class MapView extends BorderPane implements Observer {

    // For cohesion purposes, please parametrize me
    private SmartGraphPanel graphView;

    // For cohesion purposes, please parametrize me
    private Graph<Stop, Route> graph;

    private final int mapWidth, mapHeight;

    private final double minLat, maxLat, minLon, maxLon;

    /**
     * Private constructor to initialize the dimensions and geographical bounds of the map.
     *
     * @throws IOException If the properties for map configuration cannot be loaded.
     */
    private MapView() throws IOException{
        this.mapWidth = Integer.parseInt(PropertiesUtil.getInstance().getProperty("map.width"));
        this.mapHeight = Integer.parseInt(PropertiesUtil.getInstance().getProperty("map.height"));
        this.minLat = Double.parseDouble(PropertiesUtil.getInstance().getProperty("map.extent.latitude.min"));
        this.maxLat = Double.parseDouble(PropertiesUtil.getInstance().getProperty("map.extent.latitude.max"));
        this.minLon = Double.parseDouble(PropertiesUtil.getInstance().getProperty("map.extent.longitude.min"));
        this.maxLon = Double.parseDouble(PropertiesUtil.getInstance().getProperty("map.extent.longitude.max"));
    }

    /**
     * Constructs a {@code MapView} for the given transport map.
     *
     * @param graph The transport map to be visualized in the {@code MapView}.
     * @throws Exception If an error occurs while setting up the graph view (e.g., loading properties or CSS).
     */
    public MapView(Graph<Stop, Route> graph) throws Exception {
        this();

        this.graph = Objects.requireNonNull(graph);
        InputStream smartgraphProperties = getClass().getClassLoader().getResourceAsStream("smartgraph.properties");
        URL css = MapView.class.getClassLoader().getResource("styles/smartgraph.css");

        if (css != null) {
            this.graphView = new SmartGraphPanel<>(graph, new SmartGraphProperties(smartgraphProperties), new SmartRandomPlacementStrategy(), css.toURI());
            graphView.setMaxHeight(Integer.parseInt(PropertiesUtil.getInstance().getProperty("map.height")));
            graphView.setMaxWidth(Integer.parseInt(PropertiesUtil.getInstance().getProperty("map.width")));
        }

        doLayout();
    }

    /**
     * Configures the layout of the map view, positioning the vertices and setting the graph view at the center.
     */
    private void doLayout() {
        setSmartGraphVertexPositions(); // Implement this function
        setCenter(this.graphView);
    }

    private void setSmartGraphVertexPositions() {
        graph.vertices().forEach(vertex -> {
            int[] coordinates = computeVertexScreenPosition(vertex.element().getLatitude(), vertex.element().getLongitude());
            graphView.setVertexPosition(vertex, coordinates[0], coordinates[1]);
        });
    }

    /**
     * Computes the screen position (x, y) of a vertex given its latitude and longitude.
     * The position is determined by mapping the geographical coordinates to screen coordinates
     * based on the bounds of the map and the dimensions of the map on the screen.
     *
     * @param latitude The latitude of the vertex (in degrees).
     * @param longitude The longitude of the vertex (in degrees).
     * @return An array of two integers: the x and y screen coordinates of the vertex.
     *         The first element is the x-coordinate, and the second is the y-coordinate.
     *
     * @throws IllegalArgumentException if the latitude or longitude are out of the defined bounds
     *         (latitude should be between {@code minLat} and {@code maxLat}, and longitude should
     *         be between {@code minLon} and {@code maxLon}).
     */
    private int[] computeVertexScreenPosition(double latitude, double longitude) {
        int y = (int) ((maxLat - latitude) / (maxLat - minLat) * mapHeight);
        int x = (int) ((longitude - minLon) / (maxLon - minLon) * mapWidth);

        return new int[]{x, y};
    }

    /**
     * Sets the view events
     */
    public void setTriggers(TransportMapController controller) {
        //Stop double-click event:
        graphView.setVertexDoubleClickAction(e -> {
            final Stop stop = ((SmartGraphVertex<Stop>)e).getUnderlyingVertex().element();
            //TODO: show the menu
            new StopInformationDialog(stop).show();
        });

        //Edge double-click event:
        graphView.setEdgeDoubleClickAction(e -> {
            final Route route = ((SmartGraphEdge<Route, Stop>)e).getUnderlyingEdge().element();
            //TODO: show the menu
        });
    }

    /**
     * Clears all marked vertices from the map
     */
    public void clearMarkedVertices() {
        graph.vertices().stream().map(graphView::getStylableVertex).forEach(style -> style.setStyleClass("vertex"));
    }

    /**
     * Marks all requested vertices on the map
     * @param vertices to mark
     */
    public void markVertices(Collection<Vertex<Stop>> vertices) {
        clearMarkedVertices();
        vertices.stream().map(graphView::getStylableVertex).forEach(style -> style.setStyleClass("vertex-marked"));
    }

    /**
     * Clears all the marked edges in the map
     */
    public void clearMarkedEdges() {
        graph.edges().stream().map(graphView::getStylableEdge).forEach(style -> style.setStyleClass("edge"));
    }

    /**
     * Displays the path in the map with the right colors
     * @param path Path do display
     */
    public void markPath(Path path) {
        if(path == null) return;

        final List<Edge<Route, Stop>> edges = path.getEdges();
        final List<String> transports = path.getTransports().stream().toList();
        for (int i = 0; i < Math.min(edges.size(), transports.size()); i++) {
            graphView.getStylableEdge(edges.get(i)).setStyleClass("edge-" + transports.get(i));
        }
    }

    @Override
    public void update(Object obj) {
        setSmartGraphVertexPositions();
    }
}
