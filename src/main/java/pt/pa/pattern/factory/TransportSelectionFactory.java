package pt.pa.pattern.factory;

import pt.pa.model.CostField;
import pt.pa.pattern.strategy.transport.LowestCostStrategy;
import pt.pa.pattern.strategy.transport.LowestDistanceStrategy;
import pt.pa.pattern.strategy.transport.LowestDurationStrategy;
import pt.pa.pattern.strategy.transport.TransportStrategy;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Transport Selection Strategy Factory
 * This class is the factory to choose witch transport selection strategy use
 */
public class TransportSelectionFactory {

    /**
     * Factories the strategy
     * @param costField field (cost, distance, duration...) chosen by the user
     * @param availableTransports transports allowed by the user
     * @return TransportStrategy strategy that prioritizes the specified field
     */
    public static TransportStrategy createTransportSelection(CostField costField, Collection<String> availableTransports) {
        final Set<String> transports = Objects.requireNonNull(availableTransports).stream().map(TransportSelectionFactory::unCapitalizeString).collect(Collectors.toSet());
        return switch (costField) {
            case COST -> new LowestCostStrategy(transports);
            case DISTANCE -> new LowestDistanceStrategy(transports);
            default -> new LowestDurationStrategy(transports);
        };
    }

    /**
     * Puts the first letter of the string in lower case
     * @param string string to transform
     * @return same string with first letter of in lower case
     */
    public static String unCapitalizeString(String string) {
        return Objects.requireNonNull(string).substring(0, 1).toLowerCase() + string.substring(1);
    }
}
