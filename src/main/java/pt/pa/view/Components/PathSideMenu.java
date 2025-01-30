package pt.pa.view.Components;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import pt.pa.model.CostField;
import pt.pa.model.Path;
import pt.pa.model.Stop;
import pt.pa.view.helpers.ComponentBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Menu that show just the path (without any transport)
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public class PathSideMenu extends SideMenu {
    private static double HOUR_MINS = 60.0;

    private MapView mapView;
    private VBox stopContainer;

    private Label costLabel;
    private Label distanceLabel;
    private Label durationLabel;

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

        if(path == null) path = new Path(new ArrayList<>());

        Collection<Stop> stops = path.getStops();

        if(stops.isEmpty()) {
            stopContainer.getChildren().setAll(ComponentBuilder.createLabel("Caminho Impossível..."));
        } else {
            stopContainer.getChildren().setAll(stops.stream().map(stop -> ComponentBuilder.createLabel("• " + stop.toString())).toList());
        }

        costLabel = ComponentBuilder.createLabel(String.format("- %s: %.1f", CostField.COST, path.getCostsInformation().getCost()));
        distanceLabel = ComponentBuilder.createLabel(String.format("- %s: %.1fkm", CostField.DISTANCE, path.getCostsInformation().getDistance()));
        durationLabel = ComponentBuilder.createLabel(String.format("- %s: %dh %dm", CostField.DURATION, (int)(path.getCostsInformation().getDuration() / HOUR_MINS), Math.round(path.getCostsInformation().getDuration() % HOUR_MINS)));

        doLayout();
    }

    /**
     * Does the layout of the menu
     */
    void doLayout() {
        getChildren().addAll(
                ComponentBuilder.createTitledLabel("Paragens:"),
                stopContainer,
                ComponentBuilder.createTitledLabel("Valores:"),
                durationLabel,
                distanceLabel,
                costLabel
        );
    }

    @Override
    protected void onQuit() {
        mapView.clearMarkedEdges();
    }
}
