import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel implements MouseListener, MouseMotionListener{

	
	public Rectangle screen;	
	public JFrame frame;

	public gameTimerTask gameTask;
	public Random r;
	int screenHeight,screenWidth;
	
	//Room
	Room currentRoom;
	ArrayList<Room> rooms;
	
	public Game(){
		
		super();
		
		initiate();
		
		//Add listeners that keep track of the mouse 
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	private void initiate(){
		gameTask = new gameTimerTask();
		screenHeight = 720;
		screenWidth = 1080;
		screen = new Rectangle(0, 0, screenWidth, screenHeight);
		frame = new JFrame("Coop Adventures");
		r = new Random();
		
		rooms = new ArrayList<Room>();
		String[] temp = new String[0];
		Room kitchen = new Room(this,"src/Table_Background.png","src/Table_Icon.png",new File("src/KitchenThings"));
		Room livingRoom = new Room(this,"src/Living Room_Background.png","src/Living Room_Icon.png",new File("src/LivingRoomThings"));
		rooms.add(livingRoom);
		rooms.add(kitchen);
		
		currentRoom = rooms.get(0);
		
	}
	
	

	class gameTimerTask extends TimerTask{ 
		
		public void run(){
			currentRoom.updateThings();
			//Repaint
			frame.repaint();
		}
	}
	
	
	public void paintComponent(Graphics g){
		currentRoom.draw(g);
		
		for(int i = 0; i<rooms.size();i++){
			rooms.get(i).drawIcon(g,getRoomIconPos(i));
		}
	}
	
	private Point getRoomIconPos(int iconNo){
		return new Point((int)(screenWidth/rooms.size()*(iconNo+0.5)),screenHeight-100);
	}
	
	public void mouseClicked(MouseEvent mouse){
		Point mousePos = new Point(mouse.getX(),mouse.getY());
		if(!roomIconClicked(mousePos)){
			currentRoom.thingClicked(mousePos);
		}
    }
	public void mouseEntered(MouseEvent mouse){ 
	}   
    public void mouseExited(MouseEvent mouse){
    }
    public void mousePressed(MouseEvent mouse){
    }
    public void mouseReleased(MouseEvent mouse){ 
    }
    public void mouseDragged(MouseEvent mouse){	
    }
    public void mouseMoved(MouseEvent mouse){
    }
    
    
    private boolean roomIconClicked(Point mousePos){
    	for(int i = 0;i<rooms.size();i++){
    		if(
    				mousePos.x > getRoomIconPos(i).x &&
    				mousePos.x < getRoomIconPos(i).x+rooms.get(i).getIconSize().x &&
    				mousePos.y > getRoomIconPos(i).y &&
    				mousePos.y < getRoomIconPos(i).y+rooms.get(i).getIconSize().y
    				){
    			currentRoom = rooms.get(i);
    			return true;
    		}
    	}
    	return false;
    }
	
	public static void main(String args[]){
		//	Create a timer.
		java.util.Timer mainProgramTimer = new java.util.Timer();
		//	Create a new instance of the program
		Game panel = new Game();
		
		// 	Set up the settings of our JFrame
	    panel.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    panel.frame.setSize(panel.screen.width, panel.screen.height);
	    panel.frame.setContentPane(panel); 
	    panel.frame.setVisible(true);
	    
	    mainProgramTimer.schedule(panel.gameTask, 0, 20);
	}
}
