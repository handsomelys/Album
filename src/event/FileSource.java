package event;

public interface FileSource {
    public void addListener(FileListener fl);
    public void removeListener(FileListener fl);
    public void notifyAll(FileEvent fe);
}