package event;

import java.util.EventObject;

public class DiretoryChangedEvent extends EventObject {
    private static final long serialVersionUID = 1L;

    public String command;
    
    public DiretoryChangedEvent(Manager source, String command) {
        super(source);
        this.command = command;
    }
}