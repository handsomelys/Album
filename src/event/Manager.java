package event;

import java.util.EventObject;
import java.util.HashSet;

public class Manager {
    protected HashSet<InformationListener> listeners;

    public Manager() {
        listeners = new HashSet<InformationListener>();
    }

    public void addListener(InformationListener e) {
        listeners.add(e);
    }
    public void removeListener(InformationListener e) {
        listeners.remove(e);
    }
    public void notifyAll(EventObject e) {
        for (InformationListener l: listeners)
            l.change();
    }
}