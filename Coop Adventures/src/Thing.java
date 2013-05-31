import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


public class Thing {
	private String name;
	private JPanel jPanel;
	private BufferedImage img,animation;
	private boolean animate;
	
	public <E extends JPanel> Thing(String name, E jPanel, BufferedImage img){
		this.name = name;
		this.jPanel = jPanel;
		this.img = img;
		this.animate = false;
		this.animation = null;	//animations are added after the creation of the Thing
	}
	
	public String getName(){
		return name;
	}
	
	public void addAnimation(BufferedImage animation){
		this.animation = animation;
	}
	
	public void draw(Graphics g){
		if(!animate){
			g.drawImage(img, 0, 0,jPanel);
		}else if(animation != null){
			g.drawImage(animation, 0, 0,jPanel);
		}
	}
	
	public void clicked(Point mousePos){
		if(
				mousePos.x>0 &&
				mousePos.x<img.getWidth(jPanel) &&
				mousePos.y>0 &&
				mousePos.y<img.getHeight(jPanel)
				){
			
			int transparentColorID = 16777215;
			if(img.getRGB(mousePos.x, mousePos.y)!=transparentColorID){
				System.out.println("Object clicked with color: " + img.getRGB(mousePos.x, mousePos.y));
				animate = true;
			}
		}
	}

}
