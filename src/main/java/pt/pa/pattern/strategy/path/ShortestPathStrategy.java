package pt.pa.pattern.strategy.path;

import pt.pa.model.Path;
import pt.pa.pattern.strategy.transport.TransportStrategy;

/**
 * This strategy prioritize cheaper paths over the expensive ones
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public class ShortestPathStrategy implements PathStrategy {
    @Override
    public int compare(Path o1, Path o2) {
        return (int)Math.round(o1.getTotalCost() * TransportStrategy.FACTOR - o2.getTotalCost() * TransportStrategy.FACTOR);
    }
}
