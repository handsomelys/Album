package viewframe;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.*;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.imageio.ImageIO;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.font.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Timer;
import java.util.Calendar;

public class ViewFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<BufferedImage> img;
	private int comment_view;
	private JPanel jpl;
	private JButton turn_next;
	private JButton turn_back;
	private JButton turn_big;
	private JButton turn_small;
	private JLabel image_show;
	private JButton start;
	private JButton end;
	private ScrollPane scrollPane;
	private JPanel bottombtn;
	private Graphics gra;
	private Graphics2D gra2D;
	private int contentnum=0;
	private BufferedImage contentpic;
	private int pic_Width,pic_Height;
	private int sizeset=0;
	private Timer autoShow=new Timer();
	private TimerTask task_autoShow=new TimerTask() {
		public void run(){
			drawnext();
		}
	};
	private ActionListener ac_next=new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			drawnext();
		}
	};
	private ActionListener ac_back=new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			drawback();
		}
	};
	private ActionListener ac_big=new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			drawbig();
		}
	};
	private ActionListener ac_small=new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			drawsmall();
		}
	};
	private ActionListener ac_startAutoShow=new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			autoShow=new Timer();
			task_autoShow=new TimerTask() {
				public void run(){
					drawnext();
				}
			};
			autoShow.schedule(task_autoShow, 1000,3000);
		}
	};
	private ActionListener ac_stopAutoShow=new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			autoShow.cancel();
			autoShow=null;
			task_autoShow.cancel();
			task_autoShow=null;
			
		}
	};
	private ComponentAdapter componentAdapter=new ComponentAdapter() {
		@Override
		public void componentResized(ComponentEvent e) {
			jpl.setBounds(0,0,e.getComponent().getWidth(),e.getComponent().getHeight()-40);
			bottombtn.setBounds(0,(int) (jpl.getHeight()*0.9),jpl.getWidth(),(int)(jpl.getHeight()*0.1));
			int spaceBtwn=(int)(bottombtn.getWidth()*0.01);
	    	int btnXSize=(int)(bottombtn.getWidth()*0.11);
	    	
	    	int btnY_1=(int)(bottombtn.getHeight()*0.33);
	    	int btnYSize1=btnY_1;
	    	
	    	int btnY_2=(int)(bottombtn.getHeight()*0.25);
	    	int btnYSize2=btnY_2*2;		
	    	turn_back.setBounds(2*spaceBtwn+2*btnXSize,btnY_1,btnXSize,btnYSize1);
	    	turn_next.setBounds(4*spaceBtwn+3*btnXSize,btnY_1,btnXSize,btnYSize1);
	    	turn_big.setBounds(5*spaceBtwn+4*btnXSize,btnY_2,btnXSize,btnYSize2);
	    	turn_small.setBounds(6*spaceBtwn+5*btnXSize,btnY_2,btnXSize,btnYSize2);
	    	start.setBounds(7*spaceBtwn+6*btnXSize,btnY_2,btnXSize,btnYSize2);
	    	end.setBounds(8*spaceBtwn+7*btnXSize,btnY_2,btnXSize,btnYSize2);
	    	scrollPane.setBounds(0, 0, jpl.getWidth(), (int)(jpl.getHeight()*0.9));
	    	imgAdapt();
	    	drawContent(scrollPane.getWidth()/2-(pic_Width/2),scrollPane.getHeight()/2-(pic_Height/2));
		}
	    	
	};
	public ViewFrame(String s,File p) {
		super(s);
		img=new ArrayList<BufferedImage>();
		
		comment_view=0;
		try {
			if(p.isDirectory())
			opendirectory(p);
			else openfile(p);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
		}
		//打开文件并创建数组
		this.setTitle("kkp图片浏览");
		this.setFont(new Font("宋体",Font.PLAIN,20));
		this.setSize(1920,1080-40);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
        this.setLayout(null);
        
		jpl=new JPanel();            //初始化面板
		jpl.setLayout(null);
		jpl.setBounds(0,0,this.getWidth(),this.getHeight()-40);
		
		bottombtn =new JPanel();                                                 //initbottom();
		bottombtn.setLayout(null);
		bottombtn.setBounds(0,(int) (jpl.getHeight()*0.9),jpl.getWidth(),(int)(jpl.getHeight()*0.1));
		
		
		initButton();
		
		initscrollpane();
		scrollPane.imgs=img;
		
		
		//draw
		contentpic=img.get(contentnum);
		pic_Height=contentpic.getHeight();pic_Width=contentpic.getWidth();
		int x=scrollPane.getWidth()/2-(pic_Width/2);
    	int y=scrollPane.getHeight()/2-(pic_Height/2);
		drawContent(x,y);
		
		
		
		
		scrollPane.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				//实现滚动换图
				if(e.getWheelRotation()<0)drawback();
				else if(e.getWheelRotation()>0)drawnext();
			}
		});
		jpl.add(bottombtn);
		
		this.setContentPane(jpl);
		this.addComponentListener(componentAdapter);   //添加自动调节
		this.setVisible(true);
		
	}
	
	public void opendirectory(File p) throws IOException {
		File[] list_pic=p.listFiles();
		for(File child:list_pic) {
			if(child.getName().matches(".*\\.jpg$")||child.getName().matches(".*\\.jpeg$")||
			child.getName().matches(".*\\.png$")||child.getName().matches(".*\\.gif$")||
			child.getName().matches(".*\\.bmp$")) {
				BufferedImage temp=ImageIO.read(child);
				img.add(temp);
			}
		}
		
	}
	
	public void openfile(File p) throws IOException{
		File father=p.getParentFile();
		File[] list_pic=father.listFiles();
		for(File child:list_pic) {
			if(child.getName().matches(".*\\.jpg$")||child.getName().matches(".*\\.jpeg$")||
					child.getName().matches(".*\\.png$")||child.getName().matches(".*\\.gif$")||
					child.getName().matches(".*\\.bmp$")) {
						if(p.getAbsolutePath().equals(child.getAbsolutePath()))
							img.add(0, ImageIO.read(child));
						else
						img.add(ImageIO.read(child));
					}
		}
	}
    public void initButton() {
    	turn_back=new JButton("上一页");
    	turn_next=new JButton("下一张");
    	turn_big=new JButton("放大");
    	turn_small= new JButton("缩小");
    	start=new JButton("播放");
    	end= new JButton("停止");
    			
    	int spaceBtwn=(int)(bottombtn.getWidth()*0.01);
    	int btnXSize=(int)(bottombtn.getWidth()*0.11);
    	
    	int btnY_1=(int)(bottombtn.getHeight()*0.33);
    	int btnYSize1=btnY_1;
    	
    	int btnY_2=(int)(bottombtn.getHeight()*0.25);
    	int btnYSize2=btnY_2*2;		
    	turn_back.setBounds(2*spaceBtwn+2*btnXSize,btnY_1,btnXSize,btnYSize1);
    	turn_next.setBounds(4*spaceBtwn+3*btnXSize,btnY_1,btnXSize,btnYSize1);
    	turn_big.setBounds(5*spaceBtwn+4*btnXSize,btnY_2,btnXSize,btnYSize2);
    	turn_small.setBounds(6*spaceBtwn+5*btnXSize,btnY_2,btnXSize,btnYSize2);
    	start.setBounds(7*spaceBtwn+6*btnXSize,btnY_2,btnXSize,btnYSize2);
    	end.setBounds(8*spaceBtwn+7*btnXSize,btnY_2,btnXSize,btnYSize2);
    	bottombtn.add(turn_next);
    	bottombtn.add(turn_back);bottombtn.add(turn_big);bottombtn.add(turn_small);bottombtn.add(start);bottombtn.add(end);
    	turn_next.addActionListener(ac_next);
    	turn_back.addActionListener(ac_back);
    	turn_big.addActionListener(ac_big);
    	turn_small.addActionListener(ac_small);
    	start.addActionListener(ac_startAutoShow);
    	end.addActionListener(ac_stopAutoShow);
    }
    
    public void initscrollpane() {
    	scrollPane=new ScrollPane(img);/* {
    		/**
			 * 
			 
			private static final long serialVersionUID = 1L;
			public int pic_Width=0;
    		public int pic_Height=0;
    		public ArrayList<BufferedImage> imgs=null;
    		public int contentnum;
    		@Override
    		protected void paintComponent(Graphics g) {
    			super.paintComponent(g);
    			g.clearRect(0, 0, this.getWidth(), this.getHeight());
    			g.drawImage(imgs.get(contentnum),this.getWidth()/2,this.getHeight()/2,pic_Width,pic_Height,null);
    		}
    	};*/
    	scrollPane.setBounds(0, 0, jpl.getWidth(), (int)(jpl.getHeight()*0.9));
    	scrollPane.setBackground(null);
    	jpl.add(scrollPane);
    }
    
    public void drawContent(int x,int y) {
    	scrollPane.pic_Width=pic_Width;      //想在哪画在哪画
    	scrollPane.pic_Height=pic_Height;
    	scrollPane.pic_x=x;
    	scrollPane.pic_y=y;
    	scrollPane.contentnum=contentnum;
    	scrollPane.repaint();
    	
    }
    
    public void drawnext() {
    	sizeset=0;
    	contentnum=(contentnum+1)%img.size();      //我先加一，你们随意
    	contentpic=img.get(contentnum);
		pic_Height=contentpic.getHeight();pic_Width=contentpic.getWidth();
		imgAdapt();                                     //调整长宽
		int x=scrollPane.getWidth()/2-(pic_Width/2);     //直取中心
    	int y=scrollPane.getHeight()/2-(pic_Height/2);
		drawContent(x,y);
    }
    
    public void drawback() {
    	sizeset=0;
    	contentnum=(contentnum-1);      //两行实现完美的回退，不会因为是第一页而回退不了啦，
    	if(contentnum<0)contentnum+=img.size();
    	contentpic=img.get(contentnum);
		pic_Height=contentpic.getHeight();pic_Width=contentpic.getWidth();
		imgAdapt();                         //调整长宽
		int x=scrollPane.getWidth()/2-(pic_Width/2);
    	int y=scrollPane.getHeight()/2-(pic_Height/2);
		drawContent(x,y);
    }
    
    public void drawbig() {
    	if(sizeset>=10)return;
    	sizeset++;
    	pic_Height=(int)(pic_Height*1.1);pic_Width=(int)(pic_Width*1.1);
    	int x=scrollPane.getWidth()/2-(pic_Width/2);
    	int y=scrollPane.getHeight()/2-(pic_Height/2);
		drawContent(x,y);
    	
    }
    public void drawsmall() {
    	if(sizeset<=-10)return;
    	sizeset--;
    	pic_Height=(int)(pic_Height/1.1);pic_Width=(int)(pic_Width/1.1);
    	int x=scrollPane.getWidth()/2-(pic_Width/2);
    	int y=scrollPane.getHeight()/2-(pic_Height/2);
		drawContent(x,y);
    	
    }
    public void imgAdapt() {
    	pic_Height=contentpic.getHeight();pic_Width=contentpic.getWidth();
    	if(pic_Height>scrollPane.getHeight()||pic_Width>scrollPane.getWidth()) {
    		double scale=Math.max((double)pic_Height/scrollPane.getHeight() , (double)pic_Width/scrollPane.getWidth());
    		pic_Height=(int)(pic_Height/scale);pic_Width=(int)(pic_Width/scale);
    				}
    }
    
}
