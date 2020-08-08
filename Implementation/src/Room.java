/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5071.d9da8f6cd modeling language!*/


// line 43 "model.ump"
// line 161 "model.ump"
// line 219 "model.ump"
public class Room {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Room Attributes
    private final String roomName;
    private String roomChar;

    private RoomCard roomCard;
    private WeaponCard weapon;
    private CharacterCard character;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Room(String roomName) {
        this.roomName = roomName;
        setRoomChar(this.roomName);
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setRoomCard(RoomCard aRoomCard) {
        boolean wasSet = false;
        roomCard = aRoomCard;
        wasSet = true;
        return wasSet;
    }

    public boolean setWeapon(WeaponCard aWeapon) {
        boolean wasSet = false;
        weapon = aWeapon;
        wasSet = true;
        return wasSet;
    }

    public boolean setCharacter(CharacterCard aCharacter) {
        boolean wasSet = false;
        character = aCharacter;
        wasSet = true;
        return wasSet;
    }

    public RoomCard getRoomCard() {
        return roomCard;
    }

    public WeaponCard getWeapon() {
        return weapon;
    }

    public CharacterCard getCharacter() {
        return character;
    }

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