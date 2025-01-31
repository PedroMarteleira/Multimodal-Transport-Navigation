package pt.pa.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.*;
import pt.pa.model.*;
import pt.pa.pattern.observer.Observer;
import pt.pa.view.Components.*;
import pt.pa.view.dialogs.RouteInformationDialog;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * Application MainView
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public class MainView extends VBox implements Observer, MainViewInterface {
    private static final double MENU_MARGIN = 10.0;

    private TransportMap transportMap;

    private MainMenuBar menuBar;
    private MapView mapView;
    private StackPane root;
    private StatisticsPanel informationPanel;

    private int defaultMenuLayers;

    /**
     * Class constructor
     * @param transportMap model
     * @throws Exception if something went wrong
     */
    public MainView(TransportMap transportMap) throws Exception {
        this.transportMap = Objects.requireNonNull(transportMap);
        this.mapView = new MapView(transportMap.getGraph());
        this.informationPanel = new StatisticsPanel(transportMap);
        this.menuBar = new MainMenuBar(this);

        this.root = new StackPane();
        this.defaultMenuLayers = 0;

        doLayout();
        update(null);
    }

    /**
     * Does the View Layout
     */
    private void doLayout() {
        root.setAlignment(Pos.TOP_RIGHT);
        //Border pane to define the floating menus position:
        BorderPane mainPane = new BorderPane();
        mainPane.setMouseTransparent(true);
        StackPane.setMargin(mainPane, new Insets(MENU_MARGIN));

        root.getChildren().addAll(mapView, mainPane);

        this.getChildren().addAll(menuBar, root);
        mainPane.setBottom(new HBox(informationPanel));

        defaultMenuLayers = root.getChildren().size();
    }

    /**
     * Sets the View triggers/Events
     * @param controller controller (mvc)
     */
    public void setTriggers(TransportMapController controller) {
        Objects.requireNonNull(controller);
        menuBar.init(controller);
        mapView.setTriggers(controller);
    }

    /**
     * Puts the first letter of the string in upper case
     * @param string string to transform
     * @return same string with first letter of in upper case
     */
    public static String capitalizeString(String string) {
        return Objects.requireNonNull(string).substring(0, 1).toUpperCase() + string.substring(1);
    }

    /**
     * Displays the menu that allows the user to pick the options
     * @param controller (mvc) TransportMap controller
     */
    public void showFindShortestPathSetupMenu(TransportMapController controller) {
        closeSideMenus();
        new ShortestPathSetupSideMenu(root, mapView, controller).show();
    }

    /**
     * Close all menus that aren't shown by default
     */
    private void closeSideMenus() {
        for (int i = defaultMenuLayers; i < root.getChildren().size(); i++) {
            Node node = root.getChildren().get(i);
            if(node instanceof SideMenu) ((SideMenu)node).close();
        }
    }

    @Override
    public void update(Object obj) {
        mapView.update(null);
        informationPanel.update(null);
    }

    @Override
    public void displayMarkedStops(Collection<Stop> stops) {
        closeSideMenus();
        mapView.markVertices(stops.stream().map(transportMap::getVertexOfStop).toList());
        new AllTransportStopsSideMenu(root, mapView, stops).show();
    }

    @Override
    public void displayPath(Path path) {
        closeSideMenus();
        mapView.markPath(path);
        new PathSideMenu(root, mapView, path).show();
    }

    @Override
    public void displayShortestPath(Path path) {
        mapView.markPath(path);
        new PathSideMenu(root, mapView, path).show();
    }

    @Override
    public void showStopInformation(Stop stop) {
        closeSideMenus();
        mapView.markVertices(Collections.singletonList(transportMap.getVertexOfStop(stop)));
        new StopSideMenu(root, mapView, stop).show();
    }

    @Override
    public void showRouteInformation(Route route) {
        closeSideMenus();
        new RouteInformationDialog(route).show();
    }

    @Override
    public void displayError(String title, String caption) {
        Alert alert = new Alert(Alert.AlertType.ERROR, caption);
        alert.setHeaderText(title);
        alert.showAndWait();
    }
}
