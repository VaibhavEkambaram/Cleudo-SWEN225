package Model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5071.d9da8f6cd modeling language!*/


// line 17 "model.ump"
// line 122 "model.ump"
// line 146 "model.ump"
// line 204 "model.ump"

/**
 * Model.RoomCard Class - Implements Model.Card Interface
 */
public class RoomCard implements Card {

    //------------------------
    // MEMBER VARIABLES
    //------------------------
    //Model.RoomCard Attributes
    private final String roomName; // the name of this room
    private final Room room; // room object

    /**
     * Model.RoomCard Constructor
     *
     * @param roomString name
     * @param room       room
     */
    public RoomCard(String roomString, Room room) {
        this.roomName = roomString;
        this.room = room;
    }

    //------------------------
    // INTERFACE
    //------------------------

    /**
     * Retrieve Model.Room Name
     *
     * @return room string
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Retrieve Model.Room object
     *
     * @return room object
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Overridden toString method
     *
     * @return room card string
     */
    @Override
    public String toString() {
        return getRoomName();
    }
}