package event;

import java.util.EventListener;

public interface InformationListener extends EventListener {
    public void actionPerformed(InformationEvent ie);
}