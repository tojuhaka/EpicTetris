package hakaplanet.BackUp;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Timer;

import hakaplanet.Skill;
import hakaplanet.Skill.SKILLS;
import hakaplanet.Core.Entity;
import hakaplanet.Core.Piece;
import hakaplanet.Core.PieceFactory;
import hakaplanet.Core.PlayArea;
import hakaplanet.Core.Tetris;
import hakaplanet.Core.Piece.Tuple;
import hakaplanet.Entities.BackgroundEntity;
import hakaplanet.Entities.LevelEntity;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Playstate for tetris. The main logic and drawing
 * is in this class. Most of the other features are
 * entities, such as skills, background etc. The reason
 * for that is we dont want the playstate to be huge.
 * TODO: Wanna make tetris entity?
 * 
 * @author tojuhaka
 * @date 25.1.2011
 */
public class PlayStateBackUp extends BasicGameState {

	private int stateID = -1;
	private Image pauseImage = null;
	private ArrayList<Image> blockImages;

	private PieceFactory pieceFactory;

	// Time a piece needs to wait before fallling
	private int deltaCounter = 500;

	// Control user input
	private int inputDelta = 0;

	private Piece currentPiece;
	private Piece nextPiece;
	private Tuple curPos;

	private int blockSize;

	static int AREA_X = 20;
	static int AREA_Y = 20;

	private PlayArea area;
	private LevelEntity level;
	
	//TODO: deprecation, wait wut?
	@SuppressWarnings("deprecation")
	TrueTypeFont trueTypeFont = null;
	
	// Delay the pressing and the keydown
	private boolean leftPressed = false;
	private boolean rightPressed = false;
	
	private int leftDelay = 600;
	private int rightDelay = 600;
	
	private Skill skill = new Skill(SKILLS.TIME_STOP, 5000);
	
	// Other features like skills, background etc
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	
	private Entity bgEntity;

	// Define states we're using in our game
	private enum STATES {
		START_STATE, NEW_PIECE_STATE, MOVE_PIECE_STATE, LINE_DESTRUCTION_STATE, PAUSE_GAME_STATE, HIGHSCORE_STATE, GAME_OVER_STATE;
	}

	private STATES currentState = null;
	private STATES lastState = null; // needed for pause


	/**
	 * Constructor for play state
	 * 
	 * @param stateID
	 *            - Unique ID for state
	 */
	public PlayStateBackUp(int stateID) {
		this.stateID = stateID;
	}

	/**
	 * Method for changing states
	 */
	@Override
	public void enter(GameContainer gc, StateBasedGame sb)
			throws SlickException {
		super.enter(gc, sb);

		area.makeCleanArea();
		currentState = STATES.START_STATE;
		lastState = currentState;

	}

	@Override
	public void init(GameContainer gc, StateBasedGame sb)
			throws SlickException {
		
		pieceFactory = new PieceFactory();
		level = new LevelEntity(2);

		pauseImage = new Image("data/pause.png");
		Image allImages = new Image("data/palikat.png");

		blockImages = new ArrayList<Image>();

		for (int i = 0; i < 7; i++)
			blockImages.add(allImages.getSubImage(0, i * 28, 28, 28));

		blockSize = 28;
		area = new PlayArea(10, 20);

		Font font = new Font("Verdana", Font.BOLD, 40);
		trueTypeFont = new TrueTypeFont(font, true);
		
		bgEntity = new BackgroundEntity(AREA_X, AREA_Y, area);
		bgEntity.show();
		entities.add(bgEntity);

		
		
		for (Entity e : entities) {
			e.init(gc, sb);
			
		}

	}

	@Override
	public void render(GameContainer gc, StateBasedGame bc, Graphics g)
			throws SlickException {
		
		for (Entity e : entities) {
			if (e.isVisible()) {
				e.render(gc, bc, g);	
			}
		}
		
		switch (currentState) {
			case PAUSE_GAME_STATE:
				drawBackGround(gc, bc, g);
				drawPauseGame(gc, bc, g);
				break;

			default:
				drawBackGround(gc, bc, g);
				drawGame(gc, bc, g); break;
		}
	}

	/**
	 * draws solid background
	 * @author tojuhaka
	 * @date 2.2.2011
	 * @param gc
	 * @param bc
	 * @param g
	 */
	private void drawBackGround(GameContainer gc, StateBasedGame bc, Graphics g) {
		
		
		trueTypeFont.drawString(500, 20, "Level: "+level.getLevel());
		trueTypeFont.drawString(500, 70, "" + level.getExp() + " / " + level.getCap());
		
	}

	/**
	 * Draws the normal state of the game
	 * @author tojuhaka
	 * @date 2.2.2011
	 * @param gc
	 * @param bc
	 * @param g
	 */
	private void drawGame(GameContainer gc, StateBasedGame bc, Graphics g) {
		bgEntity.show();
		g.setColor(Color.blue);
		g.drawRoundRect(19, 19, area.getNumberOfColumns() * 28,
				area.getNumberOfLines() * 28, 0);
		// Draw inserted blocks
		for (int line = 0; line < area.getNumberOfLines(); line++) {
			for (int column = 0; column < area.getNumberOfColumns(); column++) {
				int blockType = area.getBlockAt(column, line);

				if (blockType != -1) {
					blockImages.get(blockType).draw(
							column * blockSize + AREA_X,
							AREA_Y
									+ (blockSize * (area.getNumberOfLines()
											- line - 1)));
				}
			}
		}
		

		// draw currentPiece
		if (currentPiece != null) {
			for (int i = 0; i < 4; i++) {
				Tuple blockPos = currentPiece.getPosOfBlock(i);
				blockImages
						.get(currentPiece.getImageId())
						.draw(AREA_X + (blockPos.x + curPos.x) * blockSize,
								AREA_Y
										+ (area.getNumberOfLines() - 1 - (blockPos.y + curPos.y))
										* blockSize);
			}
		}

		// draw nextPiece TODO: solve mirrormystery
		if (nextPiece != null) {
			for (int i = 0; i < 4; i++) {
				Tuple blockPos = nextPiece.getPosOfBlock(i);
				blockImages
						.get(nextPiece.getImageId())
						.draw(AREA_X + 336 + (blockPos.x) * blockSize,
								AREA_Y
										- 500
										+ (area.getNumberOfLines() - 1 - (blockPos.y))
										* blockSize);
			}
		}	
	}

	/**
	 * Draw some cool stuff when the game is paused. And of course
	 * hide all other draws, so the player can't see the game during
	 * pause
	 * @author tojuhaka
	 * @date 2.2.2011
	 * @param gc
	 * @param bc
	 * @param g
	 */
	private void drawPauseGame(GameContainer gc, StateBasedGame bc, Graphics g) {
		pauseImage.draw(AREA_X + 50, AREA_Y+200,200, 200);
		bgEntity.hide();
	
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sb, int delta) {
		
	
		switch (currentState) {
		case START_STATE:
			currentState = STATES.NEW_PIECE_STATE;
			deltaCounter = 500;
			break;
		case NEW_PIECE_STATE:
			generateNewPiece();
			break;
		case MOVE_PIECE_STATE:
			updatePiece(gc, sb, delta); // TODO
			break;
		case LINE_DESTRUCTION_STATE:
			checkForFullLines(gc, sb, delta);
			currentState = STATES.NEW_PIECE_STATE;
			break;
		case HIGHSCORE_STATE:
			break;
		case PAUSE_GAME_STATE:
			break;
		case GAME_OVER_STATE:
			level.reset();
			
			sb.enterState(Tetris.MENUSTATE);
			break;
		}
		
		for (Entity e : entities) {
			if (e.isEnabled()) {
				e.update(gc, sb, delta);
			}
		}
		
		// Handle some inputs during game
		Input input = gc.getInput();
		
		if (input.isKeyPressed(Input.KEY_P)) {
			if (currentState != STATES.PAUSE_GAME_STATE) {
				skill.use();
				lastState = currentState;
				area.hide();
				currentState = STATES.PAUSE_GAME_STATE;
			}
			else {
				area.show();
				currentState = lastState;
			}
		}
	}

	private void checkForFullLines(GameContainer gc, StateBasedGame sb,
			int delta) {
		int linesDestroyed = 0;
		
		for (int i=0; i < area.getNumberOfLines();) {
			if (area.isLineFull(i)) {
				area.destroyLine(i);
				linesDestroyed++;
			}
			else i++;
		}
		
		switch (linesDestroyed) {
			case 1: level.updateExp(100); break;
			case 2: level.updateExp(200); break;
			case 3: level.updateExp(300); break;
			case 4: level.updateExp(400); break;
		}
		
	}

	/**
	 * Update the piece. Check if the piece is insertable and alsto handle the
	 * user input.
	 * 
	 * @author tojuhaka
	 * @date 27.1.2011
	 * @param gc
	 * @param sb
	 * @param delta
	 */
	private void updatePiece(GameContainer gc, StateBasedGame sb, int delta) {
		Tuple newCurPos = new Tuple(curPos.x, curPos.y);

		deltaCounter -= delta;
		inputDelta -= delta;
		
		if (deltaCounter < 0) {
			newCurPos.y -= 1;

			if (!area.isPieceInsertable(currentPiece, newCurPos)) {
				// Insert piece into playarea
	
				area.insertPieceAt(currentPiece, (int) newCurPos.x,
						(int) newCurPos.y+1);
				currentState = STATES.LINE_DESTRUCTION_STATE;
				return;
			}
			deltaCounter = 500;
		}
		Input input = gc.getInput();

		if (inputDelta < 0) {
			if (input.isKeyPressed(Input.KEY_LEFT)) {
				leftPressed = true;
				
				
				newCurPos.x -= 1;
				if (!area.isPieceInsertable(currentPiece, newCurPos)) {
					newCurPos.x += 1;
				} else {
					inputDelta = 150;
				}
				leftDelay=450;
				
			}
			
			//Check if the key is pressed and wait for sometime
			// so it's like in windowtetris "yay"
			if (input.isKeyDown(Input.KEY_LEFT) && leftDelay <0 ) {
				newCurPos.x -= 1;
				if (!area.isPieceInsertable(currentPiece, newCurPos)) {
					newCurPos.x += 1;
				} else {
					inputDelta = 50;
				}
				leftPressed = false;
				
			}
			
			
			if (input.isKeyPressed(Input.KEY_RIGHT)) {
				rightPressed = true;
				
				newCurPos.x += 1;
				if (!area.isPieceInsertable(currentPiece, newCurPos)) {
					newCurPos.x -= 1;
				} else {
					inputDelta = 150;
				}
				rightDelay=450;
				
				
			}
			
			if (input.isKeyDown(Input.KEY_RIGHT) && rightDelay < 0) {
				newCurPos.x += 1;
				if (!area.isPieceInsertable(currentPiece, newCurPos))
					newCurPos.x -= 1;
				else
					inputDelta = 50;
				rightPressed = false;
			}
			
			
			if (input.isKeyPressed(Input.KEY_UP)) {
					currentPiece.rotateRight();
				
				if (!area.isPieceInsertable(currentPiece, newCurPos)) 
					currentPiece.rotateLeft();
			
					// Exception: if we're near the edge of
					// the area, we need to move the position
					// that we can continue rotating the piece
				if (!currentPiece.getName().contains("i")) {
					if (newCurPos.x == 0) {
						newCurPos.x += 1;
						if(!area.isPieceInsertable(currentPiece, newCurPos)) {
								newCurPos.x -= 1;
						}
						else currentPiece.rotateRight();
	
					}
					else if (newCurPos.x == area.getNumberOfColumns() - 1) {
						newCurPos.x -= 1;
						if(!area.isPieceInsertable(currentPiece, newCurPos)) {
							newCurPos.x += 1;
						}
						else currentPiece.rotateRight();
					}
				 }
				// If the current piece is "I" we need to check few things
				// that we got the same "gaming experience" as the original tetris
				// Yeah I know, it's ugly code, but I didnt come up with a better idea.
				// The center point of the piece changes, so we need to check the edges.
				else if (currentPiece.getName().contains("i")) {
					if (newCurPos.x == -1) {
						currentPiece.rotateRight();
						newCurPos.x += 2;
						if(!area.isPieceInsertable(currentPiece, newCurPos)) {
							newCurPos.x -= 2;
							currentPiece.rotateLeft();
						}
					}
					else if (newCurPos.x == 0) {
						currentPiece.rotateRight();
						newCurPos.x += 1;
						if(!area.isPieceInsertable(currentPiece, newCurPos)) {
							newCurPos.x -= 1;
							currentPiece.rotateLeft();
						}
					}
					
					// if center point at the right edge
					else if (newCurPos.x == area.getNumberOfColumns() - 2) {
						currentPiece.rotateRight();
						newCurPos.x -= 1;
						if(!area.isPieceInsertable(currentPiece, newCurPos)) {
							newCurPos.x += 1;
							currentPiece.rotateLeft();
						}
					}
				}
								
				else inputDelta = 150;
				
				
			}
			if (input.isKeyDown(Input.KEY_DOWN)) {
				newCurPos.y -= 1;
				if (!area.isPieceInsertable(currentPiece, newCurPos)) {
					newCurPos.y += 1;
				} else
					inputDelta = 100;
			}
			
			
			if (input.isKeyPressed(Input.KEY_SPACE)) {
				while (area.isPieceInsertable(currentPiece, newCurPos))
					newCurPos.y -= 1;
				newCurPos.y += 1;
				deltaCounter = -1;
			}
			
			//Delays for left and right
			//TODO Better solution
			if (leftPressed) {
				leftDelay--;
			}
			if (rightPressed) {
				rightDelay--;
			}
		}	
		curPos = new Tuple(newCurPos.x, newCurPos.y);
	} // updatePiece() ends

	private void generateNewPiece() {
		if (currentPiece == null) {
			nextPiece = pieceFactory.generateRandomPiece();
		}

		curPos = new Tuple(5, 19);
		currentPiece = nextPiece;
		if (area.isPieceInsertable(currentPiece, curPos)) {
			nextPiece = pieceFactory.generateRandomPiece();
			currentState = STATES.MOVE_PIECE_STATE;
		} else {
			currentState = STATES.GAME_OVER_STATE;
		}

	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return stateID;
	}

}
