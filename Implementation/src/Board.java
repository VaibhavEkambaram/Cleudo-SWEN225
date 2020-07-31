/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5071.d9da8f6cd modeling language!*/


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// line 90 "model.ump"
// line 183 "model.ump"
public class Board {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Board Attributes
    private final List<Position> tiles;

    //Board Associations
    private final List<Scenario> scenarios;
    private final List<Position> positions;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Board() {
        tiles = new ArrayList<Position>();
        scenarios = new ArrayList<Scenario>();
        positions = new ArrayList<Position>();
    }

    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfScenarios() {
        return 0;
    }

    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfPositions() {
        return 0;
    }

    //------------------------
    // INTERFACE
    //------------------------
    /* Code from template attribute_SetMany */
    public boolean addTile(Position aTile) {
        boolean wasAdded = false;
        wasAdded = tiles.add(aTile);
        return wasAdded;
    }

    public boolean removeTile(Position aTile) {
        boolean wasRemoved = false;
        wasRemoved = tiles.remove(aTile);
        return wasRemoved;
    }

    /* Code from template attribute_GetMany */
    public Position getTile(int index) {
        Position aTile = tiles.get(index);
        return aTile;
    }

    public Position[] getTiles() {
        Position[] newTiles = tiles.toArray(new Position[tiles.size()]);
        return newTiles;
    }

    public int numberOfTiles() {
        int number = tiles.size();
        return number;
    }

    public boolean hasTiles() {
        boolean has = tiles.size() > 0;
        return has;
    }

    public int indexOfTile(Position aTile) {
        int index = tiles.indexOf(aTile);
        return index;
    }


    /* Code from template association_GetMany */
    public Position getPosition(int index) {
        Position aPosition = positions.get(index);
        return aPosition;
    }

    public List<Position> getPositions() {
        List<Position> newPositions = Collections.unmodifiableList(positions);
        return newPositions;
    }

    public int numberOfPositions() {
        int number = positions.size();
        return number;
    }

    public boolean hasPositions() {
        boolean has = positions.size() > 0;
        return has;
    }

    public int indexOfPosition(Position aPosition) {
        int index = positions.indexOf(aPosition);
        return index;
    }


    public String toString() {
        return super.toString() + "[" + "]";
    }
}