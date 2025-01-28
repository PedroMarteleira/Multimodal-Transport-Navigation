package pt.pa.view.Components;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import pt.pa.model.Path;
import pt.pa.model.Stop;
import pt.pa.view.helpers.ComponentBuilder;

import java.util.Collection;
import java.util.Objects;

/**
 * Menu that show just the path (without any transport)
 */
public class PathSideMenu extends SideMenu {
    private MapView mapView;
    private VBox stopContainer;
    private Label costLabel;

    /**
     * Class constructor
     * @param parent Parent node
     * @param mapView Mapview graphical map representation
     * @param path Path to display the information of
     */
    public PathSideMenu(Pane parent, MapView mapView, Path path) {
        super(parent);
        this.mapView = Objects.requireNonNull(mapView);
        this.stopContainer = new VBox();

        final Collection<Stop> stops = path.getStops();
        if(stops.isEmpty()) {
            stopContainer.getChildren().setAll(ComponentBuilder.createLabel("Sem dados..."));
        } else {
            stopContainer.getChildren().setAll(stops.stream().map(stop -> ComponentBuilder.createSubtitledLabel("• " + stop.toString())).toList());
        }
        costLabel = ComponentBuilder.createTitledLabel(String.format("- Custo: %.1f", path.getTotalCost()));

        doLayout();
    }

    /**
     * Does the layout of the menu
     */
    void doLayout() {
        getChildren().addAll(
                ComponentBuilder.createTitledLabel("Paragens:"),
                stopContainer,
                costLabel
        );
    }

    @Override
    protected void onQuit() {
        mapView.clearMarkedEdges();
    }
}
