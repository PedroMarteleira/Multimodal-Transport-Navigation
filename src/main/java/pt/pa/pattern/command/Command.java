package pt.pa.pattern.command;

/**
 * Command Interface to implement the Command Pattern
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public interface Command {
    /**
     * Executes the command
     */
    void execute();

    /**
     * Does the opposite of the execute (undo)
     */
    void unExecute();
}
