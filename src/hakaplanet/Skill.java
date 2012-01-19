package hakaplanet;

import hakaplanet.Core.Entity;

import java.util.Date;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Skill extends Entity {
	public enum SKILLS {
		TIME_STOP;
	}
	
	// Position
	private int x;
	private int y;
	// Cooldown in secs
	private int cooldown;
	private SKILLS skillName;
	
	// TEST
	private String name = "TIME STOP";
	
	private boolean isReady = false;
		
	private Date date;
	private long startTime;
	
	public Skill(SKILLS skillName, int cooldown ) {
		this.skillName = skillName;
		this.cooldown = cooldown;
		date = new Date();
	}

	public int getCooldown() {
		return cooldown;
	}

	public void use() {
		if (isReady) {
			date = new Date();
			startTime = date.getTime();
			isReady = false;
		}
	}

	public boolean isReady() {
		date = new Date();
		if (date.getTime()-startTime < cooldown) {
			isReady = false;
		}
		else isReady = true;
		return isReady;
	}

	public SKILLS getSkillName() {
		return skillName;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sb) throws SlickException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame bc, Graphics g) {
		// TODO Auto-generated method stub
		
	}
}
