package hakaplanet.BackUp;

import java.util.Date;

/**
 * Simple skill with cooldown timer so we cant use it
 * all the time.
 * @author tojuhaka
 * @date 3.2.2011
 */
public class SkillThread implements Runnable  {
	
	// Why public? Cause we need it in other classes as well to
	// mark which skill is the right one
	public enum SKILLS {
		TIME_STOP;
	}
	
	
	// Cooldown in secs
	private int cooldown;
	private SKILLS skillName;
	
	private boolean isReady = false;
		
	private Thread cast;
	private Date date;
	
	public SkillThread(SKILLS skillName, int cooldown ) {
		this.skillName = skillName;
		this.cooldown = cooldown;
		date = new Date();
	}
	
	public void start() {
		 cast = new Thread(this);
		 cast.start();
		
	}
	
	public void stop() {
		
	}
	public int getCooldown() {
		return cooldown;
	}
	@Override
	public void run() {
		long startTime = date.getTime();
		while ((date.getTime()-startTime) < cooldown ) {
			date = new Date();
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("Skill Thread Problem");
			}
			isReady = false;
			
		}
		isReady = true;
	}
	

	public boolean isReady() {
		return isReady;
	}

	public SKILLS getSkillName() {
		return skillName;
	}

}
