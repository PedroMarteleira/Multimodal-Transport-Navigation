package pt.pa.view.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import pt.pa.model.CostField;
import pt.pa.model.Stop;
import pt.pa.model.TransportInformation;
import pt.pa.model.TransportMapController;
import pt.pa.view.Components.NumberTextField;
import pt.pa.view.helpers.ComponentBuilder;

import java.security.InvalidParameterException;

/**
 * Dialog used to update/create walk routes
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public class PutWalkingRouteDialog extends AppDialog<Object> {
    private static final double ITEM_SPACING = 5.0;

    private Button putButton;
    private ComboBox<Stop> stopComboBox, stopComboBox1;
    private NumberTextField costTextField, distanceTextField, durationTextField;

    /**
     * Class Constructor
     * @param controller TransportMap Controller
     */
    public PutWalkingRouteDialog(TransportMapController controller) {
        setTitle("Criar/Adicionar rota a andar");

        this.putButton = new Button("Aplicar");
        this.stopComboBox = new ComboBox<>();
        this.stopComboBox1 = new ComboBox<>();
        this.costTextField = new NumberTextField();
        this.distanceTextField = new NumberTextField();
        this.durationTextField = new NumberTextField();

        stopComboBox.getItems().addAll(controller.getStops());
        stopComboBox1.getItems().addAll(controller.getStops());

        costTextField.promptTextProperty().setValue("ex: 2,5");
        distanceTextField.promptTextProperty().setValue("ex: 40,5");
        durationTextField.promptTextProperty().setValue("ex: 19");

        doLayout();
        setTriggers(controller);
    }

    /**
     * Does the layout
     */
    private void doLayout() {
        getRoot().setSpacing(ITEM_SPACING);

        getRoot().getChildren().addAll(
                ComponentBuilder.createSubtitledLabel("Criar/Modificar rota:"),
                new HBox(ComponentBuilder.createLabel("Partida: "), stopComboBox),
                new HBox(ComponentBuilder.createLabel("Destino: "), stopComboBox1),
                new HBox(ComponentBuilder.createLabel(CostField.COST+ ": "), costTextField),
                new HBox(ComponentBuilder.createLabel(CostField.DURATION + ": "), durationTextField, ComponentBuilder.createLabel(" minutos")),
                new HBox(ComponentBuilder.createLabel(CostField.DISTANCE + ": "), distanceTextField, ComponentBuilder.createLabel(" Km")),
                putButton
        );
    }

    /**
     * Sets the triggers/events
     * @param controller TransportMap controller
     */
    private void setTriggers(TransportMapController controller) {
        stopComboBox.setOnAction(e -> loadValues(controller));
        stopComboBox1.setOnAction(e -> loadValues(controller));
        putButton.setOnAction(e -> {
            final String errorTitle = "Erro ao adicionar/atualizar rota";
            try {
                controller.doPutWalkTransportInformation(
                        stopComboBox.getValue(),
                        stopComboBox1.getValue(),
                        new TransportInformation(costTextField.getNumber(), distanceTextField.getNumber(), durationTextField.getNumber())
                );
                this.close();
            } catch (NumberFormatException exception) {
                displayAlert(Alert.AlertType.ERROR, errorTitle, "Os campos assinalados a vermelho são apenas numéricos!");
            } catch (InvalidParameterException exception) {
                displayAlert(Alert.AlertType.ERROR, errorTitle, exception.getMessage());
            } catch (NullPointerException exception) {
                displayAlert(Alert.AlertType.ERROR, errorTitle, "Por favor selecione as paragens!");
            }
        });
    }

    /**
     * Loads the values if the route already exists
     * @param controller TransportMap Controller
     */
    private void loadValues(TransportMapController controller) {
        if(stopComboBox.getValue() != null && stopComboBox1.getValue() != null) {
            TransportInformation information = controller.getInformationOfWalkingRoute(stopComboBox.getValue(), stopComboBox1.getValue());

            if(information == null) {
                clearTextFields();
                return;
            }
            costTextField.setText(String.valueOf(information.getCost()));
            durationTextField.setText(String.valueOf(information.getDuration()));
            distanceTextField.setText(String.valueOf(information.getDistance()));
        } else {
            clearTextFields();
        }
    }

    /**
     * Clear all textFields from this dialog
     */
    private void clearTextFields() {
        costTextField.setText("");
        durationTextField.setText("");
        distanceTextField.setText("");
    }
}
