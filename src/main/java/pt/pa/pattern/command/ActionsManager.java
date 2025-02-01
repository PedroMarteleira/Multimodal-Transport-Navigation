package pt.pa.pattern.command;

import java.util.Collection;
import java.util.Stack;

/**
 * Stores and restores the commands executed
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public class ActionsManager {
    private Stack<Command> actionHistory;

    /**
     * Class constructor
     */
    public ActionsManager() {
        actionHistory = new Stack<>();
    }

    /**
     * Executes the given command
     * @param command command to execute
     */
    public void execute(Command command) {
        command.execute();
        actionHistory.push(command);
    }

    /**
     * Returns the action history
     * @return history of command executed
     */
    public Collection<Command> getActionHistory() {
        return actionHistory;
    }

    /**
     * Undo the last command done
     */
    public void undo() {
        Command cmd = actionHistory.pop();
        cmd.unExecute();
    }
}
