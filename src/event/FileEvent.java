package event;

import java.io.File;
import java.util.ArrayList;
import java.util.EventObject;

public class FileEvent extends EventObject {
    private static final long serialVersionUID = 1L;

    public String command;
    public ArrayList<File> files;

    public String getCommand() {
        return this.command;
    }
    public ArrayList<File> getFiles() {
        return this.files;
    }
    
    public FileEvent(FileSource source, String command, ArrayList<File> files) {
        super(source);
        this.command = command;
        this.files = files;
    }
}