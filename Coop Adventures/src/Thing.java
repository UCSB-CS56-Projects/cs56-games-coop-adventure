import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class Thing {
	JPanel jPanel;
	Image img;
	
	public <E extends JPanel> Thing(E jPanel, String imgPath){
		this.jPanel = jPanel;
		
		try {
		    img = ImageIO.read(new File(imgPath));
		    System.out.println("Thing image loaded successfully");
		} catch (IOException e) {
			System.out.println("Failed to load thing image");
		}
		
	}
	

}
