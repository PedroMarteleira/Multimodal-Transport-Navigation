package pt.pa;

import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graph.GraphEdgeList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pt.pa.model.TransportMap;
import pt.pa.utils.TransportMapLoaderUtil;
import pt.pa.view.MapView;

/**
 * Main class
 *
 * @author amfs
 */
public class Main extends Application {

    /**
     * The default entry point of the application
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        try {
            TransportMap model = TransportMapLoaderUtil.getInstance().getLoadedTransportMap();

            MapView view = new MapView(model.getGraph());
            model.addObservers(view);

            Scene scene = new Scene(view, 1024, 720);
            Stage stage = new Stage(StageStyle.DECORATED);

            stage.setTitle("Projeto PA 2024/25 - Maps");
            stage.setScene(scene);
            stage.show();
        }  catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

    }

}
