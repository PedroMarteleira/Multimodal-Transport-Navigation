package pt.pa.view.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import pt.pa.model.Stop;
import pt.pa.model.TransportMapController;
import pt.pa.view.Components.NumberTextField;
import pt.pa.view.helpers.ComponentBuilder;

import java.security.InvalidParameterException;

/**
 * Dialog used to create a stop
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public class StopCreationDialog extends AppDialog<Object> {
    private static final double ITEM_SPACING = 5.0;

    private TextField stopCodeTextField, stopNameTextField;
    private NumberTextField stopLatitudeTextField, stopLongitudeTextField;
    private Button createButton;

    /**
     * Class constructor
     * @param controller (mvc) TransportMap controller
     */
    public StopCreationDialog(TransportMapController controller) {
        setTitle("Adicionar Paragem");

        //Create text fields:
        stopCodeTextField = new TextField();
        stopNameTextField = new TextField();
        stopLatitudeTextField = new NumberTextField();
        stopLongitudeTextField = new NumberTextField();

        createButton = new Button("Adicionar Paragem");

        //Placeholders:
        stopCodeTextField.promptTextProperty().set("ex: COR543");
        stopNameTextField.promptTextProperty().set("ex: Corroios");
        stopLatitudeTextField.promptTextProperty().set("ex: 38,432432");
        stopLongitudeTextField.promptTextProperty().set("ex: -9,43242332");

        doLayout();
        setTriggers(controller);
    }

    /**
     * Does the layout
     */
    private void doLayout() {
        getRoot().setSpacing(ITEM_SPACING);
        getRoot().getChildren().addAll(
                ComponentBuilder.createTitledLabel("Criar Paragem:"),
                new HBox(ComponentBuilder.createLabel("Código: "), stopCodeTextField),
                new HBox(ComponentBuilder.createLabel("Nome: "), stopNameTextField),
                new HBox(ComponentBuilder.createLabel("Latitude: "), stopLatitudeTextField),
                new HBox(ComponentBuilder.createLabel("Longitude: "), stopLongitudeTextField),
                createButton
        );
    }

    /**
     * Sets the triggers/events
     * @param controller TransportMap controller (mvc)
     */
    private void setTriggers(TransportMapController controller) {
        final String errorTitle = "Erro ao criar paragem";
        createButton.setOnAction(e -> {
            try {
                controller.doAddStop(new Stop(stopCodeTextField.getText(), stopNameTextField.getText(), stopLatitudeTextField.getNumber(), stopLongitudeTextField.getNumber()));
                displayAlert(Alert.AlertType.INFORMATION, "Informação", "Paragem adicionada com sucesso!");
                this.close();
            } catch (NumberFormatException ex) {
                displayAlert(Alert.AlertType.ERROR, errorTitle, "Formato numérico incorreto, por favor confirme os parametros latitude e longitude!");
            } catch (InvalidParameterException ex) {
                displayAlert(Alert.AlertType.ERROR, errorTitle, ex.getMessage());
            }
        });
    }
}
