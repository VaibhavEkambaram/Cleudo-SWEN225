package model;

import java.awt.*;

/**
 * CharacterCard - Implements Model.Card Interface
 */
public class CharacterCard implements Card{

    // CharacterCard Attributes
    private final String characterName;
    private final String characterBoardChar;
    private final Color characterBoardColor;
    private final String characterColorName;
    int characterPriority;

    /**
     * CharacterCard Constructor
     *
     * @param c name of the character
     * @author Vaibhav Ekambaram, Cameron Li
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
        characterPriority = Integer.parseInt(characterBoardChar);
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

    /**
     * get character board colour
     *
     * @return Color
     */
    public Color getCharacterBoardColor() {
        return characterBoardColor;
    }

    /**
     * Get character colour name
     *
     * @return colour string
     */
    public String getCharacterColorName() {
        return characterColorName;
    }

    public int getCharacterPriority(){
        return characterPriority;
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