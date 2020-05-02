package event;

import java.util.EventObject;
import operation.FileOperation;

public class FileOperationEvent extends EventObject {
    private static final long serialVersionUID = 1L;

    public FileOperation operation;

    public FileOperationEvent(Object source) {
        super(source);
    }

}