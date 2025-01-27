package pt.pa.view.Components;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import pt.pa.model.Stop;
import pt.pa.view.helpers.ComponentBuilder;

import java.util.Collection;
import java.util.Objects;

/**
 * Menu used to display all stops with all transports available
 */
public class AllTransportStopsSideMenu extends SideMenu {
    private MapView mapView;
    private VBox stopContainer;

    /**
     * Class constructor
     * @param parent Parent node
     * @param mapView Mapview graphical map representation
     * @param stops Collection of stops to display
     */
    public AllTransportStopsSideMenu(Pane parent, MapView mapView, Collection<Stop> stops) {
        super(parent);
        this.mapView = Objects.requireNonNull(mapView);
        this.stopContainer = new VBox();

        if(stops.isEmpty()) {
            stopContainer.getChildren().setAll(ComponentBuilder.createLabel("Sem dados..."));
        } else {
            stopContainer.getChildren().setAll(stops.stream().map(stop -> ComponentBuilder.createSubtitledLabel("• " + stop.toString())).toList());
        }
        doLayout();
    }

    /**
     * Does the layout of the menu
     */
    void doLayout() {
        getChildren().addAll(
                ComponentBuilder.createTitledLabel("Paragens:"),
                stopContainer
        );
    }

    @Override
    protected void onQuit() {
        mapView.clearMarkedVertices();
    }
}
