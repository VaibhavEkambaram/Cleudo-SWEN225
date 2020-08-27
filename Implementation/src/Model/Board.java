package Model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5071.d9da8f6cd modeling language!*/


import java.lang.reflect.Array;

/**
 * Model.Board Class
 * A board is made up of position classes, which are then all stored in this class
 */
// line 90 "model.ump"
// line 183 "model.ump"
public class Board {
    //Model.Board Positions
    private final Position[][] positions = new Position[25][24]; // Model.Board is in (y, x) format

    // Constructors
    public Board() {
    }

    // Clone Model.Board with same positions
    public Board(Board board) {
        for (int i = 0; i < board.positions.length; i++) {
            for (int j = 0; j < board.positions[0].length; j++) {
                this.addPosition(i, j, board.positions[i][j].clone());
            }
        }
    }


    /**
     * Adds a Model.Position to the Model.Board Positions Array
     * Only used for the initial Model.Board, not used in Cloned Boards
     *
     * @param y        y co-ordinate
     * @param x        x co-ordinate
     * @param position position
     */
    public void addPosition(int y, int x, Position position) {
        positions[y][x] = position; // Model.Board is in (y, x) format
    }


    /**
     * Print out the game board
     *
     * @return board print
     */
    public String toString() {
        StringBuilder boardPrint = new StringBuilder();
        boardPrint.append("      a b c d e f g h i j k l m n o p q r s t u v w x\n\n");
        for (int i = 0; i < positions.length; i++) {
            boardPrint.append(i + 1).append(i + 1 < 10 ? "    " : "   ");
            Position[] position = positions[i];
            for (int j = 0; j < positions[0].length; j++) {
                boardPrint.append("|").append(position[j].toString());
            }
            boardPrint.append("|\n");
        }
        return boardPrint.toString();
    }

    public Position[][] getPositions(){
        return this.positions;
    }


    /**
     * Create a new board with applied Model.Move action
     *
     * @param player player
     * @param move   move
     * @return Model.Board
     * @author Cameron Li
     */
    public Board apply(Player player, Move move) {
        Board cloneBoard = new Board(this);

        Position playerPos = player.getCurrentPosition();
        int x = playerPos.getLocationX();
        int y = playerPos.getLocationY();

        // Check Model.Player clone Model.Position matches original Model.Position
        if (cloneBoard.positions[y][x].getCharacter() != player.getCharacter()) {
            return null;
        }

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

            if (playerPos.getRoom() == null) { // Model.Player is not in Model.Room
                if (nextPosition.getRoom() != null) { // Attempt to enter Model.Room
                    // Model.Player is attempting to enter room but isn't going through the door
                    if (!nextPosition.isDoor()) {
                        return null;
                    }
                    // Model.Player is entering through door, check matching direction
                    if (!checkDoorMovement(nextPosition, dx, dy, true)) {
                        return null;
                    }
                }
            } else { // Model.Player is in Model.Room
                // Model.Player must be leaving door through proper direction
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
                x = playerPos.getLocationX();
                y = playerPos.getLocationY();
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

    /**
     * Teleports a player to a Model.Room
     * Used for Suggestions
     *
     * @param player player
     * @param room   room
     * @return copy of board
     * @author Cameron Li
     */
    public Board teleportPlayer(Player player, Room room) {
        Board cloneBoard = new Board(this);
        for (int i = 0; i < cloneBoard.positions.length; i++) {
            for (int j = 0; j < cloneBoard.positions[0].length; j++) {
                Position found = cloneBoard.positions[i][j];
                if (found.getRoom() != null) {
                    if (found.getRoom().equals(room) && found.isPassableTile() && !found.isDoor() && found.getWeapon()==null && found.getCharacter()==null) {
                        int y = player.getCurrentPosition().getLocationY();
                        int x = player.getCurrentPosition().getLocationX();
                        cloneBoard.positions[y][x].removeCharacter();
                        found.setCharacter(player.getCharacter());
                        player.setCurrentPosition(found);
                        return cloneBoard;
                    }
                }
            }
        }
        return null;
    }








    // Model.Board Valid Movement Checks

    /**
     * Check if co-ordinates are within game board
     *
     * @param x x co-ordinate
     * @param y y co-ordinate
     * @return out of bounds boolean
     */
    private boolean outOfBounds(int x, int y) {
        if (x < 0 || y < 0) {
            return true;
        }
        return x > positions.length || y > positions[0].length;
    }

    /**
     * Check if movement on/towards Door Model.Position is valid
     * Must be moving towards the right direction depending on direction of door
     *
     * @param checkPosition position
     * @param dx            change by x
     * @param dy            change by y
     * @param enter         enter
     * @return door validate boolean
     */
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