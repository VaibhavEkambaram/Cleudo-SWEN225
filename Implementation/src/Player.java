/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5071.d9da8f6cd modeling language!*/


import java.util.ArrayList;
import java.util.List;

// line 73 "model.ump"
// line 178 "model.ump"
public class Player {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    private final List<Card> hand;
    //Player Attributes
    private CharacterCard character;
    private boolean canAccuse = true;
    private boolean canRefute = true;
    private Position currentPosition;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Player(CharacterCard aCharacter) {
        character = aCharacter;
        hand = new ArrayList<>();
    }

    //------------------------
    // INTERFACE
    //------------------------

    public boolean setCharacter(CharacterCard aCharacter) {
        boolean wasSet = false;
        character = aCharacter;
        wasSet = true;
        return wasSet;
    }

    /* Code from template attribute_SetMany */
    public boolean addHand(Card aHand) {
        boolean wasAdded = false;
        wasAdded = hand.add(aHand);
        return wasAdded;
    }

    public boolean setCanAccuse(boolean aCanAccuse) {
        boolean wasSet = false;
        canAccuse = aCanAccuse;
        wasSet = true;
        return wasSet;
    }

    public boolean setCanRefute(boolean aCanRefute) {
        boolean wasSet = false;
        canRefute = aCanRefute;
        wasSet = true;
        return wasSet;
    }

    public boolean setCurrentPosition(Position aCurrentPosition) {
        currentPosition = aCurrentPosition;
        return true;
    }

    public CharacterCard getCharacter() {
        return character;
    }

    public List<Card> getHand() { return hand; }

    public boolean getCanAccuse() {
        return canAccuse;
    }

    public boolean getCanRefute() {
        return canRefute;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public String toString() {
        return super.toString() + "[" +
                "canAccuse" + ":" + getCanAccuse() + "," +
                "canRefute" + ":" + getCanRefute() + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "character" + "=" + (getCharacter() != null ? !getCharacter().equals(this) ? getCharacter().toString().replaceAll(" {2}", "    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "currentPosition" + "=" + (getCurrentPosition() != null ? !getCurrentPosition().equals(this) ? getCurrentPosition().toString().replaceAll(" {2}", "    ") : "this" : "null");
    }
}