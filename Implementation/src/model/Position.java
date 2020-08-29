package model;


import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Model.Position Class
 * Used as a "Tile" on the board
 */
public class Position {

    // Basic Model.Position Attributes
    private CharacterCard character;
    private WeaponCard weapon;

    private String displayName = "_";
    private boolean canMove;
    private int xLoc;
    private int yLoc;

    // Model.Room Attributes
    private Room inRoom;
    private Move.Direction doorDirection;
    private boolean isDoor = false;
    private boolean passableTile;

    private final Color COLOR_1 = new Color(223, 213, 205);
    private final Color COLOR_2 = new Color(221, 82, 53);
    private final Color COLOR_3 = new Color(215, 202, 193);
    private final Color COLOR_4 = new Color(255, 155, 0);

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


    public String getDisplayName() {
        return this.displayName;
    }

    public WeaponCard getWeapon() {
        return weapon;
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
     * Room Position Constructor
     *
     * @param x            x
     * @param y            y
     * @param canMove      ability for player to move to this position
     * @param passableTile is tile passable
     * @param direction    direction
     * @param inRoom       is the position located in a room
     * @param weapon       weapon piece
     */
    public Position(int x, int y, boolean canMove, boolean passableTile, Move.Direction direction, Room inRoom, WeaponCard weapon) {
        this.xLoc = x;
        this.yLoc = y;
        this.canMove = canMove;
        this.inRoom = inRoom;
        this.passableTile = passableTile;
        this.doorDirection = direction;
        this.weapon = weapon;

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
        clonePosition.weapon = this.weapon;
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
        if (this.character == null && this.weapon == null) { // If no character occupies position
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

    /**
     * Draw board positions
     *
     * @param g            graphics
     * @param xValue       x position
     * @param yValue       y position
     * @param RECT_SIZE    rectangle size
     * @param displayPanel JPanel
     * @author Vaibhav Ekambaram
     */
    public void draw(Graphics2D g, int xValue, int yValue, int RECT_SIZE, JPanel displayPanel) {
        if (g != null) {

            if (displayName.equals("x")) {
                g.setColor(new Color(1, 50, 32));
                g.fillRect(xValue, yValue, RECT_SIZE, RECT_SIZE);
            } else if (displayName.equals("_") && inRoom == null) {
                g.setColor(new Color(219, 211, 150));
                g.fillRect(xValue, yValue, RECT_SIZE, RECT_SIZE);
                g.setColor(Color.BLACK);
                g.setStroke(new BasicStroke(1));
               g.drawRect(xValue, yValue, RECT_SIZE, RECT_SIZE);

            } else {
                switch (inRoom.getRoomChar()) {
                    case "K":
                        g.setColor(xLoc % 2 == 0 ? yLoc % 2 == 0 ? Color.BLACK : COLOR_1 : yLoc % 2 == 0 ? COLOR_1 : Color.BLACK);
                        g.fillRect(xValue, yValue, RECT_SIZE, RECT_SIZE);

                        break;
                    case "H":
                        g.setColor(xLoc % 2 == 0 ? yLoc % 2 == 0 ? COLOR_2 : COLOR_3 : yLoc % 2 == 0 ? COLOR_3 : COLOR_2);
                        g.fillRect(xValue, yValue, RECT_SIZE, RECT_SIZE);
                        break;
                    case "C":
                        g.setColor(xLoc % 2 == 0 ? yLoc % 2 == 0 ? COLOR_4 : COLOR_1 : yLoc % 2 == 0 ? COLOR_1 : COLOR_4);
                        g.fillRect(xValue, yValue, RECT_SIZE, RECT_SIZE);
                        break;
                    case "B":
                        g.setColor(xLoc % 2 == 0 ? COLOR_3 : COLOR_1);
                        g.fillRect(xValue, yValue, RECT_SIZE, RECT_SIZE);
                        break;
                    case "D":
                        g.setColor(new Color(200, 121, 80));
                        g.fillRect(xValue, yValue, RECT_SIZE, RECT_SIZE);
                        break;
                    case "I":
                        g.setColor(new Color(220, 170, 99));
                        g.fillRect(xValue, yValue, RECT_SIZE, RECT_SIZE);
                        break;
                    case "Y":
                        g.setColor(new Color(190, 60, 60));
                        g.fillRect(xValue, yValue, RECT_SIZE, RECT_SIZE);
                        break;
                    case "L":
                        g.setColor(new Color(157, 127, 97));
                        g.fillRect(xValue, yValue, RECT_SIZE, RECT_SIZE);
                        break;
                    case "O":
                        g.setColor(new Color(104, 140, 200));
                        g.fillRect(xValue, yValue, RECT_SIZE, RECT_SIZE);
                        break;
                    default:
                        g.setColor(Color.WHITE);
                        g.fillRect(xValue, yValue, RECT_SIZE, RECT_SIZE);
                        break;
                }
            }

            if (character != null) {
                g.setColor(character.getCharacterBoardColor());
                g.fillOval(xValue, yValue, RECT_SIZE, RECT_SIZE);
            }

            if (weapon != null) {
                g.drawImage(weapon.getWeaponImage(), xValue, yValue, RECT_SIZE, RECT_SIZE, displayPanel);
            }
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

    public void removeWeapon() {
        this.weapon = null;
    }

    public void setWeapon(WeaponCard weapon) {
        this.weapon = weapon;
    }

    /**
     * Check if two Positions are aligned via X-axis or Y-axis
     *
     * @param b position
     * @return
     * @author Cameron Li
     */
    public boolean checkAligned(Position b) {
        return (b.getLocationX() == this.getLocationX() || b.getLocationY() == this.getLocationY());
    }

}