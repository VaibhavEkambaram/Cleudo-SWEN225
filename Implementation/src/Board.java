/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5071.d9da8f6cd modeling language!*/


/**
 * Board Class
 * A board is made up of position classes, which are then all stored in this class
 */
// line 90 "model.ump"
// line 183 "model.ump"
public class Board {

    //------------------------
    // MEMBER VARIABLES
    //------------------------


    //Board Associations
    private Position[][] positions = new Position[25][24]; // Board is in (y, x) format

    //------------------------
    // CONSTRUCTOR
    //------------------------


    public void addPosition(int y, int x, Position position) {
        positions[y][x] = position; // Board is in (y, x) format
    }

    // get board position from input location
    public Position getBoardPosition(String inputTile){
        return null;
    }

    // move a player
    public boolean movePlayer(Player p,Position destination,int remainingMoves){
        return false;
    }


    public String toString() {
        StringBuilder boardPrint = new StringBuilder();
        boardPrint.append("      a b c d e f g h i j k l m n o p q r s t u v w x\n\n");
        for (int i = 0; i < positions.length; i++) {
            boardPrint.append(i+1).append(i + 1 < 10 ? "    " : "   ");
            Position[] position = positions[i];
            for (int j = 0; j < positions[0].length; j++) {
                boardPrint.append("|").append(position[j].toString());
            }
            boardPrint.append("|\n");
        }

        //System.out.println("Alpha to numeric index test: "+("k".charAt(0) & 31));

        return boardPrint.toString();
    }
}