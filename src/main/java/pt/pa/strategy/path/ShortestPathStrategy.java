package pt.pa.strategy.path;

import pt.pa.model.Path;
import pt.pa.strategy.transport.TransportStrategy;

public class ShortestPathStrategy implements PathStrategy {

    @Override
    public int compare(Path o1, Path o2) {
        return (int)Math.round(o1.getTotalCost() * TransportStrategy.FACTOR - o2.getTotalCost() * TransportStrategy.FACTOR);
    }
}
