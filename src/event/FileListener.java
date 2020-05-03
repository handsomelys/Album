package event;

import java.util.EventListener;

public interface FileListener extends EventListener {
    public void actionPerformed(FileEvent fe);
}