package Model;

/**
 * Model.Move Class - Used in determining player movement around a board
 */
public class Move {
    private final Direction direction; // Direction of travel
    private final int spaces; // Number of spaces to travel

    /**
     * Model.Move Constructor
     *
     * @param direction direction of movement
     * @param spaces    number of spaces to move
     */
    public Move(Direction direction, int spaces) {
        this.direction = direction;
        this.spaces = spaces;
    }

    /**
     * Direction Enumerator
     * Model.Player is able to move:
     * - Up
     * - Down
     * - Left
     * - Right
     */
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    // Model.Move Getters

    /**
     * Return the number of times to travel on the axis given by direction
     *
     * @return number of spaces
     */
    public int getSpaces() {
        return spaces;
    }

    /**
     * Returns the number of spaces to travel on the x-axis
     *
     * @return number of spaces to travel in the x direction
     */
    public int xChange() {
        switch (direction) {
            case LEFT:
                return -1;
            case RIGHT:
                return 1;
            default:
                return 0;
        }
    }

    /**
     * Returns the number of spaces to travel on the y-axis
     *
     * @return number of spaces to travel in the y direction
     */
    public int yChange() {
        switch (direction) {
            case UP:
                return -1;
            case DOWN:
                return 1;
            default:
                return 0;
        }
    }
}