/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5071.d9da8f6cd modeling language!*/


// line 43 "model.ump"
// line 161 "model.ump"
// line 219 "model.ump"
public class Room {

    //Room Attributes
    private final String roomName;
    private String roomChar;

    public Room(String roomName) {
        this.roomName = roomName;
        setRoomChar(this.roomName);
    }

    // Basic Information Getters

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

    public String getRoomChar() {
        return roomChar;
    }

    public String toString() {
        return this.roomName;
    }
}