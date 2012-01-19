package hakaplanet.Core;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Entity {
	
	private boolean enabled = true;
	private boolean visible = true;
	
	public boolean isVisible() {
		return visible;
	}
	
	/**
	 * Set Visibilty true for drawing
	 * @author tojuhaka
	 * @date 4.2.2011
	 */
	public void show() {
		this.visible = true;
	}
	
	public void hide() {
		this.visible = false;
	}
	/**
	 * Check if we can update the entity
	 * @author tojuhaka
	 * @date 4.2.2011
	 * @return
	 */
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public abstract void init(GameContainer gc, StateBasedGame sb) throws SlickException;
	public abstract void update(GameContainer gc, StateBasedGame sb, int delta);
	public abstract void render(GameContainer gc, StateBasedGame bc, Graphics g);
}
