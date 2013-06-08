import java.awt.image.BufferedImage;

import javax.swing.JPanel;


public class Teleport extends Thing{
	private String room;
	private Game game;
	public <E extends JPanel> Teleport(String name, E jPanel, BufferedImage img,Game game,String room){
		super(name,jPanel,img);
		this.game = game;
		
		this.room = room;
		
	}
	
	//Override clicked
	protected void clicked(){
		super.clicked();
		System.out.println("Hiho");
		game.changeRoom(room);
		
	}

}
