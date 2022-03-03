package Main;

import java.awt.Color;
import java.util.LinkedList;
import java.util.Queue;

import Data.Vector2D;
import logic.Control;
import timer.stopWatchX;

public class Main{
	// Fields (Static) below...
	public static boolean isImageDrawn = false;
	public static stopWatchX timer = new stopWatchX(50);
	public static Queue<Vector2D> vecs1 = new LinkedList<>();
	public static Queue<Vector2D> vecs2 = new LinkedList<>();
	public static Vector2D currentVec = new Vector2D(-100, -100);
	// End Static fields...
	
	public static void main(String[] args) {
		Control ctrl = new Control();				// Do NOT remove!
		ctrl.gameLoop();							// Do NOT remove!
	}
	
	/* This is your access to things BEFORE the game loop starts */
	public static void start(){
		// TODO: Code your starting conditions here...NOT DRAW CALLS HERE! (no addSprite or drawString)
		for (int i = -128; i < (1280 + 128); i += 8) {
			vecs1.add(new Vector2D(i, 200));
		}
		currentVec.setX(vecs1.peek().getX());
		currentVec.setY(vecs1.peek().getY());
		vecs1.remove();
	}
	
	/* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
	public static void update(Control ctrl) {
		// TODO: This is where you can code! (Starting code below is just to show you how it works)
		ctrl.addSpriteToFrontBuffer(currentVec.getX(), currentVec.getY(), "greenguy");
		if (timer.isTimeUp()) {
			currentVec.setX(vecs1.peek().getX());
			currentVec.setY(vecs1.peek().getY());
			vecs2.add(vecs1.remove());
			timer.resetWatch();
			if (vecs1.isEmpty()) {
				while(!vecs2.isEmpty()) {
					vecs1.add(vecs2.remove());
				}
			}
		}
	}
	
	// Additional Static methods below...(if needed)

}
