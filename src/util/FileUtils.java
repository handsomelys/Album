package util;

import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;

import preview.PreviewPanel;
import preview.ThumbNail;

import java.util.regex.Matcher;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {
    /**
     * @param file file to copy
     * @param dest the desination path
     */
	static boolean ctrl = false;
    public static void copyFile(File file, String dest) {
        String reg = "-copy(\\((\\d+)\\))?";
        Pattern p = Pattern.compile(reg);
        String name = file.getName();
        File nf = Path.of(dest, name).toFile();
        // getting an unique name for copied file
        while (true) {
            if(nf.exists()) {
                Matcher m = p.matcher(name);
                if(m.find()) {
                    // had found the copied format inside file name
                    if (m.group(2) != null) {
                        int c = Integer.parseInt(m.group(2));
                        name = m.replaceFirst(String.format("-copy(%d)", c+1));
                    } else {
                        name = m.replaceFirst("-copy(1)");
                    }
                }
                else {
                    // did not find the copied format, adding it
                    if ((name != null) && (name.length() > 0)) {
                        int dot = name.lastIndexOf('.');
                        if (dot > 0 && dot < (name.length()-1)) {
                            String s = name.substring(0, dot);
                            name = s + "-copy." + name.substring(dot+1);
                        } else
                            name += "-copy";
                    }
                }
                nf = Path.of(dest, name).toFile();
                continue;
            }
            break;
        }
        try {
            Files.copy(file.toPath(), nf.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * @param files files to copy
     * @param dest the desination path
     */
    public static void copyFiles(ArrayList<File> files, String dest) {
        for (File file: files) {
            FileUtils.copyFile(file, dest);
        }
    }

    /**
     * @param file file to rename
     * @param name only name
     */
    public static void renameFile(File file, String name) {
        File nf = Path.of(file.getParent(), name).toFile();
        if (nf.exists()) {
            System.out.println("file exist!");
        } else {
            file.renameTo(nf);
        }
    }
    /**
     * @param files file to rename
     * @param name name prefix
     * @param start index that start from
     * @param bit the bits of later number
     */
    public static void renameFiles(ArrayList<File> files, String name,
        int start, int bit) {
            for (int i = 0; i < files.size(); ++i) {
                String previous = files.get(i).getName();
                String suffix = previous.substring(previous.lastIndexOf("."));
                FileUtils.renameFile(files.get(i),
                    name + String.format("%0"+bit+"d", i) + suffix);
            }
    }

    /**
     * @param file file to delete
     */
    public static void removeFile(File file) {
        if (file.exists() && file.isFile())
            file.delete();
    }
    /**
     * @param files files to delete
     */
    public static void removeFiles(ArrayList<File> files) {
        for (File file: files)
            FileUtils.removeFile(file);
    }
    /**
     * obvious
     */
    public static boolean isPicture(File f) {
        String regex = ".*.(jpg|jpeg|png|bmp|gif)$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        return p.matcher(f.getName()).matches();
    }
    public static int getPicturesCount(File directory) {
        int count = 0;
        for (File f: directory.listFiles())
            count += isPicture(f)? 1: 0;
        return count;
    }
    public static long getPicturesSize(File directory) {
        long total = 0;
        for (File f: directory.listFiles())
            total += isPicture(f)? f.length(): 0;
        return total;
    }
    public static String sizeToString(long size) {
        String[] sign = {"Byte", "KB", "MB", "GB", "TB"};
        int index = 0;
        double length = size;
        while (length > 1024) {
            length /= 1024;
            ++index;
        }
        return String.format("%.2f%s", length, sign[index]);
    }
    /*
    public static void picListener_keyboard(PreviewPanel pp) {
    		for(int i=0;i<pp.pictures.size();i++) {
    			
    		
    		pp.pictures.get(i).addKeyListener(new KeyListener() {
    			
				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void keyPressed(KeyEvent e) {
					// TODO Auto-generated method stub
					if(e.getKeyCode()==KeyEvent.VK_CONTROL) {
						ctrl = true;
						System.out.println("ctrl pressed "+ctrl);
					}
				}

				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub
					if(e.getKeyCode()==KeyEvent.VK_CONTROL) {
						ctrl = false;
						System.out.println("ctrl松开"+ctrl);
					}
				}
    			
    		});
    	}
    }
    */
    public static void picListener_keyboard(JFrame f) {
		
			
		f.requestFocus();
		f.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode()==KeyEvent.VK_CONTROL) {
					ctrl = true;
					//System.out.println("ctrl pressed "+ctrl);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(e.getKeyCode()==KeyEvent.VK_CONTROL) {
					ctrl = false;
					//System.out.println("ctrl松开"+ctrl);
				}
			}
			
		});
	}

    public static void picListener1(PreviewPanel pp) {
    	
    	for(int i=0;i<pp.pictures.size();i++) {
    		pp.pictures.get(i).addMouseListener(new MouseAdapter() {
    			
    			public void mousePressed(MouseEvent e) {
    				
    				int seleted = 0;
    				
    				ThumbNail  selectedLabel;
    				selectedLabel = (ThumbNail) e.getSource();
    				//selectedLabel.setBackground(new java.awt.Color(0,191,255));
    				/*
    				System.out.println(e.getSource() instanceof ThumbNail);
    				if(!(e.getSource() instanceof ThumbNail)) {
    					for(int x=0;x<pp.pictures.size();x++) {
    						pp.pictures.get(x).picture.setBackground(new java.awt.Color(245,245,245));
        					pp.pictures.get(x).selected = false;
    					}
    				}
    				*/
    				//单击取消要放在previewpanel的监听里
    				for(int j=0;j<pp.pictures.size();j++) {
    					if(pp.pictures.get(j).picture.getIcon().equals(selectedLabel.picture.getIcon())) {
    						seleted = j;
    						
    					}
    				}
    				if(!ctrl) {
    					for(int z=0;z<pp.pictures.size();z++) {
    					if(z!=seleted&&pp.pictures.get(z).selected==true) {
    						pp.pictures.get(z).picture.setBackground(new java.awt.Color(245,245,245));
    						pp.pictures.get(z).selected = false;
    					}
    					if(!pp.pictures.get(seleted).selected)
        				{
        					pp.pictures.get(seleted).picture.setBackground(new java.awt.Color(0,191,255));
        					pp.pictures.get(seleted).selected = true;
        				}
        				else if(pp.pictures.get(seleted).selected) {
        					pp.pictures.get(seleted).picture.setBackground(new java.awt.Color(245,245,245));
        					pp.pictures.get(seleted).selected = false;
        				}
    				}
    				}
    				if(ctrl) {
    					pp.pictures.get(seleted).picture.setBackground(new java.awt.Color(0,191,255));
    					pp.pictures.get(seleted).selected = true;
    					pp.pictures.get(seleted).clicktwice++;
    					if(!pp.pictures.get(seleted).selected)
    				{
    					pp.pictures.get(seleted).picture.setBackground(new java.awt.Color(0,191,255));
    					pp.pictures.get(seleted).selected = true;
    				}
    					if(pp.pictures.get(seleted).clicktwice%2==0) {
    						pp.pictures.get(seleted).picture.setBackground(new java.awt.Color(245,245,245));
        					pp.pictures.get(seleted).selected = false;
    					}
    					/*
    					else if(pp.pictures.get(seleted).selected) {
    					pp.pictures.get(seleted).picture.setBackground(new java.awt.Color(245,245,245));
    					pp.pictures.get(seleted).selected = false;
    				}
    				*/
    		
    				
    				/*
    				if(flag == 0) {
    					for(int i=0;i<pp.pictures.size();i++) {
    						pp.pictures.get(seleted).picture.setBackground(new java.awt.Color(245,245,245));
        					pp.pictures.get(seleted).selected = false;
    					}
    				}
    				*/
    				/*
    				if(clickTime%2==1) {
    					pp.pictures.get(seleted).picture.setBackground(new java.awt.Color(0,191,255));
    				}
    				else {
    					pp.pictures.get(seleted).picture.setBackground(new java.awt.Color(245,245,245));
    				}*/
    				
    			}
    			}});
    	}
    }
    public static void picListener2(PreviewPanel pp) {
    		pp.addMouseListener(new MouseAdapter() {
    			
    			@Override
    			public void mousePressed(MouseEvent e) {
    				 pp.sx=e.getX();
    				 pp.sy=e.getY();
    				System.out.println("sx:"+pp.sx);
    				System.out.println("sy:"+pp.sy);
    			}
    			@Override
    			public void mouseReleased(MouseEvent e) {
    				pp.ex=e.getX();
    				pp.ey=e.getY();
    				System.out.println("ex:"+pp.ex);
    				System.out.println("ey:"+pp.ey);
    				pp.current = true;
    				pp.repaint();
    				addSelectedField();
    				
    			}
    			
    			public void addSelectedField() {
    				for(int i=0;i<pp.pictures.size();i++) {
    					if(judge(pp,i,pp.ex,pp.ey,pp.sx,pp.sy)) {
    						pp.pictures.get(i).selected = true;
    						pp.pictures.get(i).picture.setBackground(new java.awt.Color(0,191,255));
    						
    					}
    				}
    			}
    			
    			
    			
    		});
    }
    
    public static void picListener3(PreviewPanel pp) {
    	pp.addMouseMotionListener(new MouseMotionAdapter() {
    		public void mouseDragged(MouseEvent e) {
    			pp.ex = e.getX();
    			pp.ey = e.getY();
    			pp.current = false;
    			pp.repaint();
    		}
    	});
    }
    
	protected static boolean judge(PreviewPanel pp,int i,int ex,int ey,int sx,int sy) {
		
		
		if((pp.pictures.get(i).centerx<sx&&pp.pictures.get(i).centerx>ex)) {
			if(pp.pictures.get(i).centery>sy&&pp.pictures.get(i).centery<ey) {
				System.out.println("x:"+pp.pictures.get(i).centerx+"  y:"+pp.pictures.get(i).centery);
				System.out.println(pp.pictures.get(i).text.getName()+"选中");
				return true;
			}
		}
		else if((pp.pictures.get(i).centerx>sx&&pp.pictures.get(i).centerx<ex)) {
			if((pp.pictures.get(i).centery<sy&&pp.pictures.get(i).centery>ex)) {
				System.out.println("x:"+pp.pictures.get(i).centerx+"  y:"+pp.pictures.get(i).centery);
				System.out.println(pp.pictures.get(i).text.getName()+"选中");
				return true;
			}
		}
		//System.out.println("无任何选中");
		 
		
		
		return false;
	}
}