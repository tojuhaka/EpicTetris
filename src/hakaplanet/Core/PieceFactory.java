package hakaplanet.Core;

import hakaplanet.Core.Piece.Tuple;

public class PieceFactory {
	 
    private int counter = 0;
    /** Creates a new instance of PieceFactory */
    public PieceFactory( ) {
 
    }
 
    /**
     * Creates random piece from seven different types
     * @author tojuhaka
     * @date 27.1.2011
     * @return
     */
    public Piece generateRandomPiece() 
    {
        java.util.Random random = new java.util.Random();
 
        switch(random.nextInt(7))
        {
            case 0:  return generatePiece('t');
            case 1:  return generatePiece('s');
            case 2:  return generatePiece('z');
            case 3:  return generatePiece('o');
            case 4:  return generatePiece('i');
            case 5:  return generatePiece('l');
            case 6:  return generatePiece('j');
        }
 
        return null;
 
    }
   

	// Rotation Matrices
    static Tuple [][] TRotationMatrice = {
                    {new Tuple(0,0), new Tuple(-1,0), new Tuple(1,0), new Tuple(0,-1)},
                    {new Tuple(0,0), new Tuple(0,1), new Tuple(0,-1), new Tuple(-1,0)},
                    {new Tuple(0,0), new Tuple(1,0), new Tuple(-1,0), new Tuple(0,1)},
                    {new Tuple(0,0), new Tuple(0,-1), new Tuple(0,1), new Tuple(1,0)}
                };
 
    static Tuple [][] SRotationMatrice = {
                    {new Tuple(0,0), new Tuple(1,0), new Tuple(0,-1), new Tuple(-1,-1)},
                    {new Tuple(0,-1), new Tuple(-1,-1), new Tuple(-1,0), new Tuple(0,-2)},
                    {new Tuple(0,0), new Tuple(1,0), new Tuple(0,-1), new Tuple(-1,-1)},
                    {new Tuple(0,-1), new Tuple(-1,-1), new Tuple(-1,0), new Tuple(0,-2)}
                };
 
 
    static Tuple [][] ZRotationMatrice = {
                    {new Tuple(0,0), new Tuple(-1,0), new Tuple(0,-1), new Tuple(1,-1)},
                    {new Tuple(0,-1), new Tuple(-1,-1), new Tuple(-1,-2), new Tuple(0,0)},
                    {new Tuple(0,0), new Tuple(-1,0), new Tuple(0,-1), new Tuple(1,-1)},
                    {new Tuple(0,-1), new Tuple(-1,-1), new Tuple(-1,-2), new Tuple(0,0)}
                };
    

 
    static Tuple [][] ORotationMatrice = {
                    {new Tuple(0,0), new Tuple(1,0), new Tuple(0,-1), new Tuple(1,-1)},
                    {new Tuple(0,0), new Tuple(1,0), new Tuple(0,-1), new Tuple(1,-1)},
                    {new Tuple(0,0), new Tuple(1,0), new Tuple(0,-1), new Tuple(1,-1)},
                    {new Tuple(0,0), new Tuple(1,0), new Tuple(0,-1), new Tuple(1,-1)}
                };
 
  /*  static Tuple [][] IRotationMatrice = {
                    {new Tuple(0,0), new Tuple(-1,0), new Tuple(1,0), new Tuple(2,0)},
                    {new Tuple(0,0), new Tuple(0,-1), new Tuple(0,1), new Tuple(0,2)},
                    {new Tuple(0,0), new Tuple(-1,0), new Tuple(1,0), new Tuple(2,0)},
                    {new Tuple(0,0), new Tuple(0,-1), new Tuple(0,1), new Tuple(0,2)}
                };*/
    
    static Tuple [][] IRotationMatrice = {
        {new Tuple(0,0), new Tuple(-1,0), new Tuple(1,0), new Tuple(2,0)},
        {new Tuple(1,2), new Tuple(1,1), new Tuple(1,0), new Tuple(1,-1)},
        {new Tuple(0,0), new Tuple(-1,0), new Tuple(1,0), new Tuple(2,0)},
        {new Tuple(1,2), new Tuple(1,1), new Tuple(1,0), new Tuple(1,-1)}
    };
 
    static Tuple [][] LRotationMatrice = {
                    {new Tuple(0,0), new Tuple(1,0), new Tuple(-1,0), new Tuple(-1,-1)},
                    {new Tuple(0,0), new Tuple(0,-1), new Tuple(0,1), new Tuple(-1,1)},
                    {new Tuple(0,0), new Tuple(-1,0), new Tuple(1,0), new Tuple(1,1)},
                    {new Tuple(0,0), new Tuple(0,1), new Tuple(0,-1), new Tuple(1,-1)}
                };
 
   static Tuple [][] JRotationMatrice = {
                    {new Tuple(0,0), new Tuple(-1,0), new Tuple(1,0), new Tuple(1,-1)},
                    {new Tuple(0,0), new Tuple(0,1), new Tuple(0,-1), new Tuple(-1,-1)},
                    {new Tuple(0,0), new Tuple(1,0), new Tuple(-1,0), new Tuple(-1,1)},
                    {new Tuple(0,0), new Tuple(0,-1), new Tuple(0,1), new Tuple(1,1)}
                };
 
   /**
    * Generate a certain piece
    * @author tojuhaka
    * @date 27.1.2011
    * @param c - piece we want
    * @return
    */
   private Piece generatePiece(char pieceType) {
	   String name = ""+pieceType + counter++;
	   switch(pieceType) {
		   case 't': {
			   return new Piece(name,0, TRotationMatrice);
		   }
		   case 's': {
			   return new Piece(name,1, SRotationMatrice);
		   }
		   case 'z': {
			   return new Piece(name,2, ZRotationMatrice);
		   }
		   case 'o': {
			   return new Piece(name,3, ORotationMatrice);
		   }
		   case 'i': {
			   return new Piece(name,4, IRotationMatrice);
		   }
		   case 'l': {
			   return new Piece(name,5, LRotationMatrice);
		   }
		   case 'j': {
			   return new Piece(name,6,JRotationMatrice);
		   }

	   }
	   
	   return null;
	}
 
}
