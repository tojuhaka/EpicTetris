package hakaplanet.Core;



import hakaplanet.GameStates.MenuState;
import hakaplanet.GameStates.PlayState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Tetris extends StateBasedGame {

	public static final int PLAYSTATE = 0;
	public static final int MENUSTATE = 1;

	/**
	 * Constructor defines the game "name" and adds
	 * gamestates into the game.
	 * @param name
	 */
	public Tetris(String name) {
		super(name);
		this.addState(new MenuState(MENUSTATE));
		this.addState(new PlayState(PLAYSTATE));
	}

	/**
	 * Define that the first state to start rendering is the menu
	 */
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		gc.setShowFPS(false);
		//gc.setFullscreen(true);
		//this.getState(MENUSTATE).init(gc, this);
		//this.getState(PLAYSTATE).init(gc, this);
		//TODO Why this? It cass init twice
	}


}
