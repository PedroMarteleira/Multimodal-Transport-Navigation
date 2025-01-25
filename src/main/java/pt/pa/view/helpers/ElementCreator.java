package pt.pa.view.helpers;

import javafx.scene.control.Label;

/**
 * Helper class to create some components and avoid the repeated code.
 */
public abstract class ElementCreator {

    public static Label createLabel(String text) {
        return new Label(text);
    }

    public static Label createSubtitledLabel(String text) {
        Label label = createLabel(text);
        label.getStyleClass().add("subtitle");
        return label;
    }

    public static Label createTitledLabel(String text) {
        Label label = createLabel(text);
        label.getStyleClass().add("title");
        return label;
    }

}
