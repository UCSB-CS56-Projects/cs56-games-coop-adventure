import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;


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
	
	public String getName(){
		return name;
	}
	
	public void addAudio(File audioFile){
		this.audioFile = audioFile;
	}
	
	public void addAnimation(BufferedImage animation){
		this.animation = animation;
	}
	
	public void addAnimation(Image animation){
		this.gifAnimation = animation;
	}
	
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
	
	public void markAsClicked(Point mousePos){
		this.clicked = true;
		this.mousePos = mousePos;
	}
	
	private void clicked(Point mousePos){
		
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

			}
		}
		
		clicked = false;
	}
	
	public void update(){
		
		if(clicked){
			clicked(this.mousePos);
		}else if(audioClip!=null&&!audioClip.isActive()){
			//Close audioclip if it isn't playing
			this.audioClip.close();
		}
		

		
		
		
	}

}
