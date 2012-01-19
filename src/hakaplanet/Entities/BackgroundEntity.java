package hakaplanet.Entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import hakaplanet.Core.Entity;
import hakaplanet.Core.PlayArea;

/**
 * Simple background entity for game. Includes
 * the pit background and the base background.
 * @author tojuhaka
 * @date 6.2.2011
 */
public class BackgroundEntity extends Entity {

	private Image background;
	private Image areabg;
	private PlayArea area;
	private int AREA_X;
	private int AREA_Y;
	
	public BackgroundEntity(int AREA_X, int AREA_Y, PlayArea area) {
		
		this.AREA_X = AREA_X;
		this.AREA_Y = AREA_Y;
		this.area = area;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
	
		background = new Image("data/playbg.png");
		areabg = new Image("data/areabg.png");	

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame bc, Graphics g) {
		background.draw(0, 0);
		
		areabg.draw(AREA_X, AREA_Y, area.getNumberOfColumns()*28, area.getNumberOfLines()*28);
		
	}

}
