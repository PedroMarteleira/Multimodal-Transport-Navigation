package pt.pa.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.shape.Path;
import pt.pa.controller.TransportMapController;
import pt.pa.model.Stop;
import pt.pa.model.TransportMap;
import pt.pa.observer.Observer;
import pt.pa.view.Components.AllTransportStopsSideMenu;
import pt.pa.view.Components.MainMenuBar;
import pt.pa.view.Components.MapView;
import pt.pa.view.Components.StatisticsPanel;

import java.util.Collection;
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

    /**
     * Class constructor
     * @param transportMap model
     * @throws Exception if something went wrong
     */
    public MainView(TransportMap transportMap) throws Exception {
        this.transportMap = Objects.requireNonNull(transportMap);
        this.mapView = new MapView(transportMap.getGraph());
        this.informationPanel = new StatisticsPanel(transportMap);
        this.menuBar = new MainMenuBar();

        this.root = new StackPane();

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

    @Override
    public void update(Object obj) {
        mapView.update(null);
        informationPanel.update(null);
    }

    @Override
    public void displayStopsWithAllTransports(Collection<Stop> stops) {
        mapView.markVertices(stops.stream().map(transportMap::getVertexOfStop).toList());
        new AllTransportStopsSideMenu(root, mapView, stops).show();
    }

    @Override
    public void displayPath(Path path) {
        //TODO:
    }
}
