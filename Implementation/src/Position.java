/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5071.d9da8f6cd modeling language!*/


// line 100 "model.ump"
// line 190 "model.ump"
public class Position {

    //------------------------
    // MEMBER VARIABLES
    //------------------------


    //Position Attributes
    private Room inRoom;
    private boolean passableTile;
    private String displayName;
    private boolean isRoom;
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

    //------------------------
    // INTERFACE
    //------------------------

    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfBoards() {
        return 0;
    }

    public boolean setInRoom(Room aInRoom) {
        boolean wasSet = false;
        inRoom = aInRoom;
        wasSet = true;
        return wasSet;
    }

    public boolean setPassableTile(boolean aPassableTile) {
        boolean wasSet = false;
        passableTile = aPassableTile;
        wasSet = true;
        return wasSet;
    }

    public boolean setDisplayName(String aDisplayName) {
        boolean wasSet = false;
        displayName = aDisplayName;
        wasSet = true;
        return wasSet;
    }

    public boolean setIsRoom(boolean aIsRoom) {
        boolean wasSet = false;
        isRoom = aIsRoom;
        wasSet = true;
        return wasSet;
    }

    public Room getInRoom() {
        return inRoom;
    }

    /**
     * Not quite sure we will need this as the Room card should store this information for us, instead of individual tiles.
     * CharacterCard hasCharacter;
     * WeaponCard hasWeapon;
     */
    public boolean getPassableTile() {
        return passableTile;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean getIsRoom() {
        return isRoom;
    }

    /* Code from template attribute_IsBoolean */
    public boolean isIsRoom() {
        return isRoom;
    }

    /*
    /* Code from template association_GetMany
    public Board getBoard(int index) {
        Board aBoard = boards.get(index);
        return aBoard;
    }

    public List<Board> getBoards() {
        List<Board> newBoards = Collections.unmodifiableList(boards);
        return newBoards;
    }

    public int numberOfBoards() {
        int number = boards.size();
        return number;
    }

    public boolean hasBoards() {
        boolean has = boards.size() > 0;
        return has;
    }

    public int indexOfBoard(Board aBoard) {
        int index = boards.indexOf(aBoard);
        return index;
    }

    // Code from template association_AddManyToManyMethod
    public boolean addBoard(Board aBoard) {
        boolean wasAdded = false;
        if (boards.contains(aBoard)) {
            return false;
        }
        boards.add(aBoard);
        if (aBoard.indexOfPosition(this) != -1) {
            wasAdded = true;
        } else {
            wasAdded = aBoard.addPosition(this);
            if (!wasAdded) {
                boards.remove(aBoard);
            }
        }
        return wasAdded;
    }

    // Code from template association_RemoveMany
    public boolean removeBoard(Board aBoard) {
        boolean wasRemoved = false;
        if (!boards.contains(aBoard)) {
            return wasRemoved;
        }

        int oldIndex = boards.indexOf(aBoard);
        boards.remove(oldIndex);
        if (aBoard.indexOfPosition(this) == -1) {
            wasRemoved = true;
        } else {
            wasRemoved = aBoard.removePosition(this);
            if (!wasRemoved) {
                boards.add(oldIndex, aBoard);
            }
        }
        return wasRemoved;
    }

    // Code from template association_AddIndexControlFunctions
    public boolean addBoardAt(Board aBoard, int index) {
        boolean wasAdded = false;
        if (addBoard(aBoard)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfBoards()) {
                index = numberOfBoards() - 1;
            }
            boards.remove(aBoard);
            boards.add(index, aBoard);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveBoardAt(Board aBoard, int index) {
        boolean wasAdded = false;
        if (boards.contains(aBoard)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfBoards()) {
                index = numberOfBoards() - 1;
            }
            boards.remove(aBoard);
            boards.add(index, aBoard);
            wasAdded = true;
        } else {
            wasAdded = addBoardAt(aBoard, index);
        }
        return wasAdded;
    }

    public void delete() {
        ArrayList<Board> copyOfBoards = new ArrayList<Board>(boards);
        boards.clear();
        for (Board aBoard : copyOfBoards) {
            aBoard.removePosition(this);
        }
    }

    */


    public String toString() {
        return super.toString() + "[" +
                "passableTile" + ":" + getPassableTile() + "," +
                "displayName" + ":" + getDisplayName() + "," +
                "isRoom" + ":" + getIsRoom() + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "inRoom" + "=" + (getInRoom() != null ? !getInRoom().equals(this) ? getInRoom().toString().replaceAll("  ", "    ") : "this" : "null");
    }
}