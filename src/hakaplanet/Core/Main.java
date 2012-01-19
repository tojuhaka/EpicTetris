package hakaplanet.Core;



import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main {
	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new Tetris(
					"HakaTetris Version 3.215609"));

			app.setDisplayMode(800, 600, false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}
}
