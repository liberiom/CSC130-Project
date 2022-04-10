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
	private static HashMap<String, String> map = new HashMap<>();
	public static stopWatchX timer = new stopWatchX(3000);
	private static Color white = new Color(255, 255, 255);
	private static EZFileRead ezr = new EZFileRead("scripts.txt");
	private static int currentText = 1;
	
	// End Static fields...
	public static void main(String[] args) {
		Control ctrl = new Control();				// Do NOT remove!
		ctrl.gameLoop();							// Do NOT remove!
	}
	
	/* This is your access to things BEFORE the game loop starts */
	public static void start(){
		String raw, key, value;
		StringTokenizer st; 		
		for (int i = 0; i < ezr.getNumLines(); i++) {
			st = new StringTokenizer(ezr.getNextLine(), "*");
			key = st.nextToken();
			value = st.nextToken();
			map.put(key, value);
		}
	} 
	
	/* This is your access to the "game loop" (It is a "callback" method from the Control class (do NOT modify that class!))*/
	public static void update(Control ctrl) {
		ctrl.drawString(100, 250, map.get("string" + currentText), white);
		if (timer.isTimeUp()) {
			timer.resetWatch();
			if (currentText == 5) {
				currentText = 1;
			} else {
				currentText++;
			} 
		}
	}
}
