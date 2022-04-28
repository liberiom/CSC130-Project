package Data;

public class BoundingBox {
	private spriteInfo sprite;
	private int x1, x2, y1, y2, widthAndHeight;
	
	public BoundingBox(spriteInfo sprite, int widthAndHeight) {
		this.sprite = sprite;
		this.widthAndHeight = widthAndHeight;
		this.x1 = sprite.getCoords().getX();
		this.y1 = sprite.getCoords().getY();
		this.x2 = sprite.getCoords().getX() + widthAndHeight;
		this.y2 = sprite.getCoords().getY() + widthAndHeight;
	}
	
	// This bounding box is specifically made for the outer barriers 
	public BoundingBox(int x1, int x2, int y1, int y2) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
	}
	
	public String toString() {
		return "" + x1 + " " + x2 + " " + y1 + " " + y2;
	}
	
	public int getX1(){
		return x1;
	}
	
	public int getX2(){
		return x2;
	}
	
	public int getY1(){
		return y1;
	}
	
	public int getY2(){
		return y2;
	}
	
	public int getWidthAndHeight() {
		return widthAndHeight;
	}
	
	public void setX1(int x1){
		this.x1 = x1;
	}
	
	public void setX2(int x2){
		this.x2 = x2;
	}
	
	public void setY1(int y1){
		this.y1 = y1;
	}
	
	public void setY2(int y2){
		this.y2 = y2;
	}
}
