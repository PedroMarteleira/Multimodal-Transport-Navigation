package pt.pa.view.dialogs;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Class StandardDialog
 * This class is mostly used for future customization.
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public abstract class StandardDialog {
    private final Stage stage;
    private VBox root;

    /**
     * Class constructor
     */
    public StandardDialog(String title) {
        stage = new Stage();
        root = new VBox();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
    }

    /**
     * Displays the dialog
     */
    public void show() {
        getStage().show();
    }

    /**
     * Closes the dialog
     */
    public void close() {
        getStage().close();
    }

    /**
     * Returns the stage
     *
     * @return the Stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Returns the root (main element VBox)
     * @return root element
     */
    public VBox getRoot() {
        return root;
    }

    /**
     * Aux function to create the ok button
     * @return Typical dialog OK Button
     */
    protected Button createOkButton() {
        Button button = new Button("Ok");
        button.setOnAction(event -> this.close());
        return button;
    }
}
