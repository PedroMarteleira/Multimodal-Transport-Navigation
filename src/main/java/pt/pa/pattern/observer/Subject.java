package pt.pa.pattern.observer;

import java.util.*;

/**
 * Represents the Subject (default Observer) in the Observer Pattern
 *
 * @author Pedro Marteleira (202300334@estudantes.ips.pt)
 */
public class Subject implements Observable {
    private final Set<pt.pa.pattern.observer.Observer> observers;

    /**
     * Class constructor
     */
    public Subject() {
        observers = new HashSet<>();
    }

    @Override
    public void addObservers(pt.pa.pattern.observer.Observer... observers) {
        this.observers.addAll(Arrays.asList(observers));
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Object obj) {
        observers.forEach(observer -> observer.update(obj));
    }
}
