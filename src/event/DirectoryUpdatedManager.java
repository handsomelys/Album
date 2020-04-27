package event;

public class DirectoryUpdatedManager extends Manager {
    public DirectoryUpdatedManager() {
        super();
    }
    public void up() {
        DirectoryUpdatedEvent dce = new DirectoryUpdatedEvent(this, "up");
        notifyAll(dce);
    }
    public void back() {
        DirectoryUpdatedEvent dce = new DirectoryUpdatedEvent(this, "back");
        notifyAll(dce);
    }
    public void forward() {
        DirectoryUpdatedEvent dce = new DirectoryUpdatedEvent(this, "forward");
        notifyAll(dce);
    }
}