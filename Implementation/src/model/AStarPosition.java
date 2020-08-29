package model;

public class AStarPosition implements Comparable<AStarPosition> {
    private Position currentPosition;
    private Position previousPosition;

    private double g;
    private double f;

    public AStarPosition(Position currentPosition, Position previousPosition,double g,double f){
        this.currentPosition = currentPosition;
        this.previousPosition = previousPosition;
        this.g = g;
        this.f = f;
    }

    public Position getCurrentPosition(){
        return this.currentPosition;
    }

    public Position getPreviousPosition(){
        return this.previousPosition;
    }

    public double getG(){
        return this.g;
    }

    public double getF(){
        return this.f;
    }


    @Override
    public int compareTo(AStarPosition o) {
        return Double.compare(this.f, o.f);
    }
}
