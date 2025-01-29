package pt.pa.view.Components;

import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

import java.util.Objects;

/**
 * Provides a menu with a close button that shows in the side of the screen
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public abstract class SideMenu extends VBox {
    private Button quitButton;
    private Pane parent;

    /**
     * Class constructor
     * @param parent Parent node (to make this work)
     */
    public SideMenu(Pane parent) {
        super();
        quitButton = new Button("Voltar");
        this.parent = Objects.requireNonNull(parent);
        this.getStyleClass().add("side-menu");

        doLayout();
        setTriggers();
    }

    /**
     * Displays the menu
     */
    public void show() {
        parent.getChildren().add(this);
    }

    /**
     * Sets the triggers
     */
    private void setTriggers() {
        quitButton.setOnAction(e -> {
            onQuit();
            parent.getChildren().remove(this);
        });
    }

    /**
     * Does the menu layout
     */
    private void doLayout() {
        this.getChildren().add(quitButton);
        this.setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Clears all the controls to the default
     */
    public void clear() {
        this.getChildren().setAll(quitButton);
    }

    /**
     * Function executed when the menu is closed
     */
    protected abstract void onQuit();
}
