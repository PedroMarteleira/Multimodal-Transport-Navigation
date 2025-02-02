package pt.pa.view.dialogs;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import pt.pa.model.TransportMapController;
import pt.pa.pattern.command.Command;
import pt.pa.view.helpers.ComponentBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Dialog used to manage the action history
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public class HistoryDialog extends AppDialog <Object> {
    private ListView<Command> actionsListView;
    private Button undoButton;

    /**
     * Class constructor
     * @param controller TransportMap controller
     */
    public HistoryDialog(TransportMapController controller) {
        super();
        setTitle("Histórico de Ações");

        actionsListView = new ListView<>();
        undoButton = new Button("<< Reverter");

        doLayout();
        setTriggers(controller);
        update(controller);
    }

    /**
     * Does the layout
     */
    private void doLayout() {
        getRoot().getChildren().addAll(
                ComponentBuilder.createSubtitledLabel("Histórico:"),
                actionsListView,
                undoButton
        );
    }

    /**
     * Sets the events/triggers
     * @param controller TransportMap Controller
     */
    private void setTriggers(TransportMapController controller) {
        undoButton.setOnAction(e -> {
            controller.doUndo();
            update(controller);
        });
    }

    /**
     * Updates the information displayed
     * @param controller TransportMap Controller
     */
    private void update(TransportMapController controller) {
        List<Command> commandList = new ArrayList<>(controller.getActionHistory());
        Collections.reverse(commandList);
        actionsListView.getItems().setAll(commandList);
    }
}
