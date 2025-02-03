package pt.pa.view.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import pt.pa.utils.DataSet;
import pt.pa.view.helpers.ComponentBuilder;

import java.io.File;

/**
 * Lets the user choose the files of the dataSet
 */
public class LoadDataSetDialog extends AppDialog<Object> {
    private static final double ITEM_SPACING = 6.0;

    private static final String CHOOSED_TEXT = "Escolhido!", UNCHOOSED_TEXT = "Carregar...";

    private String stopsFile, routesFile;
    private DataSet dataSet;

    private Button stopsButton, routesButton;
    private Button applyButton;

    /**
     * Class constructor
     */
    public LoadDataSetDialog() {
        stopsButton = new Button(UNCHOOSED_TEXT);
        routesButton = new Button(UNCHOOSED_TEXT);
        applyButton = new Button("Carregar DataSet");

        doLayout();
        setTriggers();
    }

    /**
     * Does the layout
     */
    public void doLayout() {
        getRoot().setSpacing(ITEM_SPACING);
        getRoot().getChildren().addAll(
            new HBox(ComponentBuilder.createLabel("Ficheiro de Paragens: "), stopsButton),
            new HBox(ComponentBuilder.createLabel("Ficheiro de Rotas: "), routesButton),
            applyButton
        );
    }

    /**
     * Sets the dialog events/triggers
     */
    public void setTriggers() {
        stopsButton.setOnAction(e -> {
            stopsFile = requestFilePath();
            stopsButton.setText(stopsFile == null ? UNCHOOSED_TEXT : CHOOSED_TEXT);
        });

        routesButton.setOnAction(e -> {
            routesFile = requestFilePath();
            routesButton.setText(routesFile == null ? UNCHOOSED_TEXT : CHOOSED_TEXT);
        });

        applyButton.setOnAction(e -> {
            try {
                this.dataSet = new DataSet(stopsFile, routesFile);
                this.close();
            } catch (NullPointerException exception) {
                displayAlert(Alert.AlertType.ERROR, "Erro ao importar dataset", "Ambos os ficheiros tem de ser escolhidos!");
            }
        });
    }

    /**
     * Requests a file to the user
     * @return filePath if successful, null if canceled
     */
    public String requestFilePath() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Ficheiro JSON", "*.json")
        );
        fileChooser.setTitle("Escolha um ficheiro JSON");

        File selectedFile = fileChooser.showOpenDialog(this.getOwner());
        return (selectedFile != null) ? selectedFile.getAbsolutePath() : null;
    }

    /**
     * Returns the dataSet of the files
     * @return dataSet of the files
     */
    public DataSet getDataSet() {
        return dataSet;
    }
}
