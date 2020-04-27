package event;

import java.util.EventObject;

public class DirectoryUpdatedEvent extends EventObject {
    private static final long serialVersionUID = 1L;

    public String command;
    
    public DirectoryUpdatedEvent(Manager source, String command) {
        super(source);
        this.command = command;
    }
}