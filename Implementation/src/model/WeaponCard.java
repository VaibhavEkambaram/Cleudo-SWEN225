package model;

import javax.swing.*;
import java.awt.*;


/**
 * WeaponCard Class - Implements Card Interface
 */
public class WeaponCard implements Card {

    //WeaponCard Attributes
    private final String weaponString; // weapon name
    Image weaponImage; // weapon board image storage


    /**
     * Constructor Method for WeaponCard
     *
     * @param weaponString String identifying the weapon
     */
    public WeaponCard(String weaponString) {
        this.weaponString = weaponString;
        this.weaponImage = new ImageIcon(getClass().getResource("/resources/image_" + weaponString + ".png")).getImage();
    }


    /**
     * Get Weapon image for drawing
     *
     * @return Image
     */
    public Image getWeaponImage() {
        return this.weaponImage;
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