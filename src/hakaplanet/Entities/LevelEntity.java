package hakaplanet.Entities;

import java.awt.Font;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;

import hakaplanet.Core.Entity;

/**
 * Simple class for handling level and exp
 * @author tojuhaka
 * @date 29.1.2011
 */
public class LevelEntity extends Entity {
	
	private int level;
	private long exp;
	private long expCap;
	
	// We'll grow the expcap so it's harder
	// to get lvl at higher lvls
	private double capMultiplier;
	
	// Each lvl we raise the speed of a piece
	private double speedMultiplier;
	public double getSpeedMultiplier() {
		return speedMultiplier;
	}

	public void setSpeedMultiplier(double speedMultiplier) {
		this.speedMultiplier = speedMultiplier;
	}

	//TODO: deprecation, wait wut?
	@SuppressWarnings("deprecation")
	TrueTypeFont trueTypeFont = null;
	
	public LevelEntity(double capMultiplier) {
		this.capMultiplier = capMultiplier;
		init();
	}
	
	public void init() {
		exp = 0;
		level = 1;
		this.speedMultiplier = 1;
		// when we reach the cap, we get lvl
		expCap = 1000;
	}
	
	public void updateExp(int amount ) {
		exp += amount;
		if (exp >= expCap) {
			level++;
			exp = exp - expCap;
			expCap *=capMultiplier;
		}
	}
	
	/**
	 * Returns experience cap. After reaching
	 * the cap we get lvl and a new cap is set.
	 * @author tojuhaka
	 * @date 29.1.2011
	 * @return
	 */
	public long getCap() {
		return expCap;
	}
	
	public long getExp() {
		return exp;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void reset() {
		init();
		
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		Font font = new Font("Verdana", Font.BOLD, 40);
		trueTypeFont = new TrueTypeFont(font, true);	
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame bc, Graphics g) {
		trueTypeFont.drawString(500, 20, "Level: "+getLevel());
		trueTypeFont.drawString(500, 70, "" + getExp() + " / " + getCap());
		
	}
	
	
	
	
	
}
