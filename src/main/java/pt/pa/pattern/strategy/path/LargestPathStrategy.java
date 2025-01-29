package pt.pa.pattern.strategy.path;

import pt.pa.model.Path;
import pt.pa.pattern.strategy.transport.TransportStrategy;

/**
 * This strategy prioritize expensive paths over the cheaper ones
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public class LargestPathStrategy implements PathStrategy {

    @Override
    public int compare(Path o1, Path o2) {
        return (int)Math.round(o2.getTotalCost() * TransportStrategy.FACTOR - o1.getTotalCost() * TransportStrategy.FACTOR);
    }
}
