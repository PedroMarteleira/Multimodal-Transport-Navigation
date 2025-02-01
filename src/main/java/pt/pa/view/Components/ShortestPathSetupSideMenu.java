package pt.pa.view.Components;

import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import pt.pa.model.CostField;
import pt.pa.model.Stop;
import pt.pa.model.TransportMapController;
import pt.pa.view.MainView;
import pt.pa.view.helpers.ComponentBuilder;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Menu used to display all stops with all transports available
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public class ShortestPathSetupSideMenu extends SideMenu {

    private static final double ITEM_SPACING = 5.0;

    private MapView mapView;
    private TransportMapController controller;
    private Set<String> selectedTransports;
    private ComboBox<Stop> sourceStopComboBox, destinationStopComboBox;
    private ComboBox<CostField> costFieldComboBox;

    /**
     * Class constructor
     *
     * @param parent     Parent node
     * @param mapView    Mapview graphical map representation
     * @param controller transportMap controller
     */
    public ShortestPathSetupSideMenu(Pane parent, MapView mapView, TransportMapController controller) {
        super(parent);

        this.mapView = Objects.requireNonNull(mapView);
        this.controller = Objects.requireNonNull(controller);

        this.selectedTransports = controller.getAvailableTransports().stream().map(MainView::capitalizeString).collect(Collectors.toSet());

        this.sourceStopComboBox = new ComboBox<>();
        sourceStopComboBox.getItems().setAll(controller.getStops());

        this.destinationStopComboBox = new ComboBox<>();
        destinationStopComboBox.getItems().setAll(controller.getStops());

        this.costFieldComboBox = new ComboBox<>();
        costFieldComboBox.getItems().setAll(CostField.values());

        doLayout();
    }

    /**
     * Does the layout of the menu
     */
    void doLayout() {

        //Transports selection section:
        getChildren().addAll(
                ComponentBuilder.createTitledLabel("Caminho:"),
                ComponentBuilder.createSubtitledLabel("Transportes:")
        );
        getChildren().addAll(controller.getAvailableTransports().stream().map(transport -> ComponentBuilder.createCheckBox(MainView.capitalizeString(transport), selectedTransports)).toList());

        //Stop pickers:
        getChildren().addAll(
                ComponentBuilder.createLabel("Partida:"),
                sourceStopComboBox,
                ComponentBuilder.createLabel("Destino:"),
                destinationStopComboBox,
                ComponentBuilder.createLabel("Otimizar por menor:"),
                costFieldComboBox,
                ComponentBuilder.createButton("Submeter", e -> controller.doShowShortestPath(getSourceStop(), getDestinationStop(), getSelectedTransports(), getCostField()))
        );
        this.setSpacing(ITEM_SPACING);
    }

    public Collection<String> getSelectedTransports() {
        return selectedTransports;
    }

    public Stop getSourceStop() {
        return sourceStopComboBox.getValue();
    }

    public Stop getDestinationStop() {
        return destinationStopComboBox.getValue();
    }

    public CostField getCostField() {
        return costFieldComboBox.getValue();
    }

    @Override
    protected void onQuit() {/* Nothing needed */}
}