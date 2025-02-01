package pt.pa.pattern.command;

import pt.pa.model.Stop;
import pt.pa.model.TransportMap;

import java.util.Objects;

/**
 * Command to add a stop
 */
public class AddStopCommand implements Command{
    private TransportMap transportMap;
    private Stop stop;

    /**
     * Class constructor
     * @param transportMap command receiver
     * @param stop to add
     */
    public AddStopCommand(TransportMap transportMap, Stop stop) {
        this.transportMap = Objects.requireNonNull(transportMap);
        this.stop = Objects.requireNonNull(stop);
    }

    @Override
    public void execute() {
        transportMap.addUserStop(stop);
    }

    @Override
    public void unExecute() {
        transportMap.removeUserStop(stop);
    }
}
