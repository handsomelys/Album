package event;

import java.util.EventObject;

public class CommandEvent extends EventObject {
    private static final long serialVersionUID = 1L;

    public String[] command;
    
    public String[] getCommand() {
        return this.command;
    }
    
    public CommandEvent(CommandSource source, String... command) {
        super(source);
        this.command = command;
    }
}