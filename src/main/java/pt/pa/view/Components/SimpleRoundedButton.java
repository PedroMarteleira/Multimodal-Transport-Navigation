package pt.pa.view.Components;

import javafx.scene.control.Button;

import java.util.Objects;

/**
 * Rounded button that expands when selected
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public class SimpleRoundedButton extends Button {

    /**
     * Class constructor
     * @param text button text
     * @param hoverText button text when the mouse hover
     */
    public SimpleRoundedButton(String text, String hoverText) {
        super(text);
        Objects.requireNonNull(hoverText);

        getStyleClass().add("simple-rounded-button");
        this.hoverProperty().addListener((observable, oldValue, newValue) -> {
           if (newValue) {
               setText(hoverText);
           } else {
               setText(text);
           }
        });
    }
}
