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

    private boolean isRoom = false;
    private boolean passableTile;
    private boolean canMove;

    private int xLoc;
    private int yLoc;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Position(Room aInRoom, boolean aPassableTile, int x, int y) {
        inRoom = aInRoom;
        passableTile = aPassableTile;
        displayName = x + ", " + y;
        xLoc = x;
        yLoc = y;
    }

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
        this.isRoom = true;
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

    public void setCharacter(CharacterCard character) {
        this.character = character;
    }

    public void removeCharacter() {
        this.character = null;
    }


    //------------------------
    // INTERFACE
    //------------------------

    public boolean getCanMove() {
        if (this.character == null) { // If no character occupies position
            return this.canMove;
        }
        return false;
    }

    public boolean getIsRoom() {
        return isRoom;
    }

    public Room getInRoom() {
        return inRoom;
    }


    /* Code from template attribute_IsBoolean */
    public boolean isIsRoom() {
        return isRoom;
    }

    public boolean isIsDoor() {
        return isDoor;
    }

    public Move.Direction getDoorDirection() {
        if (isDoor) {
            return doorDirection;
        }
        return null;
    }

    public int getxLoc() {
        return xLoc;
    }

    public int getyLoc() {
        return yLoc;
    }


    public String toString() {
        if (this.character != null) {
            return this.character.getCharacterBoardChar();
        }
        return this.displayName;
    }
}