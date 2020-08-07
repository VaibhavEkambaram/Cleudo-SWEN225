public class Move {
    private Direction direction;
    private int spaces;

    public Move(Direction direction, int spaces) {
        this.direction = direction;
        this.spaces = spaces;
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public int getSpaces() {
        return spaces;
    }

    public Direction getDirection() {
        return direction;
    }

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
