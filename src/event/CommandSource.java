package event;

public interface CommandSource {
    public void addListener(CommandListener cl);
    public void removeListener(CommandListener cl);
    public void notifyAll(CommandEvent ce);
}