/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5071.d9da8f6cd modeling language!*/


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// line 32 "model.ump"
// line 135 "model.ump"
// line 156 "model.ump"
// line 214 "model.ump"
public class Scenario {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    //Scenario Associations
    private final List<WeaponCard> weaponCards;
    private final List<RoomCard> roomCards;
    private final List<CharacterCard> characterCards;
    private final List<Game> games;
    private final List<Board> boards;
    //Scenario Attributes
    private RoomCard roomCard;
    private WeaponCard weapon;
    private CharacterCard murderer;

    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Scenario(WeaponCard aWeapon, RoomCard aRoomCard, CharacterCard aMurderer) {
        roomCard = aRoomCard;
        weapon = aWeapon;
        murderer = aMurderer;
        weaponCards = new ArrayList<WeaponCard>();
        roomCards = new ArrayList<RoomCard>();
        characterCards = new ArrayList<CharacterCard>();
        games = new ArrayList<Game>();
        boards = new ArrayList<Board>();
    }

    //------------------------
    // INTERFACE
    //------------------------

    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfWeaponCards() {
        return 0;
    }

    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfRoomCards() {
        return 0;
    }

    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfCharacterCards() {
        return 0;
    }

    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfGames() {
        return 0;
    }

    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfBoards() {
        return 0;
    }

    public boolean setRoomCard(RoomCard aRoomCard) {
        boolean wasSet = false;
        roomCard = aRoomCard;
        wasSet = true;
        return wasSet;
    }

    public boolean setWeapon(WeaponCard aWeapon) {
        boolean wasSet = false;
        weapon = aWeapon;
        wasSet = true;
        return wasSet;
    }

    public boolean setMurderer(CharacterCard aMurderer) {
        boolean wasSet = false;
        murderer = aMurderer;
        wasSet = true;
        return wasSet;
    }

    public RoomCard getRoomCard() {
        return roomCard;
    }

    public WeaponCard getWeapon() {
        return weapon;
    }

    public CharacterCard getMurderer() {
        return murderer;
    }

    /* Code from template association_GetMany */
    public WeaponCard getWeaponCard(int index) {
        WeaponCard aWeaponCard = weaponCards.get(index);
        return aWeaponCard;
    }

    public List<WeaponCard> getWeaponCards() {
        List<WeaponCard> newWeaponCards = Collections.unmodifiableList(weaponCards);
        return newWeaponCards;
    }

    public int numberOfWeaponCards() {
        int number = weaponCards.size();
        return number;
    }

    public boolean hasWeaponCards() {
        boolean has = weaponCards.size() > 0;
        return has;
    }

    public int indexOfWeaponCard(WeaponCard aWeaponCard) {
        int index = weaponCards.indexOf(aWeaponCard);
        return index;
    }

    /* Code from template association_GetMany */
    public RoomCard getRoomCard(int index) {
        RoomCard aRoomCard = roomCards.get(index);
        return aRoomCard;
    }

    public List<RoomCard> getRoomCards() {
        List<RoomCard> newRoomCards = Collections.unmodifiableList(roomCards);
        return newRoomCards;
    }

    public int numberOfRoomCards() {
        int number = roomCards.size();
        return number;
    }

    public boolean hasRoomCards() {
        boolean has = roomCards.size() > 0;
        return has;
    }

    public int indexOfRoomCard(RoomCard aRoomCard) {
        int index = roomCards.indexOf(aRoomCard);
        return index;
    }

    /* Code from template association_GetMany */
    public CharacterCard getCharacterCard(int index) {
        CharacterCard aCharacterCard = characterCards.get(index);
        return aCharacterCard;
    }

    public List<CharacterCard> getCharacterCards() {
        List<CharacterCard> newCharacterCards = Collections.unmodifiableList(characterCards);
        return newCharacterCards;
    }

    public int numberOfCharacterCards() {
        int number = characterCards.size();
        return number;
    }

    public boolean hasCharacterCards() {
        boolean has = characterCards.size() > 0;
        return has;
    }

    public int indexOfCharacterCard(CharacterCard aCharacterCard) {
        int index = characterCards.indexOf(aCharacterCard);
        return index;
    }

    /* Code from template association_GetMany */
    public Game getGame(int index) {
        Game aGame = games.get(index);
        return aGame;
    }

    public List<Game> getGames() {
        List<Game> newGames = Collections.unmodifiableList(games);
        return newGames;
    }

    public int numberOfGames() {
        int number = games.size();
        return number;
    }

    public boolean hasGames() {
        boolean has = games.size() > 0;
        return has;
    }

    public int indexOfGame(Game aGame) {
        int index = games.indexOf(aGame);
        return index;
    }

    /* Code from template association_GetMany */
    public Board getBoard(int index) {
        Board aBoard = boards.get(index);
        return aBoard;
    }

    public List<Board> getBoards() {
        List<Board> newBoards = Collections.unmodifiableList(boards);
        return newBoards;
    }

    public int numberOfBoards() {
        int number = boards.size();
        return number;
    }

    public boolean hasBoards() {
        boolean has = boards.size() > 0;
        return has;
    }

    public int indexOfBoard(Board aBoard) {
        int index = boards.indexOf(aBoard);
        return index;
    }


    public String toString() {
        return super.toString() + "[" + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "roomCard" + "=" + (getRoomCard() != null ? !getRoomCard().equals(this) ? getRoomCard().toString().replaceAll("  ", "    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "weapon" + "=" + (getWeapon() != null ? !getWeapon().equals(this) ? getWeapon().toString().replaceAll("  ", "    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "murderer" + "=" + (getMurderer() != null ? !getMurderer().equals(this) ? getMurderer().toString().replaceAll("  ", "    ") : "this" : "null");
    }
}