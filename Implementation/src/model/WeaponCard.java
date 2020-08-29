package model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Model.WeaponCard Class - Implements Model.Card Interface
 */
public class WeaponCard implements Card {

    //WeaponCard Attributes
    private final String weaponString; // name of the weapon
    Image weaponImage;


    /**
     * Constructor Method for Model.WeaponCard
     *
     * @param weaponString String identifying the weapon
     */
    public WeaponCard(String weaponString) {
        this.weaponString = weaponString;


        weaponImage = new ImageIcon(getClass().getResource("/resources/image_"+weaponString+".png")).getImage();
    }


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