package pt.pa.pattern.command;

import pt.pa.model.Stop;
import pt.pa.model.TransportMap;

import java.util.Objects;

/**
 * Command to remove a stop
 */
public class RemoveStopCommand implements Command{
    private TransportMap transportMap;
    private Stop stop;

    /**
     * Class constructor
     * @param transportMap receiver
     * @param stop stop to remove
     */
    public RemoveStopCommand(TransportMap transportMap, Stop stop) {
        this.transportMap = Objects.requireNonNull(transportMap);
        this.stop = Objects.requireNonNull(stop);
    }

    @Override
    public void execute() {
        transportMap.removeUserStop(stop);
    }

    @Override
    public void unExecute() {
        transportMap.addUserStop(stop);
    }
}
