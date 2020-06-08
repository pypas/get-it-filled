package model;
public class Wall extends Cell{
	public Wall() {
		isEmpty = false;
		type = Celltype.WALL;
	}
	public Cell moveToMe() {
		return null;
	}
}
