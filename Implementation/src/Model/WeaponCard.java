package Model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5071.d9da8f6cd modeling language!*/

// line 11 "model.ump"
// line 117 "model.ump"
// line 141 "model.ump"
// line 199 "model.ump"

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;

/**
 * Model.WeaponCard Class - Implements Model.Card Interface
 */
public class WeaponCard implements Card {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Model.WeaponCard Attributes
    private final String weaponString; // name of the weapon
    BufferedImage image;


    /**
     * Constructor Method for Model.WeaponCard
     *
     * @param weaponString String identifying the weapon
     */
    public WeaponCard(String weaponString) {
        this.weaponString = weaponString;


        try {
            image = ImageIO.read(getClass().getResource("/resources/image_"+weaponString+".png"));
        } catch (IOException e) {
            System.out.println("failed");
        }
    }


    public BufferedImage getWeaponImage(){
        return this.image;
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