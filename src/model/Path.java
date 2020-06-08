package model;

import java.io.IOException;
import java.util.LinkedList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Path {
	private Cell currentCell;
	private Board board;
	private LinkedList<Cell> path;
	private Cell lastCell;
	public boolean isOver;
	
	public Path(String fileName) throws ParserConfigurationException, SAXException, IOException {
		this.board = new Board(fileName);
		this.currentCell = board.getFirstCell();
		this.path = new LinkedList<Cell>();
		path.add(currentCell);
		this.lastCell = null;
		this.isOver = false;
	}
	
	public int getNbLines() {
		return board.getNbLines();
	}
	
	public int getNbColumns() {
		return board.getNbColumns();
	}
	
	public void oneStep(Direction dir) {		
		if (path.size() > 1) {
			lastCell = path.get(path.size() - 2);
		}
		Cell moveCell = board.nextCell(currentCell, dir, lastCell);
		if (moveCell != null) {
			Cell nextCell = moveCell.moveToMe();
			
			if(nextCell != null && moveCell != lastCell) {
				path.add(nextCell);
				nextCell.isEmpty = false;
				currentCell = nextCell;
				if (board.isOver(nextCell)) {
					isOver = true;
				}
			} else if(moveCell == lastCell) {
				if (moveCell.type == Celltype.BRIDGE) {
					moveCell.goBack();
				}
				path.removeLast();
				currentCell.goBack(); 
				currentCell = lastCell;
			}
			
		}
	}
	
	public Cell getLastCell() {
		return lastCell = path.get(path.size() - 1);
	}
	
	public Celltype getCellType(int i, int j) {
		return board.getCellType(i,j);
	}
	
	public Cell getCell(int i, int j) {
		return board.getCell(i,j);
	}
	
	public boolean cellInPath(int i, int j) {
		return board.cellInPath(i, j);
	}
	
	public boolean isCurrentCell(int i, int j) {
		return (currentCell == board.getCell(i,j));
	}
}
