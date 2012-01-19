package hakaplanet.Core;


/**
 * Class for a piece in tetris. Receives a rotation Matrix 4×4 in 
 * size each element containing a Tuple class. this "piece" is just
 * a set of four blocks. Every piece has a central block and all the
 * other pieces are related to that. For example "T" piece:
 * [1][2][3]
 *    [4]
 * 1 = -1,0
 * 2 = 0,0 (central block)
 * 3 = 1,0
 * 4 = 0,-1  
 * @author tojuhaka
 * @date 26.1.2011
 */
public class Piece {

	private String name;
	
	// t=0 s=1 z=2 o=3 i=4 l=5 j=6
	// the order comes from the image
	private int imageId;
	
	Tuple[][] rotationMatrice;
	int currentRotation;
	
	/**
	 * Constructor for Piece
	 * @param name - name/id of the piece
	 * @param rotationMatrice - rotation variations
	 */
	public Piece(String name, int imageId, Tuple[][] rotationMatrice) {
		this.name = name;
		this.imageId = imageId;
		currentRotation = 0;

		this.rotationMatrice = rotationMatrice;
	}
	
	/**
	 * Returns the position of block relative to the central block
	 * @author tojuhaka
	 * @date 26.1.2011
	 * @param blockNumber 
	 * @return
	 */
	public Tuple getPosOfBlock ( int blockNumber ) {
		return rotationMatrice[currentRotation][blockNumber];
	}

	
	public void rotateRight() {
		
		currentRotation++;
		if (currentRotation >= rotationMatrice.length) {
			currentRotation = 0;
		}
	}

	public void rotateLeft() {
		currentRotation--;
		if (currentRotation < 0) {
			currentRotation = rotationMatrice.length-1;
		}
	}
	
	/**
	 * ImageId is the order number of the image
	 * we load. For example if we load an image that
	 * has six blocks and we take 6 subimages the first is 
	 * number 0 and second is number 1 etc.
	 * @author tojuhaka
	 * @date 28.1.2011
	 * @return
	 */
	public int getImageId() {
		return imageId;
	}

	public String getName() {
		return name;
	}
	/**
	 * Simple "coordinate" class
	 * 
	 * @author tojuhaka
	 * @date 26.1.2011
	 */
	public static class Tuple {
		public int x;
		public int y;

		public Tuple(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}
