package view;

import java.awt.Dimension;
import java.io.IOException;

import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import controller.*;
import engine.Engine;

public class GameWindow extends JFrame{
	
	public GamePanel gamePanel;
	public GameController gameController;
	
	public GameWindow(Engine engine, String fileName) throws ParserConfigurationException, SAXException, IOException {
		this.gameController = new GameController(fileName);
		this.gamePanel = new GamePanel(gameController, engine);
		
		Dimension dim = new Dimension(2* this.gamePanel.getSquareSize() * this.gameController.getNbColumns(),
				2*this.gamePanel.getSquareSize() * this.gameController.getNbLines());
		
		this.setPreferredSize(dim);
		this.addKeyListener(this.gamePanel);
		this.add(gamePanel);
		this.pack();
		this.setVisible(true);
	}
	
	
}
