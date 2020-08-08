/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5071.d9da8f6cd modeling language!*/


// line 25 "model.ump"
// line 128 "model.ump"
// line 151 "model.ump"
// line 209 "model.ump"
public class CharacterCard implements Card {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //CharacterCard Attributes
    private String characterName;
    private final String characterBoardChar;

    /**
     * CharacterCard Constructor
     *
     * @param c name of the character
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
            case "Ms. Scarlett":
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




    public String getCharacterName() {
        return characterName;
    }

    public String getCharacterBoardChar() {
        return characterBoardChar;
    }

    public void delete() {
    }



    @Override
    public String toString() {
        return getCharacterName();
    }
}