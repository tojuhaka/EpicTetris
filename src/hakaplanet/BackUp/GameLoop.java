package hakaplanet.BackUp;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Gives class an ability to join the gameloop. Make "Entity"
 * class if needed.
 * @author tojuhaka
 * @date 4.2.2011
 */
public interface GameLoop {
	public void init(GameContainer gc, StateBasedGame sb);
	public void update(GameContainer gc, StateBasedGame sb, int delta);
	public void render(GameContainer gc, StateBasedGame bc, Graphics g);
}
