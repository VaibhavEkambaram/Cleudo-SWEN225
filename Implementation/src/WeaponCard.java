/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5071.d9da8f6cd modeling language!*/

// line 11 "model.ump"
// line 117 "model.ump"
// line 141 "model.ump"
// line 199 "model.ump"

/**
 * WeaponCard Class - Implements Card Interface
 */
public class WeaponCard implements Card {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //WeaponCard Attributes
    private final String weaponString; // name of the weapon
    private Room room; // room where the weapon is located

    /**
     * Constructor Method for WeaponCard
     *
     * @param weaponString String identifying the weapon
     */
    public WeaponCard(String weaponString) {
        this.weaponString = weaponString;
    }

    /**
     * Get Weapon String
     *
     * @return weapon name
     */
    public String getWeaponName() {
        return weaponString;
    }

    /**
     * Update Weapon Room Location
     *
     * @param r room to replace with
     */
    public void setRoom(Room r) {
        room = r;
    }

    /**
     * Get Weapon Room Location
     *
     * @return room
     */
    public Room getRoom() {
        return room;
    }


    /**
     * Overridden toString method
     *
     * @return weapon card string
     */
    @Override
    public String toString() {
        return getWeaponName();
    }
}