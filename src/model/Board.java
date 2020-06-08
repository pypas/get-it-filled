package model;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Board {
	private Cell[][] board;
	private Cell firstCell;
	private Cell lastCell;
	private int nbLines;
	private int nbColumns;
	
	public Board(String file) throws ParserConfigurationException, SAXException, IOException {
		this.createBoard(file);
	}	
	
	public int getNbLines() {
		return nbLines;
	}
	
	public int getNbColumns() {
		return nbColumns;
	}
	
	public void createBoard(String fileName) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();	 
		Document document = builder.parse(new File(fileName));
		 
		document.getDocumentElement().normalize();
		
		NodeList nList = document.getElementsByTagName("cell");
		
		nbLines = Integer.parseInt(document.getElementsByTagName("nbLines").item(0).getTextContent());
		nbColumns = Integer.parseInt(document.getElementsByTagName("nbColumns").item(0).getTextContent());
		
		board = new Cell[nbLines][nbColumns];
		
		for (int temp = 0; temp < nList.getLength(); temp++)
		{
		 Node node = nList.item(temp);
		 if (node.getNodeType() == Node.ELEMENT_NODE)
		 {
			 Element eElement = (Element) node; 
				int line = Integer.parseInt(eElement.getElementsByTagName("line").item(0).getTextContent());
				int column = Integer.parseInt(eElement.getElementsByTagName("column").item(0).getTextContent());
				if (eElement.getElementsByTagName("type").item(0).getTextContent().equals("StandardCell")) {
					board[line][column] = new StandardCell();
				} else if (eElement.getElementsByTagName("type").item(0).getTextContent().equals("Wall")) {
					board[line][column] = new Wall();
				} else if (eElement.getElementsByTagName("type").item(0).getTextContent().equals("Portal")) {
					board[line][column] = new Portal();
				} else if (eElement.getElementsByTagName("type").item(0).getTextContent().equals("Bridge")) {
					board[line][column] = new Bridge();
				} else if (eElement.getElementsByTagName("type").item(0).getTextContent().equals("LastCell")) {
					board[line][column] = new LastCell();
					lastCell = board[line][column];
				} else if (eElement.getElementsByTagName("type").item(0).getTextContent().equals("FirstCell")) {
					board[line][column] = new StandardCell();
					firstCell = board[line][column];
					firstCell.isEmpty = false;
				} 
		 }
		}
		
		for (int temp = 0; temp < nList.getLength(); temp++)
		{
		 Node node = nList.item(temp);
		 if (node.getNodeType() == Node.ELEMENT_NODE)
		 {
			 Element eElement = (Element) node; 
				if (eElement.getElementsByTagName("type").item(0).getTextContent().equals("Portal")) {
					int linePortal = Integer.parseInt(eElement.getElementsByTagName("line").item(0).getTextContent());
				    int columnPortal = Integer.parseInt(eElement.getElementsByTagName("column").item(0).getTextContent());
					Cell portal = board[linePortal][columnPortal];
					
					int nextCellId = Integer.parseInt(eElement.getElementsByTagName("nextCellId").item(0).getTextContent());
				    Node nextNode = nList.item(nextCellId);
				    Element nexteElement = (Element) nextNode; 
				    int nextLine = Integer.parseInt(nexteElement.getElementsByTagName("line").item(0).getTextContent());
				    int nextColumn = Integer.parseInt(nexteElement.getElementsByTagName("column").item(0).getTextContent());
				    
				    String colorName = eElement.getElementsByTagName("color").item(0).getTextContent();
				    ((Portal) portal).setColor(colorName);
				    ((Portal) portal).setNextPortal(board[nextLine][nextColumn]);
				} 
		 }
		}
		
		
	}
	
	
	public Cell nextCell(Cell currentCell, Direction dir, Cell lastCell) {
		Cell nextCell = null;
		for(int i = 0; i < nbLines; i++) {
			for(int j = 0; j < nbColumns; j++) {
				if(board[i][j] == currentCell) {
					if(dir == Direction.LEFT && (j-1) >= 0) {
						nextCell = board[i][j-1];
					} else if(dir == Direction.RIGHT && (j+1) < nbColumns) {
						nextCell = board[i][j+1];
					} else if(dir == Direction.UP && (i-1) >= 0) {
						nextCell = board[i-1][j];
					} else if(dir == Direction.DOWN && (i+1) < nbLines) {
						nextCell = board[i+1][j];
					}
				}
			}
		}
		if (nextCell == null) return null;
		if(nextCell.type == Celltype.PORTAL) {
			Cell moveCellBis = ((Portal) nextCell).getNextPortal();
			Cell nextCellBis = nextCell(moveCellBis, dir, lastCell);
			if ((nextCell.isEmpty || nextCellBis == lastCell)) {
				if(nextCellBis != null) {
					if(nextCellBis.type == Celltype.WALL) return null;
					nextCell.isEmpty = !nextCell.isEmpty;
					moveCellBis.isEmpty = !moveCellBis.isEmpty;
					return nextCellBis;
				} else {
					return null;
				}
			} else {
				return null;
			} 
			
		}
		return nextCell;
	}
	
	public boolean emptyCells() {
		for(int i = 0; i < nbLines; i++) {
			for(int j = 0; j < nbColumns; j++) {
				if(board[i][j].isEmpty)
					return true;
			}
		}
		return false;
	}
	
	public boolean isOver(Cell nextCell) {
		if (!emptyCells() && nextCell == lastCell) {
			return true;
		}
		return false;
	}
	
	public Cell getFirstCell() {
		return firstCell;
	}
	
	public Cell getCell(int i,int j) {
		return board[i][j];
	}
	
	public Celltype getCellType(int i, int j) {
		return board[i][j].type;
	}
	
	public boolean cellInPath(int i, int j) {
		if (board[i][j].type != Celltype.WALL && !board[i][j].isEmpty) {
			return true;
		}
		return false;
	}
}
