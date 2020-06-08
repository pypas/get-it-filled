package model;
public class Bridge extends Cell{
	private int count;
	public Bridge() {
		count = 0;
		type = Celltype.BRIDGE;
	}
	
	public Cell moveToMe() {
		count++;
		isEmpty = false;
		System.out.println(count);
		return this;
		
	}
	
	public void goBack() {
		count--;
		if(count == 0) {
			isEmpty = true;
		}
		System.out.println(count);
	}
}
