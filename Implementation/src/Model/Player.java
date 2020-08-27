package Model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5071.d9da8f6cd modeling language!*/

import java.util.ArrayList;
import java.util.List;

// line 73 "model.ump"
// line 178 "model.ump"

/**
 * Model.Player Class
 */
public class Player {

    private final List<Card> hand;
    // Model.Player Gameplay Information
    private final CharacterCard character;
    private Position currentPosition;
    // Model.Player State
    private boolean canAccuse = true;

    private String playerVanityName;


    /**
     * Model.Player Constructor and Initialise Methods
     *
     * @param aCharacter character associated with player
     */
    public Player(CharacterCard aCharacter,String playerVanityName) {
        character = aCharacter;
        this.playerVanityName = playerVanityName;
        hand = new ArrayList<>();
    }


    /**
     * Add Model.Card to player hand
     *
     * @param card card to add
     */
    public void addHand(Card card) {
        hand.add(card);
    }


    /**
     * Set ability to make accusations
     *
     * @param aCanAccuse accusation validator boolean
     */
    public void setCanAccuse(boolean aCanAccuse) {
        canAccuse = aCanAccuse;
    }


    /**
     * Set current Model.Position of player
     *
     * @param aCurrentPosition position on board
     */
    public void setCurrentPosition(Position aCurrentPosition) {
        currentPosition = aCurrentPosition;
    }

    // Model.Player Information Getters


    /**
     * Get Character associated with player
     *
     * @return character
     */
    public CharacterCard getCharacter() {
        return character;
    }

    /**
     * Get an ArrayList of the player hand
     *
     * @return hand arrayList
     */
    public List<Card> getHand() {
        return hand;
    }

    /**
     * Get current position of the player
     *
     * @return current position tile
     */
    public Position getCurrentPosition() {
        return currentPosition;
    }

    /**
     * Model.Player toString
     *
     * @return player board character
     */
    public String toString() {
        return playerVanityName + " - " + this.character + " [" + this.character.getCharacterColorName() + "] on board";
    }

    // Model.Player State Gettersa

    /**
     * Check player ability to make accusation
     *
     * @return can accuse boolean validator
     */
    public boolean getCanAccuse() {
        return canAccuse;
    }

    public String getPlayerVanityName(){
        return playerVanityName;
    }

}