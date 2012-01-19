package hakaplanet.Core;

import java.util.ArrayList;

import hakaplanet.Core.Piece.Tuple;
/**
 * Gamearea, like a map for tetris. Includes
 * basic actions just like inserting piece,
 * removing lines etc.
 * @author tojuhaka
 * @date 27.1.2011
 */
public class PlayArea {
	private int width;
	private int height;
	
	private boolean show;
	
	ArrayList<int[]> area = null;
	
	public PlayArea(int width, int height) {
		this.width = width;
		this.height = height;
		
		area = new ArrayList<int[]>();
		show = true;
		makeCleanArea();
	}

	/**
	 * Clear the area from all blocks
	 * @author tojuhaka
	 * @date 27.1.2011
	 */
	public void makeCleanArea() {
		area.clear();
		
		for (int y = 0; y < height; y++) {
			addNewLine();
		}
	}

	/**
	 * Pit a piece in specific location
	 * 
	 * @author tojuhaka
	 * @date 27.1.2011
	 * @param piece
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean insertPieceAt(Piece piece, int x, int y) {
		Tuple insertPos = new Tuple(x, y);

		if(isPieceInsertable(piece, insertPos )) {
			for (int i = 0; i < 4; i++) {
				setBlockAt(piece.getImageId(), new Tuple(piece.getPosOfBlock(i).x
				+ insertPos.x, piece.getPosOfBlock(i).y + insertPos.y));
			}
			return true;
		}
		return false;
	}

	private void setBlockAt(int value, Tuple pos) {
		setBlockAt(value, (int)pos.x, (int)pos.y);
	}

	private void setBlockAt(int value, int x, int y) {
		try {
			area.get(y)[x] = value;
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Unexpected Exception at setBlock(value,x,y");
			System.out.println("x = " + x + " y = " + y);
			e.printStackTrace();
		}
	}

	/**
	 * Check if the piece we're moving is able to move
	 * @author tojuhaka
	 * @date 27.1.2011
	 * @param piece 
	 * @param iPos - position where piece are trying to move
	 * @return 
	 */
	public boolean isPieceInsertable(Piece piece, Tuple iPos) {
		boolean isInsertable = true;
		int blockType = 0;
		for (int pieceBlock = 0; pieceBlock < 4 && isInsertable; pieceBlock++) {
			Tuple blockPos = piece.getPosOfBlock(pieceBlock);
			/*	try {
				blockType = getBlockAt( (int)(iPos.x + blockPos.x), (int)(iPos.y + blockPos.y));
			} catch (ArrayIndexOutOfBoundsException e) {
				isInsertable = false;
				e.printStackTrace();
				System.out.println("Exception: isInsertable");
				return isInsertable;
			}	*/	

			
			isInsertable &= ( iPos.x + blockPos.x >= 0 && iPos.x + blockPos.x < width)
            && (iPos.y + blockPos.y >= 0 && iPos.y + blockPos.y < height)
            && ( getBlockAt( (int)(iPos.x + blockPos.x), (int)(iPos.y + blockPos.y)) == -1 );
			
			}
			
		return isInsertable;
	}
	
	public boolean isPieceRotatable(Piece piece, Tuple iPos) {
		boolean isRotatable = true;
		piece.rotateRight();
		
		for (int pieceBlock = 0; pieceBlock < 4 && isRotatable; pieceBlock++) {
			Tuple blockPos = piece.getPosOfBlock(pieceBlock);
			System.out.println(iPos.x + " " + blockPos.x);
			if (iPos.x + blockPos.x < 0) {
				piece.rotateLeft();
				return false;
			}
			
		}
		piece.rotateLeft();
		return true;

		
	}

	public int getBlockAt(int x, int y) {
		return area.get(y)[x];
	}

	/**
	 * Add new line into the area
	 * @author tojuhaka
	 * @date 28.1.2011
	 */
	public void addNewLine() {
		int[] line = new int[width];
		for (int x = 0; x < width; x++) {
			line[x] = -1;
		}
		
		area.add(line);
		
	}
	
	public int getNumberOfColumns() {
		return width;
	}
	
	public int getNumberOfLines() {
		return height;
	}
	
	/**
	 * Check if the line is full of blocks
	 * (no empty blocks)
	 * @author tojuhaka
	 * @date 29.1.2011
	 * @param lineId 
	 * @return
	 */
	public boolean isLineFull(int lineId) {
		//TODO CHECK THIS
		int[] line =  area.get(lineId);
		for (int i=0; i < line.length; i++) {
			if (line[i] == -1) return false;
		}
		return true;
	}
	
	/**
	 * Erase one line, then add new line
	 * @author tojuhaka
	 * @date 29.1.2011
	 * @param i - line index
	 */
	public void destroyLine(int i) {
		area.remove(i);
		addNewLine();
	}
	
	public void hide() {
		show = false;
	}
	
	public void show() {
		show = true;
	}
	
	
}
