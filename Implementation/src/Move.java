public class Move {
    private final Direction direction; // Direction of travel
    private final int spaces; // Number of spaces to travel

    public Move(Direction direction, int spaces) {
        this.direction = direction;
        this.spaces = spaces;
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    // Move Getters

    /**
     * Return the number of times to travel on the axis given by direction
     *
     * @return
     */
    public int getSpaces() {
        return spaces;
    }

    /**
     * Returns the number of spaces to travel on the x-axis
     *
     * @return
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
     * @return
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
