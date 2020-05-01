package event;

import java.util.EventListener;

public interface CommandListener extends EventListener {
    public void actionPerformed(CommandEvent ce);
}