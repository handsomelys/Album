package preview;

import java.io.File;
import java.util.ArrayList;

import util.FileUtils;

public class PicThreading implements Runnable {
	public ArrayList<Thumbnail> pictures;
	int head;
	int nail;
	public File directory;
	public PreviewPanel previewPanel;
	public ArrayList<File> picFiles;
    private static final int WIDTH1 = 632;
    private static final int HEIGHT1 = 158;
    private static final int WIDTH2 = 166;
    private static final int WIDTH3 = 830;
    private static final int HEIGHT3 = 725;
    private static final int THUMBNAILX = 175;
    private static final int THUMBNAILY = 150;
    private static final int THUMBNAILWIDTH = 120;
    private static final int THUMBNAILHEIGHT = 110;
    private static final int PIC_PER_ROW = 5;
    private static final int EXTEND_X = 50;
    private static final int EXTEND_Y = 30;
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
		for(int i=head;i<nail;i++) {
			//System.out.println("i"+i);
					
					Thumbnail tn = new Thumbnail(picFiles.get(i));

					this.pictures.add(tn);

		}

		for(Thumbnail tn:this.pictures) {
			this.previewPanel.add(tn);
			
		}
		//System.out.println("size:"+pictures.size());

}
}