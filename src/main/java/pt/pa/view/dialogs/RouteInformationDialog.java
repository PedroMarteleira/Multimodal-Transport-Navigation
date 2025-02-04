package pt.pa.view.dialogs;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.Region;
import pt.pa.model.CostField;
import pt.pa.model.Route;
import pt.pa.model.TransportInformation;
import pt.pa.view.Components.TableComponent;
import pt.pa.view.MainView;
import pt.pa.view.helpers.ComponentBuilder;

import java.util.Objects;

/**
 * Displays all information about a route
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public class RouteInformationDialog extends AppDialog<Object> {
    private Route route;

    private TableComponent transportsTable;
    private CheckBox activeCheckBox;

    /**
     * Class constructor
     * @param route to show the information from
     */
    public RouteInformationDialog(Route route) {
        super();

        this.route = Objects.requireNonNull(route);
        this.transportsTable = new TableComponent();
        this.activeCheckBox = new CheckBox("Ativa");

        setTitle("Informação da rota");

        //Checkbox:
        activeCheckBox.setSelected(route.isActive());

        //Table columns:
        transportsTable.addColumn("Transporte");
        for (CostField costField : CostField.values()) {
            transportsTable.addColumn(costField.toString());
        }

        //Table rows:
        route.getTransportsWithInformation().entrySet().forEach(entry -> {
            final TransportInformation information = entry.getValue();
            transportsTable.addRow(
                    MainView.capitalizeString(entry.getKey()),
                    TransportInformation.formatCost(information.getCost()),
                    TransportInformation.formatDistance(information.getDistance()),
                    TransportInformation.formatDuration(information.getDuration())
            );
        });


        doLayout();
        setTriggers();
    }

    /**
     * Does the layout
     */
    private void doLayout() {
        getDialogPane().setPrefHeight(Region.USE_COMPUTED_SIZE);
        //Add the controls
        getRoot().getChildren().addAll(
                ComponentBuilder.createTitledLabel(route.getStart() + " ↔ " + route.getDestination()),
                ComponentBuilder.createSubtitledLabel("Transportes:"),
                transportsTable,
                activeCheckBox,
                ComponentBuilder.createSubtitledLabel("Desativar transportes:"),
                ComponentBuilder.createLabel("NOTA: selecionado = inativo")
        );

        getRoot().getChildren().addAll(
                route.getAvailableTransports().stream().map(t -> ComponentBuilder.createCheckBox(t, route.getDisabledTransports())).toList()
        );
    }

    /**
     * Sets the dialog triggers/events
     */
    private void setTriggers() {
        activeCheckBox.setOnAction(e -> {
            if (activeCheckBox.isSelected()) {
                route.enable();
            } else {
                route.disable();
            }
        });
    }
}
