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
    private String weaponString; // name of the weapon
    private Room room; // room where the weapon is located


    //------------------------
    // CONSTRUCTOR
    //------------------------

    /**
     * Constructor Method for WeaponCard
     *
     * @param weaponString String identifying the weapon
     */
    public WeaponCard(String weaponString) {
        this.weaponString = weaponString;
    }

    /**
     * Update Weapon String
     *
     * @param weaponString String identifying the weapon
     * @return boolean checking action was executed
     */
    public boolean setWeapon(String weaponString) {
        boolean wasSet;
        this.weaponString = weaponString;
        wasSet = true;
        return wasSet;
    }

    /**
     * Get Weapon String
     *
     * @return weapon name
     */
    public String getWeapon() {
        return weaponString;
    }

    /**
     * Update Room Location
     *
     * @param r room to replace with
     */
    public void setRoom(Room r) {
        room = r;
    }

    /**
     * Get Room Location
     *
     * @return room
     */
    public Room getRoom() {
        return room;
    }


    public void delete() {
    }

    /**
     * Overridden toString method
     * @return weapon card string
     */
    @Override
    public String toString() {
        return getWeapon();
    }
}