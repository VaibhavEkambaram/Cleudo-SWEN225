package model;

/**
 * Model.Card Interface - Later used to make Character, Model.Room and Weapon Cards
 */
public interface Card {

    /**
     * General toString method
     *
     * @return card string (generally returned from override in specific card)
     */
    String toString();
}