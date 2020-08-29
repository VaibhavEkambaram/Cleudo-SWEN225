package model;

/**
 * Scenario Class
 * Stores a randomly generated murder scenario consisting of a room, character and weapon
 */
public class Scenario {

    // Scenario Attributes
    private final RoomCard roomCard;
    private final WeaponCard weapon;
    private final CharacterCard murderer;

    /**
     * Scenario Constructor
     *
     * @param weapon   murder weapon
     * @param room     room where murder took place
     * @param murderer the player that is the murderer
     */
    public Scenario(WeaponCard weapon, RoomCard room, CharacterCard murderer) {
        this.weapon = weapon;
        this.roomCard = room;
        this.murderer = murderer;
    }

    /**
     * Room Card Get Method
     *
     * @return room card
     */
    public RoomCard getRoom() {
        return roomCard;
    }

    /**
     * Weapon Card Get Method
     *
     * @return weapon card
     */
    public WeaponCard getWeapon() {
        return weapon;
    }

    /**
     * Character Card Get Method
     *
     * @return murderer character card
     */
    public CharacterCard getMurderer() {
        return murderer;
    }

    /**
     * Equals Method - Compare scenario with another
     *
     * @param o other object
     * @return boolean validator
     * @author Vaibhav Ekambaram
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Scenario) {
            return o.toString().equals(this.toString());
        }
        return false;
    }

    /**
     * Placeholder toString method
     *
     * @return room + murderer + weapon string
     * @author Vaibhav Ekambaram
     */
    public String toString() {
        return getRoom().getRoomName() + " " + getMurderer().getCharacterName() + " " + getWeapon().getWeaponName();
    }
}