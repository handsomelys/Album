package preview;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.net.MalformedURLException;

import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PicPreviewDialog extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JPanel contentpane;
	private PreviewPanel preview;
	public PicPreviewDialog() {
		setTitle("preview");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100,100,557,411);
		contentpane = new JPanel();
		contentpane.setBorder(new EmptyBorder(5,5,5,5));
		contentpane.setLayout(new BorderLayout(0,0));
		setContentPane(contentpane);
		
		JFileChooser fChooser = new JFileChooser();
		contentpane.add(fChooser,BorderLayout.CENTER);
		preview = new PreviewPanel();
		preview.setBorder(new BevelBorder(BevelBorder.LOWERED,null,null,null,null));
		preview.setPreferredSize(new Dimension(300,300));
		fChooser.setAccessory(preview);
		fChooser.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent e) {
				do_propertyChange(e);
			}
		});
		
		fChooser.setFileFilter(new FileNameExtensionFilter("jpg","jpeg","gif","png","bmp"));
	}
	
	protected void do_propertyChange(PropertyChangeEvent e) {
		if(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY == e.getPropertyName()) {
			File pic = (File)e.getNewValue();
			if(pic != null && pic.isFile()) {
				try {
					Image image = getToolkit().getImage(pic.toURL());
					preview.setImage(image);
					preview.repaint();
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}