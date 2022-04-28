package Main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

import Data.Vector2D;
import Data.spriteInfo;
import FileIO.EZFileRead;
import logic.Control;
import timer.stopWatchX;

public class Main{
	// Fields (Static) below...
	public static stopWatchX timer = new stopWatchX(3000);
	private static Color white = new Color(255, 255, 255);
	public static String trigger = "";
	public static Vector2D vector2d = new Vector2D(0, 0);
	public static int speed = 10;
	public static boolean visible = true;
	public static boolean isRight = true;
	public static ArrayList<spriteInfo> spritesRight = new ArrayList<spriteInfo>();
	public static ArrayList<spriteInfo> spritesLeft = new ArrayList<spriteInfo>();
	public static int currentSpriteIndex = 0;
	
	// End Static fields...
	public static void main(String[] args) {
		Control ctrl = new Control();				// Do NOT remove!
		ctrl.gameLoop();							// Do NOT remove!
	}
	
	/* This is your access to things BEFORE the game loop starts */
	public static void start(){
		for (int i = 1; i <= 8; i++) {
			spritesRight.add(new spriteInfo(new Vector2D(0, 0), "frame" + i));
			spritesLeft.add(new spriteInfo(new Vector2D(0, 0), "flippedframe" + i));
		}
		
		for (int i = 1; i <= 8; i++) {
			System.out.println(spritesRight.get(i).getTag());
		}
		
		for (int i = 1; i <= 8; i++) {
			System.out.println(spritesLeft.get(i).getTag());
		}
	} 
	
	/* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
	public static void update(Control ctrl) {
		ctrl.addSpriteToFrontBuffer(vector2d.getX(), vector2d.getY(), "frame1");
	}
}
