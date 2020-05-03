package event;

import java.util.EventObject;

public class FileEvent extends EventObject {
    private static final long serialVersionUID = 1L;

    public String command;

    public String getCommand() {
        return this.command;
    }
    
    public FileEvent(FileSource source, String command) {
        super(source);
    }
}