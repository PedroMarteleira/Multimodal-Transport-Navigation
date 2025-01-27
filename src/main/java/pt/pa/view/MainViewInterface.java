package pt.pa.view;

import com.brunomnsilva.smartgraph.graph.Vertex;
import javafx.scene.shape.Path;
import pt.pa.model.Stop;

import java.util.Collection;

public interface MainViewInterface {
    public void displayStopsWithAllTransports(Collection<Stop> stops);
    public void displayPath(Path path);
}
