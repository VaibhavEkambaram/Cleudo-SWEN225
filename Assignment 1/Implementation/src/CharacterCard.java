/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5071.d9da8f6cd modeling language!*/

// line 25 "model.ump"
// line 128 "model.ump"
// line 151 "model.ump"
// line 209 "model.ump"

/**
 * CharacterCard - Implements Card Interface
 */
public class CharacterCard implements Card {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //CharacterCard Attributes
    private final String characterName;
    private final String characterBoardChar;

    /**
     * CharacterCard Constructor
     *
     * @param c name of the character
     * @author Vaibhav Ekambaram
     */
    public CharacterCard(String c) {
        this.characterName = c;

        switch (characterName) {
            case "Mrs. White":
                characterBoardChar = "3";
                break;
            case "Mr. Green":
                characterBoardChar = "4";
                break;
            case "Mrs. Peacock":
                characterBoardChar = "5";
                break;
            case "Prof. Plum":
                characterBoardChar = "6";
                break;
            case "Miss Scarlett":
                characterBoardChar = "1";
                break;
            case "Col. Mustard":
                characterBoardChar = "2";
                break;
            default:
                characterBoardChar = "-";
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