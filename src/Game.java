import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Game extends JPanel implements MouseListener, MouseMotionListener{

	
	public Rectangle screen;	
	public JFrame frame;

	public gameTimerTask gameTask;
	public Random r;
	int screenHeight,screenWidth;
	
	//Room
	private Room currentRoom;
	private ArrayList<Room> rooms;
	
	//NPC interaction
	ArrayList<DialoguePart> conversationDialogue;
	ArrayList<JLabel> conversationJLabels;
	private JPanel conversationWindow;
	private JLabel back,exit;
	private static int fontSize = 22;
	
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
		Room dashain = new Room("Dashain",this,"src/Dashain_Background.png","src/Dashain_Icon.png",new File("src/DashainThings"),this);
		Room kitchen = new Room("Kitchen",this,"src/Table_Background.png","src/Table_Icon.png",new File("src/KitchenThings"),this);
		Room livingRoom = new Room("LivingRoom",this,"src/Living Room_Background.png","src/Living Room_Icon.png",new File("src/LivingRoomThings"),this);
		Room bedroom = new Room("Bedroom",this,"src/Bedroom_Background.png","src/Bedroom_Icon.png",new File("src/BedroomThings"),this);
		rooms.add(dashain);
		rooms.add(kitchen);
		rooms.add(livingRoom);
		rooms.add(bedroom);
		
		
		currentRoom = rooms.get(0);
		
		conversationWindow = getNewConversationWindow();
		conversationDialogue = null;
		this.back = new JLabel("Thanks! (click here to return)");
		this.back.setOpaque(true);
		this.back.setBackground(new Color(200,100,100));
		this.back.setFont(new Font("Serif", Font.PLAIN, fontSize));
		this.back.addMouseListener(this);
		
		this.exit = new JLabel("See you later! (click here to stop chatting)");
		this.exit.setOpaque(true);
		this.exit.setBackground(new Color(200,100,100));
		this.exit.setFont(new Font("Serif", Font.PLAIN, fontSize));
		this.exit.addMouseListener(this);
	}
	
	public void changeConversation(ArrayList<DialoguePart> conversation){
		this.conversationDialogue = conversation;
		flushPanel(conversationWindow,"Hi! What question do you want to ask? (Click one of the questions)");
		conversationWindow.setVisible(true);
		conversationJLabels = new ArrayList<JLabel>();
		ArrayList<JLabel> questions = new ArrayList<JLabel>();
		for(DialoguePart dp:conversation){
			JLabel question = new JLabel(dp.getQuestion());
			question.setBackground(new Color(100+r.nextInt(150),100+r.nextInt(150),100+r.nextInt(150)));
			question.setOpaque(true);
			question.setFont(new Font("Serif", Font.PLAIN, fontSize));
			question.addMouseListener(this);
			questions.add(question);
			conversationJLabels.add(question);
			conversationWindow.add(question);
		}
		conversationWindow.add(exit);
		conversationWindow.revalidate();
		conversationWindow.repaint();
		
	}
	
	
	
	private JPanel getNewConversationWindow(){
		JPanel returnPanel = new JPanel();//new JLabel("There should probably be some other text here...",SwingConstants.CENTER);
		returnPanel.setLayout(new BoxLayout(returnPanel,BoxLayout.Y_AXIS));
		returnPanel.setOpaque(true);
		returnPanel.setBackground(new Color(127,255,212));
		//returnPanel.setVisible(false);
		returnPanel.add(new JLabel("The Santa Barbara Student Housing Coops Proudly Presents"));
		returnPanel.setFont(new Font("Serif", Font.PLAIN, fontSize));
		return returnPanel;
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
			if(!(rooms.get(i).equals(currentRoom))){
				rooms.get(i).drawIcon(g,getRoomIconPos(i));
			}
		}
	}
	
	private Point getRoomIconPos(int iconNo){
		return new Point((int)(screenWidth/rooms.size()*(iconNo+0.5)),screenHeight-75);
	}
	
	public void mouseClicked(MouseEvent mouse){
		System.out.println(mouse.getSource());
		if(conversationJLabels!=null){
			for(JLabel jl:conversationJLabels){
				if(mouse.getSource().equals(jl)){
					System.out.println("Clicked JLabel");
					for(DialoguePart dp:conversationDialogue){
						if(jl.getText().equals(dp.getQuestion())){
							flushPanel(conversationWindow,"Answer: ");
							JLabel answer = new JLabel("<html><p style=\"width:600px\">"+dp.getAnswer()+"</p></html>");
							answer.setBackground(new Color(50+r.nextInt(50),200+r.nextInt(50),100+r.nextInt(50)));
							answer.setOpaque(true);
							answer.setFont(new Font("Serif", Font.PLAIN, fontSize));
							conversationWindow.add(answer);
							conversationWindow.add(back);
							conversationWindow.revalidate();
							conversationWindow.repaint();
							return;
						}
					}
					
				}
			}
		}
		if(mouse.getSource().equals(back)){
			this.changeConversation(conversationDialogue);
			return;
		}
		if(mouse.getSource().equals(exit)){
			flushPanel(conversationWindow,"Click on a person to ask questions");
			conversationWindow.revalidate();
			conversationWindow.repaint();
			return;
		}
		Point mousePos = new Point(mouse.getX(),mouse.getY());
		if(!roomIconClicked(mousePos)){
			currentRoom.thingClicked(mousePos);
		}
    }
	
	private void flushPanel(JPanel jp,String message){
		jp.removeAll();
		jp.add(new JLabel(message));
		jp.revalidate();
		jp.repaint();
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
    			flushPanel(conversationWindow,"Click on a person to ask questions!");
    			currentRoom = rooms.get(i);
    			return true;
    		}
    	}
    	return false;
    }
    
    public void changeRoom(String roomName){
    	for(Room room:rooms){
    		if(room.getName().equals(roomName)){
    			flushPanel(conversationWindow,"Click on a person to ask questions!");
    			this.currentRoom = room;
    		}
    	}
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
	    
	    panel.frame.add(panel.conversationWindow,BorderLayout.CENTER);
	    
	    mainProgramTimer.schedule(panel.gameTask, 0, 20);
	}
}
