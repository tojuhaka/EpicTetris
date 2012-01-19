package hakaplanet.Entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import hakaplanet.Core.Entity;
import hakaplanet.Core.PlayArea;
import hakaplanet.Core.Tetris;
import hakaplanet.Entities.TetrisEntity.STATES;

/**
 * Here we handle all the "maininputs" such
 * as ESC, PAUSE, OPTIONS etc
 * @author tojuhaka
 * @date 6.2.2011
 */
public class MainInputEntity extends Entity {
	
	private TetrisEntity tetris;
	
	public MainInputEntity(TetrisEntity tetris) {
		this.tetris = tetris;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		// Handle some inputs during game
		Input input = gc.getInput();
		PlayArea area = tetris.getArea();
		if (input.isKeyPressed(Input.KEY_P)) {
			if (tetris.getCurrentState() != STATES.PAUSE_GAME_STATE) {
				tetris.setLastState(tetris.getCurrentState());
				area.hide();
				tetris.setCurrentState(STATES.PAUSE_GAME_STATE);
			}
			else {
				area.show();
				tetris.setCurrentState(tetris.getLastState());
			}
		}
		
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			tetris.setCurrentState(STATES.GAME_OVER_STATE);
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame bc, Graphics g) {
		// TODO Auto-generated method stub
		
	}

}
