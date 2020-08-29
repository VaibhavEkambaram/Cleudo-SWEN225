package model;


/**
 * Model.RoomCard Class - Implements Model.Card Interface
 */
public class RoomCard implements Card {

    //------------------------
    // MEMBER VARIABLES
    //------------------------
    //RoomCard Attributes
    private final String roomName; // the name of this room
    private final Room room; // room object

    /**
     * RoomCard Constructor
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
     * Retrieve Room Name
     *
     * @return room string
     */
    public String getRoomName() {
        return roomName;
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