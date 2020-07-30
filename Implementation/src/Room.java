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
    private RoomCard roomCard;
    private WeaponCard weapon;
    private CharacterCard character;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Room(String roomName) {
        this.roomName = roomName;
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
        return super.toString() + "[" + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "roomCard" + "=" + (getRoomCard() != null ? !getRoomCard().equals(this) ? getRoomCard().toString().replaceAll("  ", "    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "weapon" + "=" + (getWeapon() != null ? !getWeapon().equals(this) ? getWeapon().toString().replaceAll("  ", "    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "character" + "=" + (getCharacter() != null ? !getCharacter().equals(this) ? getCharacter().toString().replaceAll("  ", "    ") : "this" : "null");
    }
}