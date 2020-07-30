/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5071.d9da8f6cd modeling language!*/


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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


    //------------------------
    // CONSTRUCTOR
    //------------------------

    public CharacterCard(String aCharacterName) {
        characterName = aCharacterName;
    }

    //------------------------
    // INTERFACE
    //------------------------

    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfScenarios() {
        return 0;
    }

    public boolean setCharacterName(String aCharacterName) {
        boolean wasSet = false;
        characterName = aCharacterName;
        wasSet = true;
        return wasSet;
    }

    public String getCharacterName() {
        return characterName;
    }


    public void delete() {

    }


    @Override
    public String toString() {
        return "";
    }

}