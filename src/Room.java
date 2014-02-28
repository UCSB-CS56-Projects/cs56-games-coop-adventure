import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;
import java.io.FileFilter;

/**
 * Represents a room in the game.
 * @author Gunnar Weibull
 *
 */
public class Room{
	
	private Image bg;
	private Image icon;
	private JPanel jPanel;
	private ArrayList<Thing> things;
	private Game game;
	private String name;
	
	/**
	 * 
	 * @param j Objects extending JPanel, used for ImageListener
	 * @param bgImgPath Path to background image
	 * @param iconImgPath Path to icon image
	 * @param thingImgDir Path to images for Things in the room
	 */
	public <E extends JPanel> Room(String name,E j,String bgImgPath,String iconImgPath,File thingImgDir,Game game){
		this.name = name;
		this.game = game;
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
		
		//Import images of things and create things based on them
		if (thingImgDir.isDirectory()) { // make sure it's a directory
			FileFilter filter = new ThingLoadFilter("normal");
			File[] filesToCheck = thingImgDir.listFiles(filter);
            for (final File f : filesToCheck) {
                BufferedImage img = null;
                try {
                	String thingName = f.getName().substring(0, f.getName().length()-4); //Remove the file type (".png")
                    img = ImageIO.read(f);
                    if(thingName.substring(thingName.length()-3).equals("NPC")){
                    	File conversation = new File(thingImgDir+"/"+thingName+".txt");
                    	if(conversation.exists()){
                    		System.out.println(thingName +" conversation existed");
                    	}else{
                    		System.out.println(thingName+" conversation did not exist");
                    	}
                    		
                    	things.add(new NPC(thingName,jPanel,img,game,conversation));
                    }else if(thingName.substring(thingName.length()-8).equals("Teleport")){
                    	
                    	String room = null;
                    	
                    	
                    	if(
                    			thingName.length()>8+"Kitchen".length()&&
                    			thingName.substring(thingName.length()-8-"Kitchen".length(),thingName.length()-8).equals("Kitchen")){
           
                    		room = "Kitchen";
                    	}else if(
                    			thingName.length()>8+"LivingRoom".length()&&
                    			thingName.substring(thingName.length()-8-"LivingRoom".length(),thingName.length()-8).equals("LivingRoom")){
                    		room = "LivingRoom";
                    	}else if(
                    			thingName.length()>8+"Dashain".length()&&
                    			thingName.substring(thingName.length()-8-"Dashain".length(),thingName.length()-8).equals("Dashain")){
                    		room = "Dashain";
                    	}else if(
                    			thingName.length()>8+"Bedroom".length()&&
                    			thingName.substring(thingName.length()-8-"Bedroom".length(),thingName.length()-8).equals("Bedroom")){
                    		room = "Bedroom";
                    	}
                    	
                    	if(room!=null){

                    		things.add(new Teleport(thingName,jPanel,img,game,room));
                    	}else{
                    		things.add(new Thing(thingName,jPanel,img));
                    	}
                    }else{
                    	things.add(new Thing(thingName,jPanel,img));
                    }
                    
                } catch (final IOException e) {
                    System.out.println("Failed to load images of things in a room");
                }
            }
        }
		//Add animations and sounds
		for(Thing thing:things){
			try{
				String path = thingImgDir.getPath()+"/"+thing.getName()+"2.png";
				BufferedImage animation = ImageIO.read(new File(path));
				thing.addAnimation(animation);
			}catch(Exception e){
				
			}
			
			try{
				String path = thingImgDir.getPath()+"/"+thing.getName()+"2.gif";
				Image animation = Toolkit.getDefaultToolkit().createImage(path);
				thing.addAnimation(animation);
			}catch(Exception e){
				
			}
			
			String[] musicExtensions = {".wav",".mp3"};
			for(String me:musicExtensions){
				String path = thingImgDir.getPath()+"/"+thing.getName()+me;
				try{
					File audioFile = new File(path);
					if(audioFile.exists()){
						System.out.println("Added audiofile "+path);
						thing.addAudio(audioFile);
					}

				}catch(Exception e){
					System.out.println("Couldn't load sound"+path);
				}
			}
		}
		
		//Arrange according to any order specifications
		
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(thingImgDir.getPath()+"/order.txt"));
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
		}
		String line = null;
		
		try {
			if(reader!=null){
				ArrayList<Thing> temp = new ArrayList<Thing>();
				while ((line = reader.readLine()) != null) {
					for(Thing thing:things){
						if(thing.getName().equals(line)){
							temp.add(thing);
						}
					}
				}
				
				for(Thing t: temp){
					things.remove(t);
					things.add(0,t);
				}
				
			}
			
		} catch (IOException e) {
			//e.printStackTrace();
		}
		
			
	}
	
	public String getName(){
		return this.name;
	}
	
	/**
	 * Updates all things in the room.
	 */
	public void updateThings(){
		for(Thing t: things){
			t.update();
		}
	}
	
	/**
	 * 
	 * @return Size of room icon
	 */
	public Point getIconSize(){
		if(icon!=null){
		return new Point(icon.getWidth(jPanel),icon.getHeight(jPanel));
		}else{
			return new Point(0,0);
		}
	}
	
	/**
	 * Checks if any of the Things in the room are clicked
	 * @param mousePos
	 */
	public void thingClicked(Point mousePos){
		for(Thing t: things){
			if(t.checkIfClicked(mousePos)){
				return;
			}
		}
	}
	
	/**
	 * Draws or calls necessary functions to draw everything in the room (not the icon).
	 * @param g
	 */
	public void draw(Graphics g){
		g.drawImage(bg,0,0,jPanel);
		drawThings(g);
	}
	
	/**
	 * Draws the Things in the room
	 * @param g-
	 */
	private void drawThings(Graphics g){
		for(int i = things.size()-1;i>=0;i--){
			things.get(i).draw(g);
		}
	}
	
	/**
	 * Draws the icon of the room at the specified coordinates
	 * @param g
	 * @param coord
	 */
	public void drawIcon(Graphics g,Point coord){
		if(icon!=null){
			g.drawImage(icon, coord.x, coord.y,jPanel);
		}
	}

}
