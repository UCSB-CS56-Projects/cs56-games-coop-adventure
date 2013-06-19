import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class NPC extends Thing{
	

	private Game game;
	private ArrayList<DialoguePart> dialogue;
	
	public <E extends JPanel> NPC(String name, E jPanel, BufferedImage img,Game game,File conversationFile){
		super(name, jPanel, img);
		this.game = game;
		extractDialogue(conversationFile);
	}
	
	private void extractDialogue(File conversationFile){
		this.dialogue = new ArrayList<DialoguePart>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(conversationFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line = null;
		try {
			if(reader!=null){
				String question = "";
				while ((line = reader.readLine()) != null) {
					if(question.equals("")){
						question = line;
					}else{
						dialogue.add(new DialoguePart(question,line));
						//Reset question
						question = "";
					}
					
				}
			}
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
	
	//Override clicked()
	protected void clicked(){
		super.clicked();
		game.changeConversation(dialogue);
		
	}
	
}
