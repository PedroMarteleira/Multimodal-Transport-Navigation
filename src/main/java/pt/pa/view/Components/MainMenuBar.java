package pt.pa.view.Components;

import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import pt.pa.model.TransportMap;
import pt.pa.model.TransportMapController;
import pt.pa.view.MainView;
import pt.pa.view.dialogs.HistoryDialog;
import pt.pa.view.dialogs.PutWalkingRouteDialog;
import pt.pa.view.dialogs.StopCreationDialog;
import pt.pa.view.dialogs.StopRemovalDialog;
import pt.pa.view.helpers.ComponentBuilder;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Provides that classic top menu present on the most classic apps
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public class MainMenuBar extends MenuBar {

    private MainView mainView;

    /**
     * Class constructor
     */
    public MainMenuBar(MainView mainView) {
        super();
        this.mainView = Objects.requireNonNull(mainView);
    }

    /**
     * Initializes the menu bar components
     * @param controller controller (mvc)
     */
    public void init(TransportMapController controller) {
        this.getMenus().addAll(
                ComponentBuilder.createMenu("Ficheiro",
                        ComponentBuilder.createMenuItem("Abrir...", "Ctrl+O", e -> controller.doLoadDataSet()),
                        ComponentBuilder.createMenuItem("Exportar...", "Ctrl+S", e -> controller.doExportData()),
                        ComponentBuilder.createMenuItem("Histórico", "Ctrl+H", e -> new HistoryDialog(controller).show()),
                        ComponentBuilder.createMenuItem("Sair", "Alt+F4", e-> controller.doExit())
                ),
                ComponentBuilder.createMenu("Métricas",
                        ComponentBuilder.createMenuItem("Paragens com todos os transportes...", "",e -> controller.doShowStopsWithAllTransports()),
                        ComponentBuilder.createMenuItem("Percurso mais longo de comboio...", "",e -> controller.doShowBiggestPathOfTrain()),
                        ComponentBuilder.createMenuItem("Percurso mais longo de autocarro...", "",e -> controller.doShowBiggestPathOfBus()),
                        ComponentBuilder.createMenuItem("Percurso mais longo a pé...", "",e -> controller.doShowBiggestPathOfWalk())
                ),
                ComponentBuilder.createMenu("Caminho",
                        ComponentBuilder.createMenuItem("Caminho mais curto...", "", e -> mainView.showFindShortestPathSetupMenu(controller))
                ),
                ComponentBuilder.createMenu("Paragens",
                        ComponentBuilder.createMenuItem("Adicionar...", "", e -> new StopCreationDialog(controller).show()),
                        ComponentBuilder.createMenuItem("Remover...", "", e -> new StopRemovalDialog(controller).show())
                ),
                ComponentBuilder.createMenu("Rotas",
                        ComponentBuilder.createMenuItem("Adicionar/Atualizar rota a pé...", "", e -> new PutWalkingRouteDialog(controller).show())
                ),
                ComponentBuilder.createMenu("Visualização",
                        Arrays.stream(MapViewMode.values()).map(v -> ComponentBuilder.createMenuItem(v.toString(), "", e -> controller.doSetViewMode(v))).toArray(MenuItem[]::new)
                )
        );
    }
}
