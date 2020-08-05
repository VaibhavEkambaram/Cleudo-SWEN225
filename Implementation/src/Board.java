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
    public Board() {

    }

    // Clone Board Constructor with same positions
    public Board(Board board) {
        for (int i = 0; i < board.positions.length; i++) {
            for (int j = 0; j < board.positions[0].length; j++) {
                this.addPosition(i, j, board.positions[i][j]);
            }
        }
    }

    public void addPosition(int y, int x, Position position) {
        positions[y][x] = position; // Board is in (y, x) format
    }

    // get board position from input location
    public Position getBoardPosition(String inputTile) {
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
        // New Board
        Board board = new Board(this);
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
        if (board.positions[yLoc][xLoc] != playerPos) {
            throw new Error("Player position does not match with board position?");
        }

        // Check Direction
        switch (direction) {
            case UP:  // Vertical Movement
                yChange = -1;
                break;
            case DOWN:
                yChange = 1;
                break;
            case LEFT:  // Horizontal Movement
                xChange = -1;
                break;
            case RIGHT:
                xChange = 1;
                break;
        }

        // Apply movement
        while (board.positions[yLoc + yChange][xLoc + xChange] != null && spaces > 0) {
            Position nextPosition = board.positions[yLoc + yChange][xLoc + xChange];
            if (nextPosition.getIsRoom()) {
                if (!player.checkInRoom()) {
                    if (nextPosition.isIsDoor()) {
                        switch (nextPosition.getDoorDirection()) {
                            case UP:
                                if (yChange != -1) { // if not moving DOWN towards door
                                    return null;
                                }
                                break;
                            case LEFT:
                                if (xChange != 1) { // if not moving RIGHT towards door
                                    return null;
                                }
                                break;
                            case RIGHT:
                                if (xChange != -1) { // if not moving LEFT towards door
                                    return null;
                                }
                                break;
                            case DOWN:
                                if (yChange != 1) { // if not moving UP towards door
                                    return null;
                                }
                                break;
                        }
                    }
                } else if (!playerPos.getInRoom().equals(nextPosition.getInRoom())) {
                    break;
                }
            }
            if (nextPosition.getCanMove()) {
                player.setCurrentPosition(nextPosition);
                nextPosition.setCharacter(player.getCharacter());
                playerPos.removeCharacter();
                yLoc = nextPosition.getyLoc();
                xLoc = nextPosition.getxLoc();
            } else {
                System.out.println("Invalid move");
                return null;
            }
            playerPos = player.getCurrentPosition();
            spaces--;
        }
        return board;
    }
}