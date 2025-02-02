package pt.pa.view.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import pt.pa.model.Stop;
import pt.pa.model.TransportMapController;
import pt.pa.view.helpers.ComponentBuilder;

/**
 * This dialog lets the user choose a stop to be removed
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public class StopRemovalDialog extends AppDialog<Stop> {
    private static final double WIDTH = 300, HEIGHT = 100;
    private static final double COMBO_MIN_WIDTH = 200.0;

    private ComboBox<Stop> stopComboBox;
    private Button removeButton;

    /**
     * Class constructor
     * @param controller transportMap controller
     */
    public StopRemovalDialog(TransportMapController controller) {
        stopComboBox = new ComboBox<>();
        stopComboBox.getItems().addAll(controller.getUserStops());
        removeButton = new Button("Apagar");

        setTitle("Remover uma paragem");

        doLayout();
        setTriggers(controller);
    }

    /**
     * Does the layout
     */
    public void doLayout() {
        stopComboBox.setMinWidth(COMBO_MIN_WIDTH);
        getDialogPane().setPrefSize(WIDTH, HEIGHT);
        getRoot().getChildren().addAll(
                ComponentBuilder.createSubtitledLabel("Selecione uma Paragem:"),
                new HBox(stopComboBox, removeButton)
        );
    }

    /**
     * Sets the dialog triggers/events
     * @param controller transportMap controller
     */
    public void setTriggers(TransportMapController controller) {
        removeButton.setOnAction(e -> {
           if(stopComboBox.getValue() == null) {
               displayAlert(Alert.AlertType.ERROR, "Erro ao remover paragem", "Por favor selecione uma paragem!");
           } else {
               controller.doRemoveStop(stopComboBox.getValue());
               this.close();
           }
        });
    }
}
