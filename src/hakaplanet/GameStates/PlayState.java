package hakaplanet.GameStates;

import java.util.ArrayList;

import hakaplanet.Core.Entity;
import hakaplanet.Entities.BackgroundEntity;
import hakaplanet.Entities.MainInputEntity;
import hakaplanet.Entities.TetrisEntity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Handles all the playing by using differnt kinds of 
 * entities we've created.
 * @author tojuhaka
 * @date 6.2.2011
 */
public class PlayState extends BasicGameState {
	
	private TetrisEntity tetris;
	private MainInputEntity mainInput;
	private BackgroundEntity bgEntity;
	private int stateID;
	
	private ArrayList<Entity> entities = new ArrayList<Entity>();

	
	public PlayState(int stateID) {
		this.stateID = stateID;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sb)
			throws SlickException {
		tetris = new TetrisEntity(20, 20, 10, 20, 28);
		tetris.init(gc, sb);
		mainInput = new MainInputEntity(tetris);

		bgEntity = new BackgroundEntity(tetris.getX(), tetris.getY(), tetris.getArea());
		
		
		entities.add(bgEntity);
		entities.add(mainInput);
		entities.add(tetris);
		
		
		bgEntity.init(gc, sb);
		mainInput.init(gc, sb);

		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame bc, Graphics g)
			throws SlickException {
		for (Entity e : entities) {
			if (e.isVisible()) {
				e.render(gc, bc, g);
			}
		}
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta)
			throws SlickException {
		
		for (Entity e : entities) {
			if (e.isEnabled()) {
				e.update(gc, sb, delta);
			}
		}

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return stateID;
	}
	
	/**
	 * Method for changing states
	 */
	@Override
	public void enter(GameContainer gc, StateBasedGame sb)
			throws SlickException {
		super.enter(gc, sb);
		
		tetris.start();
	}
}
