package pt.pa.utils;

import com.google.gson.*;

import pt.pa.model.Route;
import pt.pa.model.Stop;
import pt.pa.model.TransportInformation;
import pt.pa.model.TransportMap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Utility using the singleton approach to load the TransportMap Data from the files
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public class TransportMapFileHandlerUtil {

    //Field names:
    private static final String
        STOP_CODE = "stop_code",
        STOP_NAME = "stop_name",
        STOP_LATITUDE = "latitude",
        STOP_LONGITUDE = "longitude",

        STOP_CODE_START = "stop_code_start",
        STOP_CODE_END = "stop_code_end";

    //Default file paths:
    private static final DataSet DEFAULT_DATASET = new DataSet("src/main/resources/dataset/stops.json", "src/main/resources/dataset/routes.json");
    private static final Gson gson = new Gson();

    // Properties:
    private final TransportMap transportMap;
    private final DataSet dataSet;

    /**
     * Class constructor generally used to import the model from the dataset
     * @param dataSet dataset of the files
     */
    public TransportMapFileHandlerUtil(DataSet dataSet) {
        this.transportMap = new TransportMap();
        this.dataSet = dataSet == null ? DEFAULT_DATASET : dataSet;
    }

    /**
     * Class constructor generally used to export the model
     * @param dataSet dataset of the files
     */
    public TransportMapFileHandlerUtil(DataSet dataSet, TransportMap model) {
        this.transportMap = Objects.requireNonNull(model);
        this.dataSet = Objects.requireNonNull(dataSet);
    }

    /**
     * Returns all property names of the TransportInformation
     * @return all property names of the TransportInformation
     */
    private Collection<String> getInformationKeys() {
        return gson.toJsonTree(new TransportInformation()).getAsJsonObject().keySet();
    }

    /**
     * Loads the contents from the
     * @throws IOException if something wrong happens
     */
    public void load() throws IOException {
        loadStops(dataSet.getStopsFilePath());
        loadRoutes(dataSet.getRoutesFilePath());
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
            String code = obj.get(STOP_CODE).getAsString();
            String name = obj.get(STOP_NAME).getAsString();
            double lat = obj.get(STOP_LATITUDE).getAsDouble();
            double lon = obj.get(STOP_LONGITUDE).getAsDouble();
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

        final String someTransportField = getInformationKeys().stream().findFirst().get(); //Can be any, used just to test

        boolean areTransportsLoaded = false; //To only load the transports one time

        for(JsonElement jsonElement : jsonArray) {
            JsonObject obj  = jsonElement.getAsJsonObject(); //Current object

            //Get all existing transports
            if(!areTransportsLoaded) {
                transportMap.setAvailableTransports(obj.keySet().stream().filter(s -> s.startsWith(someTransportField)).map(s -> s.substring(someTransportField.length() + 1)).collect(Collectors.toCollection(TreeSet::new)));
                areTransportsLoaded = true;
            }

            //Get the stops with the req code:
            Stop start = transportMap.getStopWithCode(obj.get(STOP_CODE_START).getAsString());
            Stop end = transportMap.getStopWithCode(obj.get(STOP_CODE_END).getAsString());

            //Create + Add route
            Route route = new Route(start, end);
            transportMap.addRoute(route);

            //Load the information per transport:
            transportMap.getAvailableTransports().forEach(transport -> {
                if(!obj.get(someTransportField + "_" + transport).isJsonNull()) {
                    JsonObject jsonObject = new JsonObject();
                    //Add each property:
                    getInformationKeys().forEach(key -> {
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
     * Exports the full dataset
     * @throws IOException if something wrong happens
     */
    public void exportDataSet() throws IOException {
        exportStops(dataSet.getStopsFilePath());
        exportRoutes(dataSet.getRoutesFilePath());
    }

    /**
     * Exports the stops to a json file
     * @param stopsFilePath file path
     * @throws IOException if something wrong happens
     */
    private void exportStops(String stopsFilePath) throws IOException {
        JsonArray jsonArray = new JsonArray();

        for (Stop stop : transportMap.getStops()) {
            JsonObject obj = new JsonObject();
            obj.addProperty(STOP_CODE, stop.getCode());
            obj.addProperty(STOP_NAME, stop.getName());
            obj.addProperty(STOP_LATITUDE, stop.getLatitude());
            obj.addProperty(STOP_LONGITUDE, stop.getLongitude());
            jsonArray.add(obj);
        }

        Files.writeString(Path.of(stopsFilePath), jsonArray.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    /**
     * Exports the routes to a json file
     * @param routesFilePath file path
     * @throws IOException if something wrong happens
     */
    private void exportRoutes(String routesFilePath) throws IOException {
        JsonArray jsonArray = new JsonArray();

        for (Route route : transportMap.getRoutes()) {
            JsonObject obj = new JsonObject();
            obj.addProperty(STOP_CODE_START, route.getStart().getCode());
            obj.addProperty(STOP_CODE_END, route.getDestination().getCode());

            for (String transport : transportMap.getAvailableTransports()) {
                TransportInformation info = route.getTransportInformation(transport);
                if (info != null) {
                    for (String key : getInformationKeys()) {
                        JsonObject infoObj = gson.toJsonTree(info).getAsJsonObject();
                        obj.addProperty(key + "_" + transport, infoObj.get(key).getAsNumber());
                    }
                } else {
                    getInformationKeys().forEach(key -> obj.add(key + "_" + transport, JsonNull.INSTANCE));
                }
            }

            jsonArray.add(obj);
        }

        Files.writeString(Path.of(routesFilePath), jsonArray.toString(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    /**
     * Returns the loaded transportMap
     * @return loaded transportMap
     */
    public TransportMap getLoadedTransportMap() {
        return transportMap;
    }
}
