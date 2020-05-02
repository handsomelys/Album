package event;

import java.util.EventListener;

public interface FileSelectListener extends EventListener {
    public void actionPerformed(FileSelectEvent fse);
}