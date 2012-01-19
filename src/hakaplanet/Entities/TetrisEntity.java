package hakaplanet.Entities;

import hakaplanet.Skill;
import hakaplanet.Core.Entity;
import hakaplanet.Core.Piece;
import hakaplanet.Core.PieceFactory;
import hakaplanet.Core.PlayArea;
import hakaplanet.Core.Tetris;
import hakaplanet.Core.Piece.Tuple;
import hakaplanet.Skill.SKILLS;

import java.awt.Font;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Main entity that handles all the logic and rendering 
 * of the game tetris. Other features are included as
 * different entities.
 * @author tojuhaka
 * @date 6.2.2011
 */
public class TetrisEntity extends Entity {

	private static final int MAXSPEED = 10;
	
	private Image pauseImage = null;
	private ArrayList<Image> blockImages;

	private PieceFactory pieceFactory;

	// Time a piece needs to wait before fallling
	private double deltaCounter = 500;

	// Control user input
	private int inputDelta = 0;

	private Piece currentPiece;
	private Piece nextPiece;
	private Tuple curPos;

	private int blockSize;

	static int AREA_X = 20;
	static int AREA_Y = 20;
	private int width = 10;
	private int height = 20;

	private PlayArea area;
	private LevelEntity level;
	
	// Delay the pressing and the keydown
	private boolean leftPressed = false;
	private boolean rightPressed = false;
	
	private int leftDelay = 600;
	private int rightDelay = 600;
	
	
	// Other features like skills, background etc
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	

	// Define states we're using in our game
	public enum STATES {
		START_STATE, NEW_PIECE_STATE, MOVE_PIECE_STATE, LINE_DESTRUCTION_STATE, PAUSE_GAME_STATE, HIGHSCORE_STATE, GAME_OVER_STATE;
	}

	private STATES currentState = null;
	public STATES getCurrentState() {
		return currentState;
	}

	public void setCurrentState(STATES currentState) {
		this.currentState = currentState;
	}

	private STATES lastState = null; // needed for pause
	
	public STATES getLastState() {
		return lastState;
	}

	public void setLastState(STATES lastState) {
		this.lastState = lastState;
	}

	// Constructor
	public TetrisEntity() {
		
	}
	// Constructor
	public TetrisEntity(int x, int y, int width, int height, int blockSize) {
		this.AREA_X = x;
		this.AREA_Y = y;
		this.blockSize = blockSize;
		this.width = width;
		this.height = height;
	
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sb)
			throws SlickException {
		
		
		currentState = STATES.START_STATE;
		lastState = currentState;
		
		pieceFactory = new PieceFactory();
		level = new LevelEntity(2);
		entities.add(level);

		pauseImage = new Image("data/pause.png");
		Image allImages = new Image("data/palikat.png");

		blockImages = new ArrayList<Image>();

		for (int i = 0; i < 7; i++)
			blockImages.add(allImages.getSubImage(0, i * 28, 28, 28));

		
		area = new PlayArea(width, height);
		
		this.show();
		for (Entity e : entities) {
			e.init(gc, sb);
			
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame bc, Graphics g) {
		
		for (Entity e : entities) {
			if (e.isVisible()) {
				e.render(gc, bc, g);	
			}
		}
		
		switch (currentState) {
			case PAUSE_GAME_STATE:
				drawPauseGame(gc, bc, g);
				break;

			default:
				drawGame(gc, bc, g); break;
		}
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
		g.setColor(Color.blue);
		g.drawRoundRect(19, 19, area.getNumberOfColumns() * blockSize,
				area.getNumberOfLines() * blockSize, 0);
		// Draw inserted blocks
		for (int line = 0; line < area.getNumberOfLines(); line++) {
			for (int column = 0; column < area.getNumberOfColumns(); column++) {
				int blockType = area.getBlockAt(column, line);

				if (blockType != -1) {
					blockImages.get(blockType).draw(
							column * blockSize + AREA_X,
							AREA_Y + (blockSize * (area.getNumberOfLines()- line - 1)), blockSize, blockSize);
					
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
										* blockSize, blockSize, blockSize);
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
										* blockSize, blockSize, blockSize);
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
		
		// Check level before updateExp
		int tempLevel = level.getLevel();
		
		switch (linesDestroyed) {
			case 1: level.updateExp(200); break;
			case 2: level.updateExp(400); break;
			case 3: level.updateExp(800); break;
			case 4: level.updateExp(1200); break;
		}
		
		// if the level has changed update speed
		if (tempLevel != level.getLevel()) {
			if (level.getLevel() <= MAXSPEED) {
				level.setSpeedMultiplier(level.getSpeedMultiplier()*0.7);
			}
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
			deltaCounter = 500*level.getSpeedMultiplier() ;
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
					inputDelta = 60;
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

	public void start() {
		area.makeCleanArea();
		currentState = STATES.START_STATE;
		lastState = currentState;
		
	}

	public int getY() {
		return AREA_Y;
	}

	public int getX() {
		
		return AREA_X;
	}

	public PlayArea getArea() {
		
		return area;
	}
	
	

}
