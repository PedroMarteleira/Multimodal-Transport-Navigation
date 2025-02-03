package pt.pa.utils;

import com.google.gson.*;

import pt.pa.model.Route;
import pt.pa.model.Stop;
import pt.pa.model.TransportInformation;
import pt.pa.model.TransportMap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Utility using the singleton approach to load the TransportMap Data from the files
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public class TransportMapLoaderUtil {

    //Default file paths:
    private static final DataSet DEFAULT_DATASET = new DataSet("src/main/resources/dataset/stops.json", "src/main/resources/dataset/routes.json");

    private static TransportMapLoaderUtil instance;

    // Properties:
    private final TransportMap transportMap;

    /**
     * Class constructor
     * @param dataSet dataset of the files
     * @throws IOException if some error occurs reading/parsing the file
     */
    public TransportMapLoaderUtil(DataSet dataSet) throws IOException {
        this.transportMap = new TransportMap();
        loadStops(dataSet.getStopsFilePath());
        loadRoutes(dataSet.getRoutesFilePath());
    }

    /**
     * Returns the singleton instance
     * @return this (singleton instance)
     * @throws IOException if some error occurs reading/parsing the file (only if not loaded)
     */
    public static TransportMapLoaderUtil getInstance() throws IOException {
        if (instance == null) {
            instance = new TransportMapLoaderUtil(DEFAULT_DATASET);
        }
        return instance;
    }

    /**
     * Aux function to parse the stops file
     * @param stopsFilePath json file path containing the stops
     * @throws IOException if some error occurs reading/parsing the file
     */
    private void loadStops(String stopsFilePath) throws IOException {
        final String fileContent = Files.readString(Path.of(stopsFilePath));
        JsonArray jsonArray = JsonParser.parseString(fileContent).getAsJsonArray();

        jsonArray.forEach(jsonElement -> {
            JsonObject obj  = jsonElement.getAsJsonObject();
            String code = obj.get("stop_code").getAsString();
            String name = obj.get("stop_name").getAsString();
            double lat = obj.get("latitude").getAsDouble();
            double lon = obj.get("longitude").getAsDouble();
            transportMap.addStop(new Stop(code, name, lat, lon));
        });
    }

    /**
     * Aux function to parse the routes file
     * @param routesFilePath json file path containing the routes
     * @throws IOException if some error occurs reading/parsing the file
     */
    private void loadRoutes(String routesFilePath) throws IOException {
        //Read all the content of the file:
        final String fileContent = Files.readString(Path.of(routesFilePath));
        JsonArray jsonArray = JsonParser.parseString(fileContent).getAsJsonArray();

        //Get the existing metrics of route/transport dynamically
        Gson gson = new Gson();
        final Set<String> informationKeys = gson.toJsonTree(new TransportInformation()).getAsJsonObject().keySet();
        final String someTransportField = informationKeys.stream().findFirst().get(); //Can be any, used just to test

        boolean areTransportsLoaded = false; //To only load the transports one time

        for(JsonElement jsonElement : jsonArray) {
            JsonObject obj  = jsonElement.getAsJsonObject(); //Current object

            //Get all existing transports
            if(!areTransportsLoaded) {
                transportMap.setAvailableTransports(obj.keySet().stream().filter(s -> s.startsWith(someTransportField)).map(s -> s.substring(someTransportField.length() + 1)).collect(Collectors.toCollection(TreeSet::new)));
                areTransportsLoaded = true;
            }

            //Get the stops with the req code:
            Stop start = transportMap.getStopWithCode(obj.get("stop_code_start").getAsString());
            Stop end = transportMap.getStopWithCode(obj.get("stop_code_end").getAsString());

            //Create + Add route
            Route route = new Route(start, end);
            transportMap.addRoute(route);

            //Load the information per transport:
            transportMap.getAvailableTransports().forEach(transport -> {
                if(!obj.get(someTransportField + "_" + transport).isJsonNull()) {
                    JsonObject jsonObject = new JsonObject();
                    //Add each property:
                    informationKeys.forEach(key -> {
                        final String propertyName = key + "_" + transport;
                        jsonObject.addProperty(key, obj.get(propertyName).getAsNumber());
                    });
                    //Cast the json to the class:
                    TransportInformation information = gson.fromJson(jsonObject, TransportInformation.class);
                    route.putTransport(transport, information);
                }
            });
        }
    }

    /**
     * Returns the loaded transportMap
     * @return loaded transportMap
     */
    public TransportMap getLoadedTransportMap() {
        return transportMap;
    }
}
