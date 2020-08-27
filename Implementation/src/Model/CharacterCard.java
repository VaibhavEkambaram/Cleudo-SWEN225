package Model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5071.d9da8f6cd modeling language!*/

// line 25 "model.ump"
// line 128 "model.ump"
// line 151 "model.ump"
// line 209 "model.ump"

import java.awt.*;

/**
 * Model.CharacterCard - Implements Model.Card Interface
 */
public class CharacterCard implements Card {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Model.CharacterCard Attributes
    private final String characterName;
    private final String characterBoardChar;
    private final Color characterBoardColor;
    private final String characterColorName;

    /**
     * Model.CharacterCard Constructor
     *
     * @param c name of the character
     * @author Vaibhav Ekambaram
     */
    public CharacterCard(String c) {
        this.characterName = c;
        switch (characterName) {
            case "Mrs. White":
                characterBoardChar = "3";
                characterBoardColor = Color.WHITE;
                characterColorName = "WHITE";
                break;
            case "Mr. Green":
                characterBoardChar = "4";
                characterBoardColor = Color.GREEN;
                characterColorName = "GREEN";
                break;
            case "Mrs. Peacock":
                characterBoardChar = "5";
                characterBoardColor = Color.BLUE;
                characterColorName = "BLUE";
                break;
            case "Prof. Plum":
                characterBoardChar = "6";
                characterBoardColor = new Color(128, 0, 128);
                characterColorName = "PURPlE";
                break;
            case "Miss Scarlett":
                characterBoardChar = "1";
                characterBoardColor = Color.RED;
                characterColorName = "RED";
                break;
            case "Col. Mustard":
                characterBoardChar = "2";
                characterBoardColor = Color.YELLOW;
                characterColorName = "YELLOW";
                break;
            default:
                characterBoardChar = "-";
                characterBoardColor = Color.BLACK; // There should be no character with this color
                characterColorName = "BLACK";
                break;
        }
    }


    /**
     * Get the name string of a character card (eg. Ms. Scarlett)
     *
     * @return character name string
     */
    public String getCharacterName() {
        return characterName;
    }

    /**
     * Get the board character representation of a character
     *
     * @return board character string
     */
    public String getCharacterBoardChar() {
        return characterBoardChar;
    }

    public Color getCharacterBoardColor() {
        return characterBoardColor;
    }

    public String getCharacterColorName() {
        return characterColorName;
    }

    /**
     * toString Method - Print of the character name
     *
     * @return character name string
     */
    @Override
    public String toString() {
        return getCharacterName();
    }
}