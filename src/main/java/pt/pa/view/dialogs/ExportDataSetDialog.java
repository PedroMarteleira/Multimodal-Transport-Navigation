package pt.pa.view.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import pt.pa.utils.DataSet;
import pt.pa.view.helpers.ComponentBuilder;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Lets the user choose the files of the dataSet to export
 */
public class ExportDataSetDialog extends AppDialog<Object> {
    private static final double ITEM_SPACING = 6.0;

    private static final String CHOOSED_TEXT = "Escolhido!", UNCHOOSED_TEXT = "Escolher...";

    private String stopsFile, routesFile;
    private DataSet dataSet;

    private final Button stopsButton, routesButton;
    private final Button applyButton;

    private final Label stopsPathLabel, routesPathLabel;

    /**
     * Class constructor
     */
    public ExportDataSetDialog() {
        stopsButton = new Button(UNCHOOSED_TEXT);
        routesButton = new Button(UNCHOOSED_TEXT);
        applyButton = new Button("Exportar DataSet");
        stopsPathLabel = new Label();
        routesPathLabel = new Label();

        setTitle("Carregar DataSet de ficheiros");
        doLayout();
        setTriggers();
    }

    /**
     * Does the layout
     */
    public void doLayout() {
        getRoot().setSpacing(ITEM_SPACING);
        getRoot().getChildren().addAll(
                new HBox(ComponentBuilder.createSubtitledLabel("Ficheiro de Paragens: "), stopsButton),
                stopsPathLabel,
                new HBox(ComponentBuilder.createSubtitledLabel("Ficheiro de Rotas: "), routesButton),
                routesPathLabel,
                applyButton
        );
    }

    /**
     * Sets the dialog events/triggers
     */
    public void setTriggers() {
        stopsButton.setOnAction(e -> {
            String path = requestFilePath();
            if(path != null) {
                stopsButton.setText(CHOOSED_TEXT);
                stopsPathLabel.setText(path);
                stopsFile = path;
            }
        });

        routesButton.setOnAction(e -> {
            String path = requestFilePath();
            if(path != null) {
                routesButton.setText(CHOOSED_TEXT);
                routesPathLabel.setText(path);
                routesFile = path;
            }
        });

        applyButton.setOnAction(e -> {
            try {
                this.dataSet = new DataSet(stopsFile, routesFile);
                this.close();
            } catch (NullPointerException exception) {
                displayAlert(Alert.AlertType.ERROR, "Erro ao exportar dataset", "Ambos os ficheiros tem de ser especificados!");
            }
        });
    }

    /**
     * Requests a file path to the user
     * @return filePath if successful, null if canceled
     */
    public String requestFilePath() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Ficheiro JSON", "*.json")
        );
        fileChooser.setTitle("Salvar ficheiro JSON");
        fileChooser.setInitialFileName("data.json");

        File selectedFile = fileChooser.showSaveDialog(this.getOwner());

        //Reserve the file name:
        if (selectedFile != null){
            try {
                Files.createFile(Path.of(selectedFile.toURI()));
            } catch (Exception e) {/*Nothing needed*/}
        }
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
