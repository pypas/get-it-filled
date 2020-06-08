package model;
import java.awt.Color;
import java.lang.reflect.Field;


public class Portal extends Cell{
	private Cell nextCell;
	private Color color;
	
	public Portal() {
		this.nextCell = null;
		isEmpty = true;
		type = Celltype.PORTAL;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(String colorName) {
		Color color;
		try {
		    Field field = Class.forName("java.awt.Color").getField(colorName);
		    color = (Color)field.get(null);
		} catch (Exception e) {
		    color = null;
		}
		this.color = color;
	}
	
	public void setNextPortal(Cell nextPortal) {
		this.nextCell = nextPortal;
	}
	
	public Cell getNextPortal() {
		return nextCell;
	}
	
}
