package Model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5071.d9da8f6cd modeling language!*/

// line 100 "model.ump"
// line 190 "model.ump"

import java.awt.*;
import java.util.Objects;

/**
 * Model.Position Class
 * Used as a "Tile" on the board
 */
public class Position {

    //------------------------
    // MEMBER VARIABLES
    //------------------------


    // Basic Model.Position Attributes
    private CharacterCard character;
    private String displayName = "_";
    private boolean canMove;
    private int xLoc;
    private int yLoc;

    // Model.Room Attributes
    private Room inRoom;
    private Move.Direction doorDirection;
    private boolean isDoor = false;
    private boolean passableTile;


    /**
     * Default Constructor
     *
     * @param x       x
     * @param y       y
     * @param canMove ability for player to move to this position
     */
    public Position(int x, int y, boolean canMove) {
        this.xLoc = x;
        this.yLoc = y;
        this.canMove = canMove;
        if (!canMove) {
            this.displayName = "x";
        }
    }


    public String getDisplayName(){
        return this.displayName;
    }


    /**
     * Character Model.Position Constructor
     *
     * @param x         x
     * @param y         y
     * @param canMove   ability for player to move to this position
     * @param character character
     */
    public Position(int x, int y, boolean canMove, CharacterCard character) {
        this.xLoc = x;
        this.yLoc = y;
        this.canMove = canMove;
        this.character = character;
    }


    /**
     * Model.Room Model.Position Constructor
     *
     * @param x            x
     * @param y            y
     * @param canMove      ability for player to move to this position
     * @param passableTile is tile passable
     * @param direction    direction
     * @param inRoom       is the position located in a room
     */
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
        if (!this.passableTile) {
            this.displayName = inRoom.getRoomChar().toLowerCase();
        }
        if (this.isDoor) {
            switch (Objects.requireNonNull(direction)) {
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

    /**
     * Clone Model.Position Constructor
     *
     * @return cloned position
     */
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


    /**
     * x co-ordinate
     *
     * @return int
     */
    public int getLocationX() {
        return xLoc;
    }

    /**
     * y co-ordinate
     *
     * @return int
     */
    public int getLocationY() {
        return yLoc;
    }


    /**
     * Can this tile be moved
     *
     * @return boolean
     */
    public boolean canMove() {
        if (this.character == null) { // If no character occupies position
            return this.canMove;
        }
        return false;
    }

    /**
     * Model.Position toString Method, return either character or displayName
     *
     * @return string
     */
    public String toString() {
        if (this.character != null) {
            return this.character.getCharacterBoardChar();
        }
        return this.displayName;
    }


    public void draw(Graphics g){



        if (displayName.equals("x")) {
            g.setColor(new Color(1, 50, 32));
        } else if(displayName.equals("_") && inRoom==null) {
            g.setColor(new Color(250,218,94));
        } else if(inRoom.getRoomChar().equals("K")) {
            if ((xLoc % 2 == 0)) {
                if ((yLoc % 2 == 0)) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(new Color(223,213,205));
                }
            } else {
                if ((yLoc % 2 == 0)) {
                    g.setColor(new Color(223,213,205));
                } else {
                    g.setColor(Color.BLACK);
                }
            }
        } else if(inRoom.getRoomChar().equals("H")) {
            if ((xLoc % 2 == 0)) {
                if ((yLoc % 2 == 0)) {
                    g.setColor(new Color(221,82,53));
                } else {
                    g.setColor(new Color(215,202,193));
                }
            } else {
                if ((yLoc % 2 == 0)) {
                    g.setColor(new Color(215,202,193));
                } else {
                    g.setColor(new Color(221,82,53));
                }
            }
        } else if(inRoom.getRoomChar().equals("C")) {
            if ((xLoc % 2 == 0)) {
                if ((yLoc % 2 == 0)) {
                    g.setColor(new Color(255,155,0));
                } else {
                    g.setColor(new Color(223,213,205));
                }
            } else {
                if ((yLoc % 2 == 0)) {
                    g.setColor(new Color(223,213,205));
                } else {
                    g.setColor(new Color(255,155,0));
                }
            }
        } else if(inRoom.getRoomChar().equals("D")){
            g.setColor(new Color(200, 121, 80));
        } else if(inRoom.getRoomChar().equals("I")){
            g.setColor(new Color(220, 170, 99));
        } else if(inRoom.getRoomChar().equals("Y")) {
            g.setColor(new Color(190, 60, 60));
        } else if(inRoom.getRoomChar().equals("L")){
            g.setColor(new Color(157, 127, 97));
        } else if(inRoom.getRoomChar().equals("O")) {
            g.setColor(new Color(104, 140, 200));
        } else if(inRoom.getRoomChar().equals("B")) {
            if ((xLoc % 2 == 0)) {
                g.setColor(new Color(215,202,193));
            } else {
                g.setColor(new Color(223,213,205));
            }
        } else{
            g.setColor(Color.WHITE);
        }
    }


    /**
     * Get Character
     *
     * @return Model.CharacterCard
     */
    public CharacterCard getCharacter() {
        return this.character;
    }

    /**
     * Set Character
     *
     * @param character Model.CharacterCard
     */
    public void setCharacter(CharacterCard character) {
        this.character = character;
    }

    /**
     * Remove Character
     */
    public void removeCharacter() {
        this.character = null;
    }


    /**
     * Get Model.Room associated with this position
     *
     * @return Model.Room
     */
    public Room getRoom() {
        return inRoom;
    }

    /**
     * Determine whether this position is a door
     *
     * @return boolean
     */
    public boolean isDoor() {
        return isDoor;
    }


    /**
     * Determine whether this is an inner or outer room
     *
     * @return boolean
     */
    public boolean isPassableTile() {
        return this.passableTile;
    }

    /**
     * Get direction orientation of a particular door
     *
     * @return Model.Move direction
     */
    public Move.Direction getDoorDirection() {
        if (isDoor) {
            return doorDirection;
        }
        return null;
    }
}