package pt.pa.view.Components;

import javafx.scene.control.TextField;

/**
 * Modified javafx TextField that reads numbers and when the value is invalid the text turns red
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public class NumberTextField extends TextField {

    /**
     * Class constructor
     */
    public NumberTextField() {
        super();

        textProperty().addListener((observable, oldValue, newValue) -> {
            if (isTextValid(getText())) {
                setStyle("-fx-text-fill: black;");
            } else {
                setStyle("-fx-text-fill: red;");
            }
        });
    }

    /**
     * Checks if the input text is a valid number
     * @param text The text to validate
     * @return True if the text is a valid number, false otherwise
     */
    public boolean isTextValid(String text) {
        return text.matches("-?\\d*([.,]\\d*)?");
    }

    /**
     * Returns the numeric value of the text field as a double.
     * @return The numeric value, or 0.0 if the text is invalid.
     * @throws NumberFormatException if the input is invalid
     */
    public double getNumber() throws NumberFormatException{
        String sanitizedText = getText().replace(",", ".");
        return Double.parseDouble(sanitizedText);
    }
}
