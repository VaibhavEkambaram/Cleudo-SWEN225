package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Player Class
 */
public class Player implements Comparable<Player> {

    private final List<Card> hand;
    // Player Gameplay Information
    private final CharacterCard character;
    private Position currentPosition;
    // Player State
    private boolean canAccuse = true;

    // player set name
    private final String playerVanityName;


    /**
     * Player Constructor and Initialise Methods
     *
     * @param aCharacter character associated with player
     * @param playerVanityName display name for player
     */
    public Player(CharacterCard aCharacter, String playerVanityName) {
        character = aCharacter;
        this.playerVanityName = playerVanityName;
        hand = new ArrayList<>();
    }


    /**
     * Add Card to player hand
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
     * Set current Position of player
     *
     * @param aCurrentPosition position on board
     */
    public void setCurrentPosition(Position aCurrentPosition) {
        currentPosition = aCurrentPosition;
    }

    // Player Information Getters


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
     * Player toString
     *
     * @return player board character
     */
    public String toString() {
        return playerVanityName + " - " + this.character + " [" + this.character.getCharacterColorName() + "] on board";
    }

    // Player State Getters

    /**
     * Check player ability to make accusation
     *
     * @return can accuse boolean validator
     */
    public boolean getCanAccuse() {
        return canAccuse;
    }

    /**
     * Get "Vanity" name of player which was set at the beginning of the game
     *
     * @return name string
     */
    public String getPlayerVanityName() {
        return playerVanityName;
    }

    /**
     * Comparator for sorting players
     *
     * @param o other player
     * @return comparator value
     */
    @Override
    public int compareTo(Player o) {
        if (this.character.getCharacterPriority() > o.character.getCharacterPriority()) {
            return 1;
        } else {
            return -1;
        }
    }
}