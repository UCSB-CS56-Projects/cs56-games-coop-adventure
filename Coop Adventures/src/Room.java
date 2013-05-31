import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class Room{
	
	Image bg;
	Image icon;
	JPanel jPanel;
	Thing[] things;
	
	public <E extends JPanel> Room(E j,String bgImgPath,String iconImgPath){
		this.jPanel= j;
		try {
		    bg = ImageIO.read(new File(bgImgPath));
		    System.out.println("Background image loaded successfully");
		} catch (IOException e) {
			System.out.println("Failed to load background image");
		}
		try{
			icon = ImageIO.read(new File(iconImgPath));
			System.out.println("Icon image loaded successfully");
		}catch (IOException e) {
			System.out.println("Failed to load icon image");
		}
		
		
	}
	
	public Point getIconSize(){
		return new Point(icon.getWidth(jPanel),icon.getHeight(jPanel));
	}
	
	public void draw(Graphics g){
		g.drawImage(bg,0,0,jPanel);
	}
	
	public void drawIcon(Graphics g,Point coord){
		g.drawImage(icon, coord.x, coord.y,jPanel);
	}

}
