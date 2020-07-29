/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5071.d9da8f6cd modeling language!*/


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// line 17 "model.ump"
// line 122 "model.ump"
// line 146 "model.ump"
// line 204 "model.ump"
public class RoomCard implements Card {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //RoomCard Associations
    private final List<Scenario> scenarios;
    //RoomCard Attributes
    private String roomName;
    private Room room;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public RoomCard(String aRoomName, Room aRoom) {
        roomName = aRoomName;
        room = aRoom;
        scenarios = new ArrayList<Scenario>();
    }

    //------------------------
    // INTERFACE
    //------------------------

    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfScenarios() {
        return 0;
    }

    public boolean setRoomName(String aRoomName) {
        boolean wasSet = false;
        roomName = aRoomName;
        wasSet = true;
        return wasSet;
    }

    public boolean setRoom(Room aRoom) {
        boolean wasSet = false;
        room = aRoom;
        wasSet = true;
        return wasSet;
    }

    public String getRoomName() {
        return roomName;
    }

    public Room getRoom() {
        return room;
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

    /* Code from template association_AddManyToManyMethod */
    public boolean addScenario(Scenario aScenario) {
        boolean wasAdded = false;
        if (scenarios.contains(aScenario)) {
            return false;
        }
        scenarios.add(aScenario);
        if (aScenario.indexOfRoomCard(this) != -1) {
            wasAdded = true;
        } else {
            wasAdded = aScenario.addRoomCard(this);
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
        if (aScenario.indexOfRoomCard(this) == -1) {
            wasRemoved = true;
        } else {
            wasRemoved = aScenario.removeRoomCard(this);
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

    public void delete() {
        ArrayList<Scenario> copyOfScenarios = new ArrayList<Scenario>(scenarios);
        scenarios.clear();
        for (Scenario aScenario : copyOfScenarios) {
            aScenario.removeRoomCard(this);
        }
    }


    /**
     * Unable to update umple code due to error at null
     * Unable to update umple code due to error at null
     * Unable to update umple code due to error at null
     * Unable to update umple code due to error at null
     */
    @Override
    public String toString() {
        return "";
    }

}