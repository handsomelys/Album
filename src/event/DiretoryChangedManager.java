package event;

public class DiretoryChangedManager extends Manager {
    public DiretoryChangedManager() {
        super();
    }
    public void up() {
        DiretoryChangedEvent dce = new DiretoryChangedEvent(this, "up");
        notifyAll(dce);
    }
    public void back() {
        DiretoryChangedEvent dce = new DiretoryChangedEvent(this, "back");
        notifyAll(dce);
    }
    public void forward() {
        DiretoryChangedEvent dce = new DiretoryChangedEvent(this, "forward");
        notifyAll(dce);
    }
}