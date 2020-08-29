package model;

/**
 * Model.Room Class
 */
public class Room {

    // Room Attributes
    private final String roomName;
    private String roomChar; // room representation character for board


    /**
     * Room Constructor
     *
     * @param roomName full room name
     */
    public Room(String roomName) {
        this.roomName = roomName;
        setRoomChar(this.roomName);
    }


    /**
     * Set Room Character based on assigned room name
     *
     * @param roomName room name
     */
    private void setRoomChar(String roomName) {
        switch (roomName) {
            case "Conservatory":
                this.roomChar = "C";
                break;
            case "Ball Room":
                this.roomChar = "B";
                break;
            case "Kitchen":
                this.roomChar = "K";
                break;
            case "Billiard Room":
                this.roomChar = "I";
                break;
            case "Dining Room":
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
     * Get Room Character
     *
     * @return room character string
     */
    public String getRoomChar() {
        return roomChar;
    }


    /**
     * Room toString Method
     *
     * @return room name as toString
     */
    public String toString() {
        return this.roomName;
    }
}