package Model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5071.d9da8f6cd modeling language!*/

// line 11 "model.ump"
// line 117 "model.ump"
// line 141 "model.ump"
// line 199 "model.ump"

/**
 * Model.WeaponCard Class - Implements Model.Card Interface
 */
public class WeaponCard implements Card {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Model.WeaponCard Attributes
    private final String weaponString; // name of the weapon
    private final char weaponBoardChar;


    /**
     * Constructor Method for Model.WeaponCard
     *
     * @param weaponString String identifying the weapon
     */
    public WeaponCard(String weaponString) {
        this.weaponString = weaponString;

        switch (weaponString) {
            case "Candlestick":
                this.weaponBoardChar = '?';
                break;
            case "Dagger":
                this.weaponBoardChar = '!';
                break;
            case "Lead Pipe":
                this.weaponBoardChar = '$';
                break;
            case "Revolver":
                this.weaponBoardChar = '%';
                break;
            case "Rope":
                this.weaponBoardChar = '@';
                break;
            case "Spanner":
                this.weaponBoardChar = '&';
                break;
            default:
                this.weaponBoardChar = '~';
                break;

        }
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
     * Overridden toString method
     *
     * @return weapon card string
     */
    @Override
    public String toString() {
        return getWeaponName();
    }
}