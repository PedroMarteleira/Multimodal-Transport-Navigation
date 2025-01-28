package pt.pa.strategy.path;

import pt.pa.model.Path;

import java.util.Comparator;

public interface PathStrategy extends Comparator<Path> {
    public int compare(Path o1, Path o2);
}
