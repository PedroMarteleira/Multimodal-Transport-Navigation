package pt.pa.view.dialogs;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import pt.pa.model.Stop;

public class StopInformationDialog extends StandardDialog{
    private static final Paint TEXT_COLOR = Color.color(0xe0, 0xe0, 0xe0);
    /**
     * Class constructor
     *
     * @param stop stop to display the information
     */
    public StopInformationDialog(Stop stop) {
        super("Informação da paragem");
        /*
        getRoot().getChildren().setAll(
                ElementCreator.createTitledLabel("Informação da Paragem"),
                ElementCreator.createSubtitledLabel("Código"),
                ElementCreator.createLabel(stop.getCode()),
                ElementCreator.createSubtitledLabel("Nome"),
                ElementCreator.createLabel(stop.getName()),
                ElementCreator.createSubtitledLabel("Latitude"),
                ElementCreator.createLabel(Double.toString(stop.getLatitude())),
                ElementCreator.createSubtitledLabel("Longitude"),
                ElementCreator.createLabel(Double.toString(stop.getLongitude()))
        );*/
    }
}
