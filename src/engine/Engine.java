package engine;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import controller.GameController;
import model.Path;
import view.GamePanel;
import view.GameWindow;

public class Engine {
	public int level;
	private GameWindow gameWindow;
	private String fileName;
	private final int MAX_LEVEL = 4;
	
	
	public Engine() throws ParserConfigurationException, SAXException, IOException {
		this.level = 1;
		this.fileName = "board"+this.level+".xml";
		this.gameWindow= new GameWindow(this, fileName);
		this.gameWindow.setTitle("Level " + level);
	}
	
	public int getMaxLevel() {
		return MAX_LEVEL;
	}
	
	public void nextLevel() throws ParserConfigurationException, SAXException, IOException {
		this.level++;
		this.gameWindow.dispose();
		if(level <= MAX_LEVEL) {
			fileName = "board"+this.level+".xml";
			this.gameWindow= new GameWindow(this, fileName);
			this.gameWindow.setTitle("Level " + level);
		} else {
			JDialog d = new JDialog(new JFrame(), "", false);  
	        d.setLayout( new FlowLayout() );
	        d.add( new JLabel ("Game Over")); 
	        d.setSize(100,100);    
	        d.setVisible(true);  
		}
	}
	
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		new Engine();
	}
}
