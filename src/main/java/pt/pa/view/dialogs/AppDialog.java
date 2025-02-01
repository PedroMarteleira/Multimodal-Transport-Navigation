package pt.pa.view.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;
import pt.pa.Main;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Provides a standard for a dialog in the application
 * @param <T> Return value
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public class AppDialog<T> extends Dialog<T> {
    private static final double WIDTH = 400, HEIGHT = 200;

    private VBox root;

    /**
     * Class constructor
     */
    public AppDialog() {
        root = new VBox();

        //Styles:
        Path stylesFilePath = Paths.get(Main.STYLES_FILE_PATH);
        getDialogPane().getStylesheets().add(stylesFilePath.toUri().toString());

        doLayout();
    }

    /**
     * Does the layout
     */
    private void doLayout() {
        //Layout:
        getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

        root.setMaxWidth(Double.MAX_VALUE);
        root.setPrefWidth(WIDTH);

        getDialogPane().setContent(root);
        getDialogPane().setPrefSize(WIDTH, HEIGHT);
    }

    /**
     * Returns the main element
     * @return main element (root)
     */
    public VBox getRoot() {
        return root;
    }

    /**
     * Displays an message popup
     * @param type of alert to show
     * @param title Message title
     * @param caption Message body
     */
    public void displayAlert(Alert.AlertType type, String title, String caption) {
        Alert alert = new Alert(type, caption);
        alert.setHeaderText(title);
        alert.showAndWait();
    }
}
