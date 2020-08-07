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
                this.addPosition(i, j, board.positions[i][j].clone());
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
        Board cloneBoard = new Board(this);

        Position playerPos = player.getCurrentPosition();
        int x = playerPos.getxLoc();
        int y = playerPos.getyLoc();

        // Check Player clone Position matches original Position
        if (cloneBoard.positions[y][x].getCharacter() != player.getCharacter()) {
            return null;
        }

        Move.Direction direction = move.getDirection();
        int spaces = move.getSpaces();
        int dx = move.xChange();
        int dy = move.yChange();
        while (spaces > 0) {
            if (outOfBounds(x + dx, y + dy)) { // Check boundary errors
                System.out.println("Out of bounds");
                return null;
            }

            playerPos = cloneBoard.positions[y][x];
            Position nextPosition = cloneBoard.positions[y + dy][x + dx];

            if (playerPos.getRoom() == null) { // Player is not in Room
                if (nextPosition.getRoom() != null) { // Attempt to enter Room
                    // Player is attempting to enter room but isn't going through the door
                    if (!nextPosition.isDoor()) {
                        return null;
                    }
                    // Player is entering through door, check matching direction
                    if (!checkDoorMovement(nextPosition, dx, dy, true)) {
                        return null;
                    }
                }
            } else { // Player is in Room
                // Player must be leaving door through proper direction
                if (playerPos.isDoor()) {
                    if (nextPosition.getRoom() == null && !checkDoorMovement(playerPos, dx, dy, false)) {
                        return null;
                    }
                    // Not leaving through Door
                } else if (!playerPos.getRoom().equals(nextPosition.getRoom())) {
                    return null;
                }
            }

            // Apply Movement
            if (nextPosition.canMove()) {
                playerPos.removeCharacter();
                playerPos = nextPosition;
                nextPosition.setCharacter(player.getCharacter());

                spaces--;
                x = playerPos.getxLoc();
                y = playerPos.getyLoc();
            } else {
                return null;
            }
            if (spaces == 0) { // Movement has been fully applied
                player.setCurrentPosition(playerPos);
                return cloneBoard;
            }
        }
        return null;
    }

    private boolean outOfBounds(int x, int y) {
        if (x < 0 || y < 0) {
            return true;
        }
        if (x > positions.length || y > positions[0].length) {
            return true;
        }
        return false;
    }

    private boolean checkDoorMovement(Position checkPosition, int dx, int dy, boolean enter) {
        if (!enter) {
            switch (checkPosition.getDoorDirection()) {
                case UP:
                    return (dy == -1);
                case DOWN:
                    return (dy == 1);
                case LEFT:
                    return (dx == -1);
                case RIGHT:
                    return (dx == 1);
                default:
                    return false;
            }
        } else {
            switch (checkPosition.getDoorDirection()) {
                case UP:
                    return (dy == 1);
                case DOWN:
                    return (dy == -1);
                case LEFT:
                    return (dx == 1);
                case RIGHT:
                    return (dx == -1);
                default:
                    return false;
            }
        }
    }


}