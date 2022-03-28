package Main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import Data.Vector2D;
import Data.spriteInfo;
import logic.Control;
import timer.stopWatchX;

public class Main{
	// Fields (Static) below...
	public static stopWatchX timer = new stopWatchX(100);
	public static ArrayList<spriteInfo> sprites = new ArrayList<>();
	public static int currentSpriteIndex = 0;
	// End Static fields...
	
	public static void main(String[] args) {
		Control ctrl = new Control();				// Do NOT remove!
		ctrl.gameLoop();							// Do NOT remove!
	}
	
	/* This is your access to things BEFORE the game loop starts */
	public static void start(){
		for (int i = -128; i < (1280 + 128); i += 8) {
			spriteInfo stmp = new spriteInfo(new Vector2D(i, 200), "frame" + (currentSpriteIndex + 1));
			sprites.add(stmp);
			if(currentSpriteIndex == 7) {
				currentSpriteIndex = 0;
			} else {
				currentSpriteIndex++;
			}
		} 
		currentSpriteIndex = 0; 
	} 
	
	/* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
	public static void update(Control ctrl) {
		ctrl.addSpriteToFrontBuffer(sprites.get(currentSpriteIndex).getCoords().getX(), sprites.get(currentSpriteIndex).getCoords().getY(), sprites.get(currentSpriteIndex).getTag());
		if (timer.isTimeUp()) {
			timer.resetWatch();
			if (currentSpriteIndex == sprites.size() - 1) {
				currentSpriteIndex = 0;
			} else {
				currentSpriteIndex++;
			}
		}	
	}
}
