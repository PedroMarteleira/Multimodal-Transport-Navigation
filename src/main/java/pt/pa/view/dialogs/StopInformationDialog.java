package pt.pa.view.dialogs;

import pt.pa.model.Stop;
import pt.pa.view.helpers.ElementCreator;

public class StopInformationDialog extends StandardDialog{

    /**
     * Class constructor
     *
     * @param stop stop to display the information
     */
    public StopInformationDialog(Stop stop) {
        super("Informação da paragem");

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
        );
    }
}
