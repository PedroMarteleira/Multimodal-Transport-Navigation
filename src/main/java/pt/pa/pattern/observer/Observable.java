package pt.pa.pattern.observer;

/**
 * Represents the Observable in the Observer Pattern
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public interface Observable {

    /**
     * Adds observers to the collection of observers
     * @param observer observers to add
     */
    void addObservers(Observer... observer);

    /**
     * Removes observers from the collection of observers
     * @param observer to remove
     */
    void removeObserver(Observer observer);

    /**
     * Notify all observers associated (call this function when a relevant change occurs)
     * @param obj optional data
     */
    void notifyObservers(Object obj);
}
