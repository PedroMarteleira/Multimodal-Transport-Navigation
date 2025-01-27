package pt.pa;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pt.pa.controller.TransportMapController;
import pt.pa.model.TransportMap;
import pt.pa.utils.TransportMapLoaderUtil;
import pt.pa.view.Components.MapView;
import pt.pa.view.MainView;

import java.nio.file.Path;
import java.nio.file.Paths;

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

        private static final String STYLES_FILE_PATH = "src/main/resources/styles/styles.css";

    @Override
    public void start(Stage primaryStage) {
        try {
            //General application stylesheet:
            Path path = Paths.get(STYLES_FILE_PATH);

            TransportMap model = TransportMapLoaderUtil.getInstance().getLoadedTransportMap();

            MainView view = new MainView(model);

            TransportMapController controller = new TransportMapController(model, view);
            view.setTriggers(controller);

            model.addObservers(view);

            Scene scene = new Scene(view, 1024, 720);
            scene.getStylesheets().add(path.toUri().toString());

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
