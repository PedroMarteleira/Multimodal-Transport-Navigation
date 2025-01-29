package pt.pa.view.helpers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Collection;
import java.util.Objects;


/**
 * Helper class to create some components and avoid the repeated code.
 */
public abstract class ComponentBuilder {

    public enum ColorMode {
        LIGHT, DARK;
    }

    private static ColorMode colorMode = ColorMode.LIGHT; //Color mode to draw

    /**
     * Creates a simple javafx label node
     * @param text label text
     * @return  regular JavaFX label with the requested text
     */
    public static Label createLabel(String text) {
        Label label = new Label(text);
        if(colorMode == ColorMode.DARK)
            label.getStyleClass().add("text-light");
        return label;
    }

    /**
     * Sets the color mode
     * @param mode drawing color mode
     */
    public static void setColorMode(ColorMode mode) {
        colorMode = mode;
    }

    /**
     * Creates a simple javafx label node with bold text
     * @param text label text
     * @return  regular JavaFX label with bold text and the requested text
     */
    public static Label createSubtitledLabel(String text) {
        Label label = createLabel(text);
        label.getStyleClass().add("subtitle");
        return label;
    }

    /**
     * Creates a simple javafx label node with title text style
     * @param text label text
     * @return  regular JavaFX label with title text style and the requested text
     */
    public static Label createTitledLabel(String text) {
        Label label = createLabel(text);
        label.getStyleClass().add("title");
        return label;
    }

    /**
     * Creates a javafx Text (allows multiline)
     * @param text javafx Text Component text
     * @return Text javafx component
     */
    public static Text createText(String text) {
        Text txt = new Text(text);
        txt.setFont(Font.font("Consolas"));
        if(colorMode == ColorMode.DARK)
            txt.setFill(Color.WHITE);

        return txt;
    }

    /**
     * Creates a menu (for menubar)
     * @param text menu text
     * @param items menu items
     * @return Menu with the parameters
     */
    public static Menu createMenu(String text, MenuItem... items) {
        Menu menu = new Menu(text);
        menu.getItems().addAll(items);
        return menu;
    }

    /**
     * Creates a menu item
     * @param text Item text
     * @param shortcut Item Shortcut, for example: "Ctrl+S"
     * @param eventHandler Action called when the item is used
     * @return MenuItem with the requested parameters
     */
    public static MenuItem createMenuItem(String text, String shortcut, EventHandler<ActionEvent> eventHandler) {
        MenuItem menuItem = new MenuItem(text);
        if(!shortcut.isEmpty())
            menuItem.setAccelerator(KeyCombination.keyCombination(shortcut));
        menuItem.setOnAction(eventHandler);
        return menuItem;
    }

    /**
     * Creates a checkbox
     * @param text Text show in the checkbox
     * @param selectedItems Collection of selected items, (added and removed when selected/unselected) [can be null]
     * @return Checkbox with the given attributes
     */
    public static CheckBox createCheckBox(String text, Collection<String> selectedItems) {
        CheckBox checkbox = new CheckBox(Objects.requireNonNull(text));
        if(selectedItems != null) {
            checkbox.setOnAction(e -> {
                if (((CheckBox) e.getTarget()).isSelected()) {
                    selectedItems.add(text);
                } else {
                    selectedItems.remove(text);
                }
            });

            if (selectedItems.contains(text))
                checkbox.setSelected(true);
        }
        return checkbox;
    }

    /**
     * Creates a simple button
     * @param text button Text
     * @param eventHandler Action (executed when the button is pressed)
     * @return Button with the given attributes
     */
    public static Button createButton(String text, EventHandler<ActionEvent> eventHandler) {
        Button button = new Button(text);
        button.setOnAction(eventHandler);
        return button;
    }
}
