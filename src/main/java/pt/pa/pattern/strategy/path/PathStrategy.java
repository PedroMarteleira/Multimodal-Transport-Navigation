package pt.pa.pattern.strategy.path;

import pt.pa.model.Path;

import java.util.Comparator;

/**
 * Strategy used to order the paths
 * This strategy is a comparator used in the algorithm
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public interface PathStrategy extends Comparator<Path> {/* Comparator */}
