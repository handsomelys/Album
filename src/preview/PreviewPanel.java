package preview;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class PreviewPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private Image image;

	public PreviewPanel() {
		setOpaque(false);
		setLayout(null);
	}
	
	public void setImage(Image image) {
		this.image = image;
	}

	@Override
	protected void paintComponent(Graphics g) {
		if(image != null) {
			g.drawImage(image,0,0,getWidth(),getHeight(),this);
		}
		super.paintComponent(g);
	}
}
