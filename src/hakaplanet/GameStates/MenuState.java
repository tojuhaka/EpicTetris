package hakaplanet.GameStates;

import hakaplanet.Core.Tetris;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * State for main menu.
 * @author tojuhaka
 * @date 25.1.2011
 */
public class MenuState extends BasicGameState {

	private int stateID = -1;
	private Image background = null;
	private Image startOption = null;
	private Image exitOption = null;
	
	
	private int menuX;
	private int menuY;
	
	private float startScale = 1;
	private float exitScale = 1;
	private float scaleStep = 0.0001f;
	
	
	public MenuState(int stateID) {
		this.stateID = stateID;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sb)
			throws SlickException {

		background = new Image("data/background.png");

		// Load a single image and spawn several other imager from it
		Image menuOptions = new Image("data/startoptions.png");	
		startOption = menuOptions.getSubImage(0,0, 377, 71);	
		exitOption = menuOptions.getSubImage(0, 71, 377, 71);
		
		menuX = ((gc.getWidth() / 2)- menuOptions.getWidth());
		menuY = ((gc.getHeight() / 2));
	
	}

	@Override
	public void render(GameContainer gc, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		background.draw(0,0);
		startOption.draw(menuX, menuY, startScale);
		exitOption.draw(menuX, menuY+80, exitScale);
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int arg2)
			throws SlickException {
		Input input = gc.getInput();

		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		
		boolean inStart = false;
		boolean inExit = false;
		
		// Check if cursor is focusing menu
		if ((mouseX >= menuX && mouseX <= menuX + startOption.getWidth())
				&& (mouseY >= menuY && mouseY <= menuY
						+ startOption.getHeight())) {
			inStart = true;
		} else if ((mouseX >= menuX && mouseX <= menuX + exitOption.getWidth())
				&& (mouseY >= menuY + 80 && mouseY <= menuY + 80
						+ exitOption.getHeight())) {
			inExit = true;
		}
		int delta = 5;
		// If the cursor is pointing menuitem
		if (inStart) {
			
			// Nice scale-effect
			if (startScale < 1.05f) {
				startScale += scaleStep * delta;
			}
			
			// Press mouse left
			if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				sb.enterState(Tetris.PLAYSTATE);
			}
		} else {
			if (startScale > 1.0f) {
				startScale -= scaleStep * delta;
			}
		}
		
		if (inExit) {
			if (exitScale < 1.05f) {
				exitScale += scaleStep * delta;
			}
			
			if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				gc.exit();
			}
			
		} else {
			if (exitScale > 1.0f) {
				exitScale -= scaleStep * delta;
			}
		}
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			sb.enterState(Tetris.PLAYSTATE);
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return stateID;
	}
	
}
