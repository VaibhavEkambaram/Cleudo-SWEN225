package model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Model.WeaponCard Class - Implements Model.Card Interface
 */
public class WeaponCard implements Card {

    //WeaponCard Attributes
    private final String weaponString; // name of the weapon
    BufferedImage weaponImage;


    /**
     * Constructor Method for Model.WeaponCard
     *
     * @param weaponString String identifying the weapon
     */
    public WeaponCard(String weaponString) {
        this.weaponString = weaponString;
        
        try {
            weaponImage = ImageIO.read(getClass().getResource("/resources/image_" + weaponString + ".png"));
        } catch (IOException e) {
            System.out.println("Failed to load image!");
        }
    }

    public BufferedImage getWeaponImage() {
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