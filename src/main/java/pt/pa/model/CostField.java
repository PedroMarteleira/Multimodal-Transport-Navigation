package pt.pa.model;

/**
 * Cost fields (corresponding to TransportInformation attributes)
 *
 * @author Pedro Marteleira (20230334@estudantes.ips.pt)
 */
public enum CostField {
    COST, DISTANCE, DURATION;

    @Override
    public String toString() {
        return switch (this) {
            case COST -> "Imp. Ambiental";
            case DURATION -> "Duração";
            case DISTANCE -> "Distância";
        };
    }
}
