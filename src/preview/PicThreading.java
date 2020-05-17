package preview;

import java.io.File;
import java.util.ArrayList;

public class PicThreading implements Runnable {
    public ArrayList<Thumbnail> pictures;
    public int head;
    public int nail;
    public File directory;
    public PreviewPanel previewPanel;
    public ArrayList<File> picFiles;

    PicThreading(ArrayList<File> picFiles,PreviewPanel previewPanel,ArrayList<Thumbnail> pictures,File directory,int head,int nail){
        this.pictures = pictures;
        this.head = head;
        this.nail = nail;
        this.directory = directory;
        this.previewPanel = previewPanel;
        this.picFiles = picFiles;
    }

    @Override
    public void run() {
        // 同步多线程
        //System.out.println("head"+head);
        //System.out.println("nail"+nail);
        for(int i = head; i < nail; i++) {
            //System.out.println("i"+i);
            Thumbnail t = new Thumbnail(picFiles.get(i));
            this.pictures.add(t);
            this.previewPanel.add(t);
        }
        //System.out.println("size:"+pictures.size());
    }
}