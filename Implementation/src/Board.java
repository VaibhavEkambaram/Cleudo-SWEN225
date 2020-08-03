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

    /**
     * Create a new board with applied Move action
     *
     * @param player
     * @param move
     * @return
     * @author Cameron Li
     */
    public Board apply(Player player, Move move) {
        // Player Position
        Position playerPos = player.getCurrentPosition();
        int xLoc = playerPos.getxLoc();
        int yLoc = playerPos.getyLoc();

        // Move information
        int spaces = move.getSpaces();
        Move.Direction direction = move.getDirection();

        // Movement Adjustment
        int xChange = 0;
        int yChange = 0;
        if (positions[yLoc][xLoc] != playerPos) {
            throw new Error("Player position does not match with board position?");
        }
        if (direction.equals(Move.Direction.UP)) { // Vertical Movement
            yChange = -1;
        } else if (direction.equals(Move.Direction.DOWN)) {
            yChange = 1;
        } else if (direction.equals(Move.Direction.LEFT)) { // Horizontal Movement
            xChange = -1;
        } else if (direction.equals(Move.Direction.RIGHT)) {
            xChange = 1;
        }
        return null;
    }
}