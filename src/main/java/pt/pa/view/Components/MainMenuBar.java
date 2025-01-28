package pt.pa.view.Components;

import javafx.scene.control.MenuBar;
import pt.pa.controller.TransportMapController;
import pt.pa.view.helpers.ComponentBuilder;

/**
 * Provides that classic top menu present on the most classic apps
 */
public class MainMenuBar extends MenuBar {

    /**
     * Class constructor
     */
    public MainMenuBar() {
        super();
    }

    /**
     * Initializes the menu bar components
     * @param controller controller (mvc)
     */
    public void init(TransportMapController controller) {
        this.getMenus().addAll(
                ComponentBuilder.createMenu("Ficheiro",
                        ComponentBuilder.createMenuItem("Abrir...", "Ctrl+O", e -> {
                            System.out.println("Ola a todos");
                        }),
                        ComponentBuilder.createMenuItem("Exportar...", "Ctrl+S", e -> {}),
                        ComponentBuilder.createMenuItem("Sair", "Alt+F4", e-> controller.doExit())
                ),
                ComponentBuilder.createMenu("Métricas",
                        ComponentBuilder.createMenuItem("Paragens com todos os transportes...", "",e -> controller.doShowStopsWithAllTransports()),
                        ComponentBuilder.createMenuItem("Percurso mais longo de comboio...", "",e -> controller.doShowBiggestPathOfTrain()),
                        ComponentBuilder.createMenuItem("Percurso mais longo de autocarro...", "",e -> controller.doShowBiggestPathOfBus()),
                        ComponentBuilder.createMenuItem("Percurso mais longo a pé...", "",e -> controller.doShowBiggestPathOfWalk())
                )
        );
    }
}
