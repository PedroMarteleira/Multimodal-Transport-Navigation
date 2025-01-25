package pt.pa.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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

        transportMap.addRoute(new Route(amora, corroios));
        transportMap.addRoute(new Route(almada, corroios));
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
        List<String> availableTransports = new ArrayList<>();
        availableTransports.add("walk");
        availableTransports.add("boat");
        availableTransports.add("boat");

        assertThrows(NullPointerException.class, () -> transportMap.setAvailableTransports(null));
        assertDoesNotThrow(() -> transportMap.setAvailableTransports(availableTransports));
    }

    @Test
    void getAvailableTransports() {
        assertFalse(transportMap.getAvailableTransports().contains("walk"));
        List<String> availableTransports = new ArrayList<>();
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
}