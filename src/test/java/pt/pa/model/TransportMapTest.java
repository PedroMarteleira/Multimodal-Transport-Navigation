package pt.pa.model;

import com.brunomnsilva.smartgraph.graph.Edge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.pa.pattern.strategy.path.ShortestPathStrategy;
import pt.pa.pattern.strategy.transport.LowestDistanceStrategy;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
class TransportMapTest {
    private TransportMap transportMap;

    @BeforeEach
    void setUp() {
        transportMap = new TransportMap();
        Stop amora = new Stop("AM432", "Foros da Amora", 4.43243242, 6.4324323);
        Stop corroios = new Stop("CR432", "Corroios", 3.43243242, 5.0324323);
        Stop almada = new Stop("AL432", "Almada", 1.43243242, 7.0324323);
        transportMap.addStop(amora);
        transportMap.addStop(corroios);
        transportMap.addStop(almada);

        Route route = new Route(amora, corroios);
        Route route1 = new Route(almada, corroios);
        transportMap.addRoute(route);
        transportMap.addRoute(route1);

        route1.putTransport("walk", new TransportInformation(3,2,1));
        route.putTransport("walk", new TransportInformation(3,2,1));
    }

    @Test
    void getStopWithCode() {
        assertEquals(null, transportMap.getStopWithCode("naoexiste"));
        Stop stop = transportMap.getStops().stream().findFirst().get();
        assertEquals(stop, transportMap.getStopWithCode(stop.getCode()));
    }

    @Test
    void addStop() {
        int numberOfStops = transportMap.getStops().size();

        assertThrows(NullPointerException.class, () -> transportMap.addStop(null));

        transportMap.addStop(new Stop("LG432", "Lamego", 3.43223, 4.4324));
        assertEquals(numberOfStops + 1, transportMap.getStops().size());

        transportMap.addStop(new Stop("LX432", "Lisboa", 3.93223, 4.0324));
        assertEquals(numberOfStops + 2, transportMap.getStops().size());
    }

    @Test
    void addRoute() {
        Stop stop1 =  (Stop) transportMap.getStops().toArray()[0];
        Stop stop2 =  (Stop) transportMap.getStops().toArray()[1];

        int numberOfRoutes = transportMap.getRoutes().size();

        assertThrows(NullPointerException.class, () -> transportMap.addRoute(null));

        transportMap.addRoute(new Route(stop1, stop2));
        assertEquals(numberOfRoutes + 1, transportMap.getRoutes().size());

        transportMap.addRoute(new Route(stop1, stop2));
        assertEquals(numberOfRoutes + 2, transportMap.getRoutes().size());
    }

    @Test
    void setAvailableTransports() {
        Set<String> availableTransports = new TreeSet<>();
        availableTransports.add("walk");
        availableTransports.add("boat");
        availableTransports.add("boat");

        assertThrows(NullPointerException.class, () -> transportMap.setAvailableTransports(null));
        assertDoesNotThrow(() -> transportMap.setAvailableTransports(availableTransports));
    }

    @Test
    void getAvailableTransports() {
        assertFalse(transportMap.getAvailableTransports().contains("walk"));
        Set<String> availableTransports = new TreeSet<>();
        availableTransports.add("walk");
        transportMap.setAvailableTransports(availableTransports);
        assertTrue(transportMap.getAvailableTransports().contains("walk"));
    }

    @Test
    void getGraph() {
        assertEquals(3, transportMap.getGraph().vertices().size());
        assertEquals(2, transportMap.getGraph().edges().size());
    }

    @Test
    void getStops() {
        assertEquals(3, transportMap.getStops().size());
    }

    @Test
    void getRoutes() {
        assertEquals(2, transportMap.getRoutes().size());
    }

    @Test
    void getNumberOfUserStops() {
        Stop userStop = new Stop("US432", "User Stop", 2.43243242, 5.4324323);
        transportMap.addUserStop(userStop);
        assertEquals(1, transportMap.getNumberOfUserStops());
    }

    @Test
    void getRouteWithStops() {
        Stop stop1 = (Stop) transportMap.getStops().toArray()[0];
        Stop stop2 = (Stop) transportMap.getStops().toArray()[2];
        Route route = transportMap.getRouteWithStops(stop1, stop2);
        assertNotNull(route);
        assertEquals(stop1, route.getStart());
        assertEquals(stop2, route.getDestination());
    }

    @Test
    void removeUserStop() {
        Stop userStop = new Stop("US432", "User Stop", 2.43243242, 5.4324323);
        transportMap.addUserStop(userStop);
        transportMap.removeUserStop(userStop);
        assertEquals(0, transportMap.getNumberOfUserStops());
    }

    @Test
    void removeRoute() {
        Route route = (Route) transportMap.getRoutes().toArray()[0];
        transportMap.removeRoute(route);
        assertEquals(1, transportMap.getRoutes().size());
    }

    @Test
    void getNumberOfRoutesPerTransport() {
        Map<String, Integer> routesPerTransport = transportMap.getNumberOfRoutesPerTransport();
        assertNotNull(routesPerTransport);
    }

    @Test
    void getNumberOfIsolatedStops() {
        int isolatedStops = transportMap.getNumberOfIsolatedStops();
        assertTrue(isolatedStops >= 0);
    }

    @Test
    void findPath() {
        Stop start = (Stop) transportMap.getStops().toArray()[2];
        Stop end = (Stop) transportMap.getStops().toArray()[0];
        Path path = transportMap.findPath(start, end, new ShortestPathStrategy(), new LowestDistanceStrategy(transportMap.getAvailableTransports()));
        assertNull(path);
    }

    @Test
    void replaceWith() {
        TransportMap newTransportMap = new TransportMap();
        newTransportMap.addStop(new Stop("NEW432", "New Stop", 1.0, 2.0));
        transportMap.replaceWith(newTransportMap);
        assertEquals(1, transportMap.getStops().size());
    }


    @Test
    void getNumberOfStops() {
        assertEquals(3, transportMap.getNumberOfStops());
    }

    @Test
    void getLongestPathOfTransport() {
        Path longestPath = transportMap.getLongestPathOfTransport("walk");
        assertNotNull(longestPath);
    }

    @Test
    void getEdgeOfRoute() {
        Route route = (Route) transportMap.getRoutes().toArray()[0];
        Edge<Route, Stop> edge = transportMap.getEdgeOfRoute(route);
        assertNotNull(edge);
    }
}