package pt.pa.view.Components;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import pt.pa.model.Stop;
import pt.pa.view.helpers.ComponentBuilder;

import java.util.Collection;
import java.util.Objects;

public class StopSideMenu extends SideMenu {
    private static double SPACING = 5.0;
    private static double MENU_WIDTH = 200.0;

    private Stop stop;
    private MapView mapView;

    /**
     * Class constructor
     * @param parent Parent node
     * @param mapView Mapview graphical map representation
     * @param stop Stop to show the information
     */
    public StopSideMenu(Pane parent, MapView mapView, Stop stop) {
        super(parent);
        this.mapView = Objects.requireNonNull(mapView);
        this.stop = Objects.requireNonNull(stop);

        doLayout();
    }

    /**
     * Does the layout of the menu
     */
    void doLayout() {
        this.setSpacing(SPACING);
        this.setMinWidth(MENU_WIDTH);
        this.setMaxWidth(MENU_WIDTH);

        getChildren().addAll(
                ComponentBuilder.createTitledLabel("Paragem:"),
                ComponentBuilder.createSubtitledLabel("► Código: " + stop.getCode()),
                ComponentBuilder.createSubtitledLabel("► Nome: " + stop.getName()),
                ComponentBuilder.createSubtitledLabel("► Latitude: " + stop.getLatitude()),
                ComponentBuilder.createSubtitledLabel("► Latitude: "+ stop.getLongitude())
        );
    }

    @Override
    protected void onQuit() {
        mapView.clearMarkedVertices();
    }
}
