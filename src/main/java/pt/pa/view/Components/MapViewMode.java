package pt.pa.view.Components;

import java.nio.file.Path;

public enum MapViewMode {
    TERRAIN, DARK, MAP, SATELLITE;

    private final static String FOLDER = "src/main/resources/images/";

    public Path getFilePath() {
        return switch (this) {
            case TERRAIN -> Path.of(FOLDER, "terrain.png");
            case DARK -> Path.of(FOLDER, "dark.png");
            case MAP -> Path.of(FOLDER, "map.png");
            case SATELLITE -> Path.of(FOLDER, "satellite.png");
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case TERRAIN -> "Normal";
            case DARK -> "Escuro";
            case MAP -> "Mapa";
            case SATELLITE -> "Satelite";
        };
    }
}
