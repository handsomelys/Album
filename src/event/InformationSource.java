package event;

public interface InformationSource {
    public void addListener(InformationListener e);
    public void removeListener(InformationListener e);
    public void notifyAll(InformationEvent e);
}