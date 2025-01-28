package pt.pa.exception;

/**
 * Enumerable with the error/dialog texts of the application
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */

public enum ErrorMessages {
    INVALID_STOP_CODE, INVALID_STOP_NAME, NEGATIVE_DURATION, NEGATIVE_DISTANCE, INVALID_TRANSPORT, MISSING_TRANSPORT;

    @Override
    public String toString() {
        return switch (this) {
            case INVALID_STOP_CODE -> "Código de paragem inválido!";
            case INVALID_STOP_NAME -> "Nome de paragem inválido!";
            case NEGATIVE_DURATION -> "A duração não pode ser negativa!";
            case NEGATIVE_DISTANCE -> "A distância não pode ser negativa!";
            case INVALID_TRANSPORT -> "Transporte inválido!";
            case MISSING_TRANSPORT -> "Route missing transport!";
        };
    }
}
