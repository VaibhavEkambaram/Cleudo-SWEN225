package Model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5071.d9da8f6cd modeling language!*/

// line 43 "model.ump"
// line 161 "model.ump"
// line 219 "model.ump"

/**
 * Model.Room Class
 */
public class Room {

    //Model.Room Attributes
    private final String roomName;
    private String roomChar; // room representation character for board


    /**
     * Model.Room Constructor
     *
     * @param roomName full room name
     */
    public Room(String roomName) {
        this.roomName = roomName;
        setRoomChar(this.roomName);
    }


    /**
     * Set Model.Room Character based on assigned room name
     *
     * @param roomName room name
     */
    private void setRoomChar(String roomName) {
        switch (roomName) {
            case "Conservatory":
                this.roomChar = "C";
                break;
            case "Ball Model.Room":
                this.roomChar = "B";
                break;
            case "Kitchen":
                this.roomChar = "K";
                break;
            case "Billiard Model.Room":
                this.roomChar = "I";
                break;
            case "Dining Model.Room":
                this.roomChar = "D";
                break;
            case "Library":
                this.roomChar = "L";
                break;
            case "Lounge":
                this.roomChar = "O";
                break;
            case "Hall":
                this.roomChar = "H";
                break;
            case "Study":
                this.roomChar = "Y";
                break;
            default:
                this.roomChar = "-";
                break;
        }
    }

    /**
     * Get Model.Room Character
     *
     * @return room character string
     */
    public String getRoomChar() {
        return roomChar;
    }


    /**
     * Model.Room toString Method
     *
     * @return room name as toString
     */
    public String toString() {
        return this.roomName;
    }
}