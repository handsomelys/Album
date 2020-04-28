package event;

import java.util.EventObject;

public class InformationEvent extends EventObject {
    private static final long serialVersionUID = 1L;

    public String command;
    
    public InformationEvent(InformationSource source, String command) {
        super(source);
        this.command = command;
    }
    public String getCommand() {
        return this.command;
    }
}