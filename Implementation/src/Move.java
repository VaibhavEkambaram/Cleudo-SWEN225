public class Move {
    public Direction direction;
    public int spaces;

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
}
