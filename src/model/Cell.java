package model;
public abstract class Cell {
	boolean isEmpty;
	Celltype type;
	
	public Cell() {
		isEmpty = true;
	}
	
	public Cell moveToMe() {
		if (isEmpty) {
			return this;
		}
		return null;
	}
	
	public void goBack() {
		isEmpty = true;
	}
	
}
