import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;

/**
 * Class for clickable objects in the game rooms. The things will typically animate or change picture as well as play a sound when clicked.
 * @author Gunnar Weibull
 *
 */
public class Thing {
	private String name;
	private JPanel jPanel;
	private BufferedImage img,animation;
	private Image gifAnimation;
	private boolean animate;
	private int animationDuration,animationCountdown;
	private File audioFile;
	private Clip audioClip;
	private boolean clicked;
	private Point mousePos;

	/**
	 * 
	 * @param name Name of string, matches picture and sound associated with it, and animation with an additional "2" to the file name.
	 * @param jPanel Listener for the graphics
	 * @param img Idle image (optional animation is added afterwards)
	 */
	public <E extends JPanel> Thing(String name, E jPanel, BufferedImage img){
		this.name = name;

		this.jPanel = jPanel;
		this.img = img;
		this.animate = false;

		this.clicked = false;
		this.mousePos = null;

		this.animation = null;	//animations are added after the creation of the Thing
		this.animationDuration = 100;	//Standard duration
		this.animationCountdown = 0;


	}
	/**
	 * Returns name of Thing
	 * @return Name of Thing, same as idle picture (without file extension)
	 */
	public String getName(){
		return name;
	}
	/**
	 * Adds audio file to be played when object is clicked
	 * @param audioFile
	 */
	public void addAudio(File audioFile){
		this.audioFile = audioFile;
	}

	/**
	 * Adds picture to be played when Thing is clicked (png)
	 * @param animation
	 */
	public void addAnimation(BufferedImage animation){
		this.animation = animation;
	}

	/**
	 * Adds animation to be played when Thing is clicked (animated gif)
	 * @param animation
	 */
	public void addAnimation(Image animation){
		this.gifAnimation = animation;
	}

	/**
	 * Draws the Thing
	 * @param g
	 */
	public void draw(Graphics g){
		if(!animate){
			g.drawImage(img, 0, 0,jPanel);
		}else if(animationCountdown > 0){
			if(animation != null){
				g.drawImage(animation, 0, 0,jPanel);
			}else if(gifAnimation!=null){
				g.drawImage(gifAnimation, 0, 0,jPanel);
			}
			animationCountdown--;
			if(animationCountdown<=0){
				animate = false;
			}
		}
	}

	/**
	 * Checks if the object is clicked, and in that case, marks the Thing as clicked. The clicking consequences are handled in clicked() for synchronization purposes.
	 * @param mousePos Mouse position at time of click
	 */
	public void checkIfClicked(Point mousePos){
		int transparentColorID = 16777215;
		if(
				mousePos.x>0 &&
				mousePos.x<img.getWidth(jPanel) &&
				mousePos.y>0 &&
				mousePos.y<img.getHeight(jPanel) &&
				img.getRGB(mousePos.x, mousePos.y)!=transparentColorID	//Only check on non-transparent parts of the object
				){
			System.out.println("Object clicked with color: " + img.getRGB(mousePos.x, mousePos.y));
			this.clicked = true;
			this.mousePos = mousePos;
		}
	}

	/**
	 * Performs actions after a clicking event marked by checkIfClicked()
	 * This function is called by update() (as opposed to checkIfClicked()) to ensure synchronization.
	 */
	private void clicked(){

		if(clicked){
			animate = true;
			animationCountdown = animationDuration;
			if(gifAnimation!=null){
				gifAnimation.flush();
			}



			if(audioFile != null){
				AudioInputStream audio = null;
				try{
					audio = AudioSystem.getAudioInputStream(audioFile);
				}catch(Exception e){
					System.out.println("Audiosystem load fail");
				}
				try{
					this.audioClip = AudioSystem.getClip();
				}catch(Exception e){
					System.out.println("AudioSystem.getClip() failed");
				}
				if(audio!=null && audioClip != null){
					try{
						audioClip.open(audio);
						audioClip.start();
					}catch(Exception e){
						e.printStackTrace();
						System.out.println("Playing of sound failed.");
					}

				}
			}



			clicked = false;
		}
	}
	
	/**
	 * Runs clicked() if click is marked, otherwise checks and closes the audio file
	 */
	public void update(){

		if(clicked){
			clicked();
		}else if(audioClip!=null&&!audioClip.isActive()){
			//Close audioclip if it isn't playing
			this.audioClip.close();
		}





	}

}
