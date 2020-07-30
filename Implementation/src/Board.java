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
    public Scenario getScenario(int index) {
        Scenario aScenario = scenarios.get(index);
        return aScenario;
    }

    public List<Scenario> getScenarios() {
        List<Scenario> newScenarios = Collections.unmodifiableList(scenarios);
        return newScenarios;
    }

    public int numberOfScenarios() {
        int number = scenarios.size();
        return number;
    }

    public boolean hasScenarios() {
        boolean has = scenarios.size() > 0;
        return has;
    }

    public int indexOfScenario(Scenario aScenario) {
        int index = scenarios.indexOf(aScenario);
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

    /* Code from template association_AddManyToManyMethod */
    public boolean addScenario(Scenario aScenario) {
        boolean wasAdded = false;
        if (scenarios.contains(aScenario)) {
            return false;
        }
        scenarios.add(aScenario);
        if (aScenario.indexOfBoard(this) != -1) {
            wasAdded = true;
        } else {
            wasAdded = aScenario.addBoard(this);
            if (!wasAdded) {
                scenarios.remove(aScenario);
            }
        }
        return wasAdded;
    }

    /* Code from template association_RemoveMany */
    public boolean removeScenario(Scenario aScenario) {
        boolean wasRemoved = false;
        if (!scenarios.contains(aScenario)) {
            return wasRemoved;
        }

        int oldIndex = scenarios.indexOf(aScenario);
        scenarios.remove(oldIndex);
        if (aScenario.indexOfBoard(this) == -1) {
            wasRemoved = true;
        } else {
            wasRemoved = aScenario.removeBoard(this);
            if (!wasRemoved) {
                scenarios.add(oldIndex, aScenario);
            }
        }
        return wasRemoved;
    }

    /* Code from template association_AddIndexControlFunctions */
    public boolean addScenarioAt(Scenario aScenario, int index) {
        boolean wasAdded = false;
        if (addScenario(aScenario)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfScenarios()) {
                index = numberOfScenarios() - 1;
            }
            scenarios.remove(aScenario);
            scenarios.add(index, aScenario);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveScenarioAt(Scenario aScenario, int index) {
        boolean wasAdded = false;
        if (scenarios.contains(aScenario)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfScenarios()) {
                index = numberOfScenarios() - 1;
            }
            scenarios.remove(aScenario);
            scenarios.add(index, aScenario);
            wasAdded = true;
        } else {
            wasAdded = addScenarioAt(aScenario, index);
        }
        return wasAdded;
    }

    /*
    // Code from template association_AddManyToManyMethod
    public boolean addPosition(Position aPosition) {
        boolean wasAdded = false;
        if (positions.contains(aPosition)) {
            return false;
        }
        positions.add(aPosition);
        if (aPosition.indexOfBoard(this) != -1) {
            wasAdded = true;
        } else {
            wasAdded = aPosition.addBoard(this);
            if (!wasAdded) {
                positions.remove(aPosition);
            }
        }
        return wasAdded;
    }

    // Code from template association_RemoveMany
    public boolean removePosition(Position aPosition) {
        boolean wasRemoved = false;
        if (!positions.contains(aPosition)) {
            return wasRemoved;
        }

        int oldIndex = positions.indexOf(aPosition);
        positions.remove(oldIndex);
        if (aPosition.indexOfBoard(this) == -1) {
            wasRemoved = true;
        } else {
            wasRemoved = aPosition.removeBoard(this);
            if (!wasRemoved) {
                positions.add(oldIndex, aPosition);
            }
        }
        return wasRemoved;
    }

    // Code from template association_AddIndexControlFunctions
    public boolean addPositionAt(Position aPosition, int index) {
        boolean wasAdded = false;
        if (addPosition(aPosition)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfPositions()) {
                index = numberOfPositions() - 1;
            }
            positions.remove(aPosition);
            positions.add(index, aPosition);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMovePositionAt(Position aPosition, int index) {
        boolean wasAdded = false;
        if (positions.contains(aPosition)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfPositions()) {
                index = numberOfPositions() - 1;
            }
            positions.remove(aPosition);
            positions.add(index, aPosition);
            wasAdded = true;
        } else {
            wasAdded = addPositionAt(aPosition, index);
        }
        return wasAdded;
    }

    public void delete() {
        ArrayList<Scenario> copyOfScenarios = new ArrayList<Scenario>(scenarios);
        scenarios.clear();
        for (Scenario aScenario : copyOfScenarios) {
            aScenario.removeBoard(this);
        }
        ArrayList<Position> copyOfPositions = new ArrayList<Position>(positions);
        positions.clear();
        for (Position aPosition : copyOfPositions) {
            aPosition.removeBoard(this);
        }
    }

    // line 97 "model.ump" // Position was originally named "Tile" in this method
    public boolean movePlayer(Player arg0, Position arg1, int arg2) {
        return false;
    }


     */

    public String toString() {
        return super.toString() + "[" + "]";
    }
}