/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5071.d9da8f6cd modeling language!*/


import java.util.ArrayList;
import java.util.List;

// line 73 "model.ump"
// line 178 "model.ump"
public class Player {

    private final List<Card> hand;
    // Player Gameplay Information
    private CharacterCard character;
    private Position currentPosition;
    // Player State
    private boolean canAccuse = true;
    private boolean canRefute = true;

    // Constructor and Initialise Methods
    public Player(CharacterCard aCharacter) {
        character = aCharacter;
        hand = new ArrayList<>();
    }

    public boolean addHand(Card aHand) {
        boolean wasAdded = false;
        wasAdded = hand.add(aHand);
        return wasAdded;
    }

    // Player Setters

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

    // Player Information Getters

    public CharacterCard getCharacter() {
        return character;
    }

    public List<Card> getHand() {
        return hand;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public String toString() {
        return "Player " + this.character.getCharacterBoardChar() + this.character;
    }

    // Player State Getters

    public boolean getCanAccuse() {
        return canAccuse;
    }

    public boolean getCanRefute() {
        return canRefute;
    }


}