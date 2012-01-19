package hakaplanet;

import hakaplanet.Skill.SKILLS;
import hakaplanet.Core.Entity;
import hakaplanet.Entities.TetrisEntity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.state.StateBasedGame;


/**
 * Class that handles all the skills 
 * we're using in tetris.
 * @author tojuhaka
 * @date 7.2.2011
 */
public class Skills extends Entity {

	private Skill timeStop;
	private TetrisEntity tetris;
	
	public Skills(TetrisEntity tetris) {
		this.tetris = tetris;
	}
	@Override
	public void init(GameContainer gc, StateBasedGame sb) {
		timeStop = new Skill(SKILLS.TIME_STOP, 10);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		Input input = gc.getInput();
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame bc, Graphics g) {
		// TODO Auto-generated method stub
		
	}
	
}
