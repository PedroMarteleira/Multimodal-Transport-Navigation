package pt.pa.pattern.observer;

/**
 * Represents the Observer in the Observer Pattern
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public interface Observer {

    /**
     * Method called when a change occurs in the subject
     * @param obj optional data
     */
    void update(Object obj);
}
