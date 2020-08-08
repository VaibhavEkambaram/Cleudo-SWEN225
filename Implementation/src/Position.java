/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5071.d9da8f6cd modeling language!*/


// line 100 "model.ump"
// line 190 "model.ump"
public class Position {

    //------------------------
    // MEMBER VARIABLES
    //------------------------


    //Position Attributes
    private CharacterCard character;
    private Room inRoom;
    private String displayName = "_";

    private Move.Direction doorDirection;
    private boolean isDoor = false;
    private boolean passableTile;
    private boolean canMove;

    private int xLoc;
    private int yLoc;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    // Default Constructor
    public Position(int x, int y, boolean canMove) {
        this.xLoc = x;
        this.yLoc = y;
        this.canMove = canMove;
        if (!canMove) {
            this.displayName = "x";
        }
    }

    // Character Position Constructor
    public Position(int x, int y, boolean canMove, CharacterCard character) {
        this.xLoc = x;
        this.yLoc = y;
        this.canMove = canMove;
        this.character = character;
    }

    // Room Position Constructor
    public Position(int x, int y, boolean canMove, boolean passableTile, Move.Direction direction, Room inRoom) {
        this.xLoc = x;
        this.yLoc = y;
        this.canMove = canMove;
        this.inRoom = inRoom;
        this.passableTile = passableTile;
        this.doorDirection = direction;

        if (this.doorDirection != null) {
            this.isDoor = true;
        }
        // Check for door or outer wall
        if (this.passableTile == false) {
            this.displayName = inRoom.getRoomChar().toLowerCase();
        }
        if (this.isDoor) {
            switch (direction) {
                case UP:
                    this.displayName = "^";
                    break;
                case DOWN:
                    this.displayName = "v";
                    break;
                case RIGHT:
                    this.displayName = ">";
                    break;
                case LEFT:
                    this.displayName = "<";
                    break;
                default:
                    this.displayName = "Door has been assigned incorrect direction" + xLoc + " : " + yLoc;
                    break;
            }
        }
    }

    // Clone Constructor
    public Position clone() {
        Position clonePosition = new Position(this.xLoc, this.yLoc, this.canMove);
        clonePosition.character = this.character;
        clonePosition.inRoom = this.inRoom;
        clonePosition.displayName = this.displayName;
        clonePosition.doorDirection = this.doorDirection;
        clonePosition.isDoor = this.isDoor;
        clonePosition.passableTile = this.passableTile;
        clonePosition.canMove = this.canMove;
        clonePosition.xLoc = this.xLoc;
        clonePosition.yLoc = this.yLoc;
        return clonePosition;
    }

    // Basic Location Information
    public int getxLoc() {
        return xLoc;
    }

    public int getyLoc() {
        return yLoc;
    }

    public boolean canMove() {
        if (this.character == null) { // If no character occupies position
            return this.canMove;
        }
        return false;
    }

    public String toString() {
        if (this.character != null) {
            return this.character.getCharacterBoardChar();
        }
        return this.displayName;
    }

    // Character
    public CharacterCard getCharacter() {
        return this.character;
    }

    public void setCharacter(CharacterCard character) {
        this.character = character;
    }

    public void removeCharacter() {
        this.character = null;
    }

    // Room
    public Room getRoom() {
        return inRoom;
    }

    public boolean isDoor() {
        return isDoor;
    }

    public boolean isPassableTile() {
        return this.passableTile;
    }

    public Move.Direction getDoorDirection() {
        if (isDoor) {
            return doorDirection;
        }
        return null;
    }


}