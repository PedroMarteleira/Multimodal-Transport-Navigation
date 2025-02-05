package pt.pa.view.dialogs;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.Region;
import pt.pa.model.CostField;
import pt.pa.model.Route;
import pt.pa.model.TransportInformation;
import pt.pa.model.TransportMapController;
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
    private final Route route;

    private final TableComponent transportsTable;
    private final CheckBox activeCheckBox;

    /**
     * Class constructor
     * @param route to show the information from
     */
    public RouteInformationDialog(TransportMapController controller, Route route) {
        super();
        this.route = Objects.requireNonNull(route);
        this.transportsTable = new TableComponent();
        this.activeCheckBox = new CheckBox("Ativa");

        setTitle("Informação da Rota");

        // Checkbox setup
        activeCheckBox.setSelected(route.isActive());

        // Table columns
        transportsTable.addColumn("Transporte");
        for (CostField costField : CostField.values()) {
            transportsTable.addColumn(costField.toString());
        }

        // Table rows
        route.getTransportsWithInformation().forEach((key, information) ->
                transportsTable.addRow(
                        MainView.capitalizeString(key),
                        TransportInformation.formatCost(information.getCost()),
                        TransportInformation.formatDistance(information.getDistance()),
                        TransportInformation.formatDuration(information.getDuration())
                )
        );

        doLayout(controller);
        setTriggers(controller);
    }

    /**
     * Does the layout
     */
    private void doLayout(TransportMapController controller) {
        getDialogPane().setPrefHeight(Region.USE_COMPUTED_SIZE);

        // Add components
        getRoot().getChildren().addAll(
                ComponentBuilder.createTitledLabel(route.toString()),
                ComponentBuilder.createSubtitledLabel("Transportes:"),
                transportsTable,
                ComponentBuilder.createSubtitledLabel("Ativa:"),
                activeCheckBox,
                ComponentBuilder.createSubtitledLabel("Ativar e desativar transportes:")
        );


        getRoot().getChildren().addAll(route.getAvailableTransports().stream()
                .map(t -> {
                    CheckBox checkBox = new CheckBox(MainView.capitalizeString(t));
                    checkBox.setSelected(route.getAllowedTransports().contains(t));

                    checkBox.setOnAction(e -> {
                        if (checkBox.isSelected()) {
                            controller.doEnableTransportOnRoute(route, t);
                        } else {
                            controller.doDisableTransportOnRoute(route, t);
                        }
                    });

                    return checkBox;
                }).toList());
    }

    /**
     * Sets the dialog triggers/events
     */
    private void setTriggers(TransportMapController controller) {
        activeCheckBox.setOnAction(e -> {
            if (activeCheckBox.isSelected()) {
                controller.doEnableRoute(route);
            } else {
                controller.doDisableRoute(route);
            }
        });
    }
}
