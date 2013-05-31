import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import javax.swing.JPanel;


public class Thing {
	JPanel jPanel;
	Image img;
	
	public <E extends JPanel> Thing(E jPanel, Image img){
		this.jPanel = jPanel;
		this.img = img;
		
	}
	
	public void draw(Graphics g){
		g.drawImage(img, 0, 0,jPanel);
	}
	
	public void clicked(Point mousePos){
		if(
				mousePos.x>0 &&
				mousePos.x<img.getWidth(jPanel) &&
				mousePos.y>0 &&
				mousePos.y<img.getHeight(jPanel)
				){
			System.out.println("Object clicked");
			
		}
	}

}
