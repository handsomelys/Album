package event;

public interface FileSelectSource {
    public void addListener(FileSelectListener fsl);
    public void removeListener(FileSelectListener fsl);
    public void notifyAll(FileSelectEvent fse);
}