package pt.pa.exception;

/**
 * Thrown when the requested transport is not available in a route
 */
public class MissingTransportException extends Exception {

    /**
     * Class constructor
     */
    public MissingTransportException() {
        super(ErrorMessages.MISSING_TRANSPORT.toString());
    }
}
