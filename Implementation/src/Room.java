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
    private String roomName;
    private String roomChar;

    private RoomCard roomCard;
    private WeaponCard weapon;
    private CharacterCard character;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Room(String roomName) {
        this.roomName = roomName;
        /*
        C - Conservatory
        B - Ball Room
        K - Kitchen
        I - Billiard Room
        D - Dining Room
        L - Library
        O - Lounge
        H - Hall
        Y - Study
         */

        switch (roomName) {
            case ("Conservatory"):
                roomChar = "C";
            case ("Ball Room"):
                roomChar = "B";
            case ("Kitchen"):
                roomChar = "K";
            case ("Billiard Room"):
                roomChar = "I";
            case ("Dining Room"):
                roomChar = "D";
            case ("Library"):
                roomChar = "L";
            case ("Lounge"):
                roomChar = "O";
            case ("Hall"):
                roomChar = "H";
            case ("Study"):
                roomChar = "Y";
            default:
                roomChar = "-";
        }

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

    public void delete() {
    }


    public String toString() {
        return this.roomName;
    }
}