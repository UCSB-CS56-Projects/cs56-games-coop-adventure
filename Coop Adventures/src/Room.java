import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class Room{
	
	Image bg;
	Image icon;
	JPanel jPanel;
	ArrayList<Thing> things;
	
	public <E extends JPanel> Room(E j,String bgImgPath,String iconImgPath,File thingImgDir){
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
		
		things = new ArrayList<Thing>();
		//Import images of things
		if (thingImgDir.isDirectory()) { // make sure it's a directory
            for (final File f : thingImgDir.listFiles()) {
                Image img = null;
                try {
                    img = ImageIO.read(f);
                    things.add(new Thing(jPanel,img));
                } catch (final IOException e) {
                    System.out.println("Failed to load images of things in a room");
                }
            }
        }
		
		
	}
	
	public Point getIconSize(){
		return new Point(icon.getWidth(jPanel),icon.getHeight(jPanel));
	}
	
	public void thingClicked(Point mousePos){
		for(Thing t: things){
			t.clicked(mousePos);
		}
	}
	
	public void draw(Graphics g){
		g.drawImage(bg,0,0,jPanel);
		drawThings(g);
	}
	
	private void drawThings(Graphics g){
		for(Thing t: things){
			t.draw(g);
		}
	}
	
	public void drawIcon(Graphics g,Point coord){
		g.drawImage(icon, coord.x, coord.y,jPanel);
	}

}
