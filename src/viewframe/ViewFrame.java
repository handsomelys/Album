package viewframe;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import main.Text;
import util.FileUtils;
public class ViewFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<BufferedImage> img;
	private JPanel jpl;
	private JButton turn_next;
	private JButton turn_back;
	private JButton turn_big;
	private JButton turn_small;
	private JButton start;
	private JButton end;
	//private JButton settings;
	private ScrollPane scrollPane;
	private JPanel bottombtn;
	private int contentnum=0;
	private BufferedImage contentpic;
	private int pic_Width,pic_Height;
	private int sizeset=0;
	private Timer autoShow=new Timer();
	private int contentxMove=0,contentyMove=0;
	private boolean isDragged=false;
	private int mousestartx,mousestarty;
	private int nowScrollx,nowScrolly;
	private int shownow;
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
			bottombtn.setBounds(0,jpl.getHeight()-80,jpl.getWidth(),80);
			int tempx=bottombtn.getWidth()/2;
	    	int tempy=bottombtn.getHeight()/2;		
	    	turn_next.setBounds(tempx+5,tempy-15,30,30);
	    	turn_back.setBounds(tempx-35,tempy-15,30,30);
	    	turn_big.setBounds(tempx+40,tempy-15,30,30);
	    	turn_small.setBounds(tempx+75,tempy-15,30,30);
	    	start.setBounds(tempx+110,tempy-15,30,30);
	    	end.setBounds(tempx+145,tempy-15,30,30);
	    	scrollPane.setBounds(0, 0, jpl.getWidth(), jpl.getHeight()-80);
	    	imgAdapt();
	    	drawContent(scrollPane.getWidth()/2-(pic_Width/2),scrollPane.getHeight()/2-(pic_Height/2));
		}
	    	
	};
	
	/*private MouseListener mouse_1=new MouseListener() {
		 public void mouseClicked(MouseEvent e) {
		       
		      }
		      // 按下鼠标时，更改状态，并且记录拖拽起始位置。
		      public void mousePressed(MouseEvent e) {
		        if (!isDragged) {
		          isDragged=true;
		          mousestartx = e.getPoint().getX();
		          
		        }
		      }
		      // 松开鼠标时更改状态
		      public void mouseReleased(MouseEvent e) {
		        if (status == DragStatus.Dragging) {
		          status = DragStatus.Ready;
		        }
		      }
		      public void mouseEntered(MouseEvent e) {
		      }
		      public void mouseExited(MouseEvent e) {
		      }
		    };
	private  MouseMotionListener mouse_2=new MouseMotionListener() {
		      // Java 有拖拽事件，在这个事件中移动图片位置
		      public void mouseDragged(MouseEvent e) {
		        if (status == DragStatus.Dragging) {
		          moveImage(e.getPoint());
		        }
		      }
		      public void mouseMoved(MouseEvent e) {
		      };*/
	private MouseListener mouse_1=new MouseListener() {
		public void mouseClicked(MouseEvent e) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if(isDragged==false) {
				isDragged=true;
				mousestartx=e.getX();
				mousestarty=e.getY();
				nowScrollx=scrollPane.pic_x;
				nowScrolly=scrollPane.pic_y;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if(isDragged)isDragged=false;
			if(pic_Height<=scrollPane.getHeight()&&pic_Width<=scrollPane.getWidth()) {
				int x=scrollPane.getWidth()/2-(pic_Width/2);
		    	int y=scrollPane.getHeight()/2-(pic_Height/2);
				drawContent(x,y);
			}
				
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}
	};
	private MouseMotionListener mouse_2=new MouseMotionListener() {

		@Override
		public void mouseDragged(MouseEvent e) {
			if(isDragged) {
				contentxMove=e.getX()-mousestartx;
				contentyMove=e.getY()-mousestarty;
				
				drawContent(nowScrollx+contentxMove,nowScrolly+contentyMove);
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}
		
	};
	
	
	public ViewFrame(String s,File p,int shownow) {
		super(s);
		
		img=new ArrayList<BufferedImage>();
		
		this.shownow=shownow;
		try {
			if(p.isDirectory())
			opendirectory(p);
			else openfile(p);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//打开文件并创建数组
		this.setTitle(s+" - "+Text.SOFTWARENAME);
		this.setFont(new Font("宋体",Font.PLAIN,20));
		this.setSize(800, 600);
		//this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
        this.setLayout(null);
        
		jpl=new JPanel();            //初始化面板
		jpl.setLayout(null);
		jpl.setBounds(0,0,this.getWidth(),this.getHeight()-40);
		
		bottombtn =new JPanel();                                                 //initbottom();
		bottombtn.setLayout(null);
		bottombtn.setBounds(0,jpl.getHeight()-80,jpl.getWidth(),80);
		bottombtn.setBackground(new Color(245,245,245));
		
		
		initButton();
		
		initscrollpane();
		
		//scrollPane.imgs=img;
		
		
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
		scrollPane.addMouseListener(mouse_1);
		scrollPane.addMouseMotionListener(mouse_2);
		jpl.add(bottombtn);
		this.setMinimumSize(new Dimension(430,0));
		this.setContentPane(jpl);
		this.addComponentListener(componentAdapter);
		//添加自动调节
		if(this.shownow==1) {
			autoShow=new Timer();
			task_autoShow=new TimerTask() {
				public void run(){
					drawnext();
				}
			};
			autoShow.schedule(task_autoShow, 1000,3000);
		}
		this.setVisible(true);
	}
	
	
	
	
	
	
	public void opendirectory(File p) throws IOException {
		File[] list_pic = p.listFiles();
		for (File child:list_pic) {
			if(FileUtils.isPicture(child)) {
				BufferedImage temp = ImageIO.read(child);
				img.add(temp);
			}
		}
	}
	
	public void openfile(File p) throws IOException{
		File father = p.getParentFile();
		File[] list_pic = father.listFiles();
		for (File child:list_pic) {
			if(FileUtils.isPicture(child)) {
				if(p.getAbsolutePath().equals(child.getAbsolutePath()))
					img.add(0, ImageIO.read(child));
				else
					img.add(ImageIO.read(child));
			}
		}
	}
    public void initButton() {
    	turn_back=new JButton();
    	turn_next=new JButton();
    	turn_big=new JButton();
    	turn_small= new JButton();
    	start=new JButton();
    	end= new JButton();
    	
    	turn_big.setIcon(new ImageIcon("resource/turnbig.png"));
    	turn_small.setIcon(new ImageIcon("resource/turnsmall.png"));
    	turn_next.setIcon(new ImageIcon("resource/turnnext.png"));
    	turn_back.setIcon(new ImageIcon("resource/turnback.png"));
    	start.setIcon(new ImageIcon("resource/start.png"));
    	end.setIcon(new ImageIcon("resource/end.png"));
    	
    	turn_big.setBorder(null);
    	turn_small.setBorder(null);
    	turn_next.setBorder(null);
    	turn_back.setBorder(null);
    	start.setBorder(null);
        end.setBorder(null);
    	
    	int tempx=bottombtn.getWidth()/2;
    	int tempy=bottombtn.getHeight()/2;		
    	turn_next.setBounds(tempx+5,tempy-15,30,30);
    	turn_back.setBounds(tempx-35,tempy-15,30,30);
    	turn_big.setBounds(tempx+40,tempy-15,30,30);
    	turn_small.setBounds(tempx+75,tempy-15,30,30);
    	start.setBounds(tempx+110,tempy-15,30,30);
    	end.setBounds(tempx+145,tempy-15,30,30);
    	
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
    	scrollPane=new ScrollPane(img);
    	scrollPane.setBounds(0, 0, jpl.getWidth(), jpl.getHeight()-80);
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
