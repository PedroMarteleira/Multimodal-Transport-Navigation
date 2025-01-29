package pt.pa.view.Components;

import javafx.scene.layout.VBox;
import pt.pa.model.TransportMap;
import pt.pa.pattern.observer.Observer;
import pt.pa.view.MainView;
import pt.pa.view.helpers.ComponentBuilder;

import java.util.*;

/**
 * Provides a beautiful transparent gray panel with some information about the transport network
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public class StatisticsPanel extends VBox implements Observer {
    private static double ITEM_SPACING = 5.0;

    TransportMap transportMap;

    /**
     * Class constructor
     * @param transportMap model
     */
    public StatisticsPanel(TransportMap transportMap) {
        super();
        this.transportMap = Objects.requireNonNull(transportMap);
        this.getStyleClass().add("information-panel");
        this.setSpacing(ITEM_SPACING);
    }

    /**
     * Generates the ascii table with the number of routes per transport
     * @param numberOfRoutesByTransport Map[TransportName, Number of routes]
     * @return string with the table formated
     */
    public String generateTransportRoutesTable(Map<String, Integer> numberOfRoutesByTransport) {
        final String SEPARATOR = " ";
        final String LEFT_PADDING = "   ";

        final String TOTAL_HEADER_TEXT = "Total";

        // Calculate column with: (largest length + 2)
        int columnWidth = Math.max(TOTAL_HEADER_TEXT.length(), numberOfRoutesByTransport.keySet().stream().map(String::length).max(Integer::compare).orElse(0)) + 2;

        StringBuilder builder = new StringBuilder();

        // Join headers:
        builder.append(LEFT_PADDING);
        builder.append(String.format("%-" + columnWidth + "s", TOTAL_HEADER_TEXT)).append(SEPARATOR);
        numberOfRoutesByTransport.keySet().stream().map(MainView::capitalizeString).forEach(key -> builder.append(String.format("%-" + columnWidth + "s", key)).append(SEPARATOR));

        builder.append("\n");

        //Join values:
        builder.append(LEFT_PADDING);
        builder.append(String.format("%-" + columnWidth + "s",
                numberOfRoutesByTransport.values().stream().reduce(0, Integer::sum))).append(SEPARATOR);
        numberOfRoutesByTransport.values()
                .forEach(value -> builder.append(String.format("%-" + columnWidth + "s", value)).append(SEPARATOR));

        return builder.toString();
    }

    @Override
    public void update(Object obj) {
        ComponentBuilder.setColorMode(ComponentBuilder.ColorMode.DARK);
        this.getChildren().setAll(
                ComponentBuilder.createTitledLabel("Informações da rede:"),
                ComponentBuilder.createSubtitledLabel("Paragens:"),
                ComponentBuilder.createLabel(String.format("• Total de paragens: %3d", transportMap.getNumberOfStops())),
                ComponentBuilder.createLabel(String.format("• Paragens isoladas: %3d", transportMap.getNumberOfIsolatedStops())),
                ComponentBuilder.createLabel(String.format("• Paragens adicionadas: %3d", transportMap.getNumberOfUserStops())),
                ComponentBuilder.createSubtitledLabel("Rotas:"),
                ComponentBuilder.createLabel(String.format("• Total de rotas: %3d", transportMap.getNumberOfRoutes())),
                ComponentBuilder.createLabel("• Por meio de transporte:"),
                ComponentBuilder.createText(generateTransportRoutesTable(transportMap.getNumberOfRoutesPerTransport()))
        );
        ComponentBuilder.setColorMode(ComponentBuilder.ColorMode.LIGHT);
    }
}

