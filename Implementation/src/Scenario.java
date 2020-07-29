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

    public Scenario(RoomCard aRoomCard, WeaponCard aWeapon, CharacterCard aMurderer) {
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

    /* Code from template association_AddManyToManyMethod */
    public boolean addWeaponCard(WeaponCard aWeaponCard) {
        boolean wasAdded = false;
        if (weaponCards.contains(aWeaponCard)) {
            return false;
        }
        weaponCards.add(aWeaponCard);
        if (aWeaponCard.indexOfScenario(this) != -1) {
            wasAdded = true;
        } else {
            wasAdded = aWeaponCard.addScenario(this);
            if (!wasAdded) {
                weaponCards.remove(aWeaponCard);
            }
        }
        return wasAdded;
    }

    /* Code from template association_RemoveMany */
    public boolean removeWeaponCard(WeaponCard aWeaponCard) {
        boolean wasRemoved = false;
        if (!weaponCards.contains(aWeaponCard)) {
            return wasRemoved;
        }

        int oldIndex = weaponCards.indexOf(aWeaponCard);
        weaponCards.remove(oldIndex);
        if (aWeaponCard.indexOfScenario(this) == -1) {
            wasRemoved = true;
        } else {
            wasRemoved = aWeaponCard.removeScenario(this);
            if (!wasRemoved) {
                weaponCards.add(oldIndex, aWeaponCard);
            }
        }
        return wasRemoved;
    }

    /* Code from template association_AddIndexControlFunctions */
    public boolean addWeaponCardAt(WeaponCard aWeaponCard, int index) {
        boolean wasAdded = false;
        if (addWeaponCard(aWeaponCard)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfWeaponCards()) {
                index = numberOfWeaponCards() - 1;
            }
            weaponCards.remove(aWeaponCard);
            weaponCards.add(index, aWeaponCard);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveWeaponCardAt(WeaponCard aWeaponCard, int index) {
        boolean wasAdded = false;
        if (weaponCards.contains(aWeaponCard)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfWeaponCards()) {
                index = numberOfWeaponCards() - 1;
            }
            weaponCards.remove(aWeaponCard);
            weaponCards.add(index, aWeaponCard);
            wasAdded = true;
        } else {
            wasAdded = addWeaponCardAt(aWeaponCard, index);
        }
        return wasAdded;
    }

    /* Code from template association_AddManyToManyMethod */
    public boolean addRoomCard(RoomCard aRoomCard) {
        boolean wasAdded = false;
        if (roomCards.contains(aRoomCard)) {
            return false;
        }
        roomCards.add(aRoomCard);
        if (aRoomCard.indexOfScenario(this) != -1) {
            wasAdded = true;
        } else {
            wasAdded = aRoomCard.addScenario(this);
            if (!wasAdded) {
                roomCards.remove(aRoomCard);
            }
        }
        return wasAdded;
    }

    /* Code from template association_RemoveMany */
    public boolean removeRoomCard(RoomCard aRoomCard) {
        boolean wasRemoved = false;
        if (!roomCards.contains(aRoomCard)) {
            return wasRemoved;
        }

        int oldIndex = roomCards.indexOf(aRoomCard);
        roomCards.remove(oldIndex);
        if (aRoomCard.indexOfScenario(this) == -1) {
            wasRemoved = true;
        } else {
            wasRemoved = aRoomCard.removeScenario(this);
            if (!wasRemoved) {
                roomCards.add(oldIndex, aRoomCard);
            }
        }
        return wasRemoved;
    }

    /* Code from template association_AddIndexControlFunctions */
    public boolean addRoomCardAt(RoomCard aRoomCard, int index) {
        boolean wasAdded = false;
        if (addRoomCard(aRoomCard)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfRoomCards()) {
                index = numberOfRoomCards() - 1;
            }
            roomCards.remove(aRoomCard);
            roomCards.add(index, aRoomCard);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveRoomCardAt(RoomCard aRoomCard, int index) {
        boolean wasAdded = false;
        if (roomCards.contains(aRoomCard)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfRoomCards()) {
                index = numberOfRoomCards() - 1;
            }
            roomCards.remove(aRoomCard);
            roomCards.add(index, aRoomCard);
            wasAdded = true;
        } else {
            wasAdded = addRoomCardAt(aRoomCard, index);
        }
        return wasAdded;
    }

    /* Code from template association_AddManyToManyMethod */
    public boolean addCharacterCard(CharacterCard aCharacterCard) {
        boolean wasAdded = false;
        if (characterCards.contains(aCharacterCard)) {
            return false;
        }
        characterCards.add(aCharacterCard);
        if (aCharacterCard.indexOfScenario(this) != -1) {
            wasAdded = true;
        } else {
            wasAdded = aCharacterCard.addScenario(this);
            if (!wasAdded) {
                characterCards.remove(aCharacterCard);
            }
        }
        return wasAdded;
    }

    /* Code from template association_RemoveMany */
    public boolean removeCharacterCard(CharacterCard aCharacterCard) {
        boolean wasRemoved = false;
        if (!characterCards.contains(aCharacterCard)) {
            return wasRemoved;
        }

        int oldIndex = characterCards.indexOf(aCharacterCard);
        characterCards.remove(oldIndex);
        if (aCharacterCard.indexOfScenario(this) == -1) {
            wasRemoved = true;
        } else {
            wasRemoved = aCharacterCard.removeScenario(this);
            if (!wasRemoved) {
                characterCards.add(oldIndex, aCharacterCard);
            }
        }
        return wasRemoved;
    }

    /* Code from template association_AddIndexControlFunctions */
    public boolean addCharacterCardAt(CharacterCard aCharacterCard, int index) {
        boolean wasAdded = false;
        if (addCharacterCard(aCharacterCard)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfCharacterCards()) {
                index = numberOfCharacterCards() - 1;
            }
            characterCards.remove(aCharacterCard);
            characterCards.add(index, aCharacterCard);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveCharacterCardAt(CharacterCard aCharacterCard, int index) {
        boolean wasAdded = false;
        if (characterCards.contains(aCharacterCard)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfCharacterCards()) {
                index = numberOfCharacterCards() - 1;
            }
            characterCards.remove(aCharacterCard);
            characterCards.add(index, aCharacterCard);
            wasAdded = true;
        } else {
            wasAdded = addCharacterCardAt(aCharacterCard, index);
        }
        return wasAdded;
    }

    /* Code from template association_AddManyToManyMethod */
    public boolean addGame(Game aGame) {
        boolean wasAdded = false;
        if (games.contains(aGame)) {
            return false;
        }
        games.add(aGame);
        if (aGame.indexOfScenario(this) != -1) {
            wasAdded = true;
        } else {
            wasAdded = aGame.addScenario(this);
            if (!wasAdded) {
                games.remove(aGame);
            }
        }
        return wasAdded;
    }

    /* Code from template association_RemoveMany */
    public boolean removeGame(Game aGame) {
        boolean wasRemoved = false;
        if (!games.contains(aGame)) {
            return wasRemoved;
        }

        int oldIndex = games.indexOf(aGame);
        games.remove(oldIndex);
        if (aGame.indexOfScenario(this) == -1) {
            wasRemoved = true;
        } else {
            wasRemoved = aGame.removeScenario(this);
            if (!wasRemoved) {
                games.add(oldIndex, aGame);
            }
        }
        return wasRemoved;
    }

    /* Code from template association_AddIndexControlFunctions */
    public boolean addGameAt(Game aGame, int index) {
        boolean wasAdded = false;
        if (addGame(aGame)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfGames()) {
                index = numberOfGames() - 1;
            }
            games.remove(aGame);
            games.add(index, aGame);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveGameAt(Game aGame, int index) {
        boolean wasAdded = false;
        if (games.contains(aGame)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfGames()) {
                index = numberOfGames() - 1;
            }
            games.remove(aGame);
            games.add(index, aGame);
            wasAdded = true;
        } else {
            wasAdded = addGameAt(aGame, index);
        }
        return wasAdded;
    }

    /* Code from template association_AddManyToManyMethod */
    public boolean addBoard(Board aBoard) {
        boolean wasAdded = false;
        if (boards.contains(aBoard)) {
            return false;
        }
        boards.add(aBoard);
        if (aBoard.indexOfScenario(this) != -1) {
            wasAdded = true;
        } else {
            wasAdded = aBoard.addScenario(this);
            if (!wasAdded) {
                boards.remove(aBoard);
            }
        }
        return wasAdded;
    }

    /* Code from template association_RemoveMany */
    public boolean removeBoard(Board aBoard) {
        boolean wasRemoved = false;
        if (!boards.contains(aBoard)) {
            return wasRemoved;
        }

        int oldIndex = boards.indexOf(aBoard);
        boards.remove(oldIndex);
        if (aBoard.indexOfScenario(this) == -1) {
            wasRemoved = true;
        } else {
            wasRemoved = aBoard.removeScenario(this);
            if (!wasRemoved) {
                boards.add(oldIndex, aBoard);
            }
        }
        return wasRemoved;
    }

    /* Code from template association_AddIndexControlFunctions */
    public boolean addBoardAt(Board aBoard, int index) {
        boolean wasAdded = false;
        if (addBoard(aBoard)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfBoards()) {
                index = numberOfBoards() - 1;
            }
            boards.remove(aBoard);
            boards.add(index, aBoard);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveBoardAt(Board aBoard, int index) {
        boolean wasAdded = false;
        if (boards.contains(aBoard)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfBoards()) {
                index = numberOfBoards() - 1;
            }
            boards.remove(aBoard);
            boards.add(index, aBoard);
            wasAdded = true;
        } else {
            wasAdded = addBoardAt(aBoard, index);
        }
        return wasAdded;
    }

    public void delete() {
        ArrayList<WeaponCard> copyOfWeaponCards = new ArrayList<WeaponCard>(weaponCards);
        weaponCards.clear();
        for (WeaponCard aWeaponCard : copyOfWeaponCards) {
            aWeaponCard.removeScenario(this);
        }
        ArrayList<RoomCard> copyOfRoomCards = new ArrayList<RoomCard>(roomCards);
        roomCards.clear();
        for (RoomCard aRoomCard : copyOfRoomCards) {
            aRoomCard.removeScenario(this);
        }
        ArrayList<CharacterCard> copyOfCharacterCards = new ArrayList<CharacterCard>(characterCards);
        characterCards.clear();
        for (CharacterCard aCharacterCard : copyOfCharacterCards) {
            aCharacterCard.removeScenario(this);
        }
        ArrayList<Game> copyOfGames = new ArrayList<Game>(games);
        games.clear();
        for (Game aGame : copyOfGames) {
            aGame.removeScenario(this);
        }
        ArrayList<Board> copyOfBoards = new ArrayList<Board>(boards);
        boards.clear();
        for (Board aBoard : copyOfBoards) {
            aBoard.removeScenario(this);
        }
    }

    // line 40 "model.ump"
    public Boolean isEqual(Scenario o) {
        return false;
    }


    public String toString() {
        return super.toString() + "[" + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "roomCard" + "=" + (getRoomCard() != null ? !getRoomCard().equals(this) ? getRoomCard().toString().replaceAll("  ", "    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "weapon" + "=" + (getWeapon() != null ? !getWeapon().equals(this) ? getWeapon().toString().replaceAll("  ", "    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "murderer" + "=" + (getMurderer() != null ? !getMurderer().equals(this) ? getMurderer().toString().replaceAll("  ", "    ") : "this" : "null");
    }
}