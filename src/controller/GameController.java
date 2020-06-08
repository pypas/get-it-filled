	package controller;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import model.*;

public class GameController {
	private Path path;
	
	
	public GameController(String fileName) throws ParserConfigurationException, SAXException, IOException {
		this.path = new Path(fileName);
	}
	
	public int getNbColumns() {
		return path.getNbColumns();
	}
	
	public int getNbLines() {
		return path.getNbLines();
	}
	
	public void oneStep(Direction dir) {
		path.oneStep(dir);
	}
	
	public boolean cellInPath(int i, int j) {
		return path.cellInPath(i, j);
	}
	
	public Celltype getCellType(int i, int j) {
		return path.getCellType(i,j);
	}
	
	public Cell getCell(int i, int j) {
		return path.getCell(i,j);
	}
	
	public boolean isCurrentCell(int i, int j) {
		return path.isCurrentCell(i, j);
	}
	
	public boolean isOver() {
		return path.isOver;
	}
}
