package view;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import controller.*;
import engine.*;
import model.*;

import javax.swing.*;


public class GamePanel extends JPanel implements KeyListener {
	
	private Integer squareSize = 50;
	private Engine engine;
	private GameController gameController;

	public void paint(Graphics graphics) {
		int nbLignes = gameController.getNbLines();
		int nbColonnes = gameController.getNbColumns();
		
		this.squareSize = 50;
		for (int i = 0; i < nbLignes; i++) {
			for (int j = 0; j < nbColonnes; j++) {
				graphics.drawRect(j * this.squareSize, i * this.squareSize, this.squareSize, this.squareSize);
				if (this.gameController.getCellType(i, j) == Celltype.WALL) {
					graphics.setColor(Color.BLACK);
					graphics.fillRect(j * this.squareSize, i * this.squareSize, this.squareSize, this.squareSize);	
				} else if (this.gameController.getCellType(i, j) == Celltype.PORTAL) {
					Portal portal = (Portal)this.gameController.getCell(i, j);
					graphics.setColor(portal.getColor());
					graphics.fillRect(j * this.squareSize, i * this.squareSize, this.squareSize, this.squareSize);
					graphics.setColor(Color.LIGHT_GRAY);
					graphics.fillRect(j * this.squareSize + 15, i * this.squareSize + 15, (this.squareSize - 30), (this.squareSize - 30));	
				}else if (this.gameController.getCellType(i, j) == Celltype.LAST) {
					ImageIcon icon = new ImageIcon("lastCell.png");
				    int x = j * this.squareSize;
				    int y = i * this.squareSize;
				    icon.paintIcon(this, graphics, x, y);
				}else if (this.gameController.getCellType(i, j) == Celltype.BRIDGE) {
					graphics.setColor(Color.LIGHT_GRAY);
					graphics.fillRect(j * this.squareSize, i * this.squareSize, (this.squareSize), (this.squareSize));
					graphics.setColor(Color.MAGENTA);
					graphics.fillRect(j * this.squareSize + 10, i * this.squareSize, this.squareSize - 20, this.squareSize);	
					graphics.fillRect(j * this.squareSize, i * this.squareSize + 10, this.squareSize, this.squareSize - 20);	
				}  else {
					graphics.setColor(Color.LIGHT_GRAY);
					graphics.fillRect(j * this.squareSize, i * this.squareSize, this.squareSize, this.squareSize);	
				}
				if (this.gameController.cellInPath(i, j)) {
					graphics.setColor(Color.RED);
					graphics.fillRect(j * this.squareSize + 5, i * this.squareSize + 5, (this.squareSize - 10), (this.squareSize - 10));	
				}
				if (this.gameController.isCurrentCell(i, j)) {
					graphics.setColor(Color.YELLOW);
					graphics.fillRect(j * this.squareSize + 5, i * this.squareSize + 5, (this.squareSize - 10), (this.squareSize - 10));	
				}
				graphics.setColor(Color.DARK_GRAY);
				graphics.drawRect(j * this.squareSize, i * this.squareSize,	this.squareSize, this.squareSize);
			}
		}
		
		if(this.gameController.isOver()) {
			JDialog d = new JDialog(new JFrame(), "", false);  
	        d.setLayout( new FlowLayout() ); 
	        JButton b = new JButton ("OK");  
	        b.addActionListener ( new ActionListener()  
	        {  
	            public void actionPerformed( ActionEvent e )  
	            {  
	            	try {
	            		d.dispose();
						engine.nextLevel();
					} catch (Exception f) {};
	            }  
	        });  
	        if(this.engine.level + 1 < engine.getMaxLevel()) {
		        d.add( new JLabel ("Next level?")); 
		        d.add(b);
		        d.setSize(100,100);    
		        d.setVisible(true);  
	        } else {
	        	try {
					engine.nextLevel();
				} catch (Exception e) {};
	        }
	        
		}
}

	public Integer getSquareSize() {
		return this.squareSize;
	}

	public GamePanel(GameController gameController, Engine engine) {
		this.gameController = gameController;
		this.engine = engine;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_UP:
				gameController.oneStep(Direction.UP);
				break;
			case KeyEvent.VK_DOWN:
				gameController.oneStep(Direction.DOWN);
				break;
			case KeyEvent.VK_LEFT:
				gameController.oneStep(Direction.LEFT);
				break;
			default:
				gameController.oneStep(Direction.RIGHT);
				break;
		}
		this.repaint();
	}
}
