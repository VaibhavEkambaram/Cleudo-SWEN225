/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5071.d9da8f6cd modeling language!*/


import java.util.*;

// line 50 "model.ump"
// line 166 "model.ump"
// line 224 "model.ump"
public class Game {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    private List<Card> deck;
    private List<Player> players;

    // Identifiers
    private List<WeaponCard> weapons;
    private List<Room> rooms;
    private List<RoomCard> roomCards;
    private List<CharacterCard> characters;

    //Game Associations
    private List<Scenario> scenarios;
    //Game Attributes
    private Board board;
    private Player currentPlayer;
    private Scenario murderScenario;


    //------------------------
    // CONSTRUCTOR
    //------------------------

    public Game() {
        initializeGame();
        //System.out.println(rollDice());
        String board =  "|X|X|X|X|X|X|X|X|X|_|X|X|X|X|_|X|X|X|X|X|X|X|X|X|\n" +
                        "|_|_|_|_|_|_|X|_|_|_|_|_|_|_|_|_|_|X|_|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|X|\n" +
                        "|X|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|X|\n" +
                        "|X|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|_|_|_|_|_|X|X|X|X|X|_|_|_|_|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|_|_|_|_|_|X|X|X|X|X|_|_|_|_|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|_|_|_|_|_|X|X|X|X|X|_|_|_|_|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|_|_|_|_|_|X|X|X|X|X|_|_|_|_|_|_|_|_|X|\n" +
                        "|_|_|_|_|_|_|_|_|_|_|X|X|X|X|X|_|_|_|_|_|_|_|_|X|\n" +
                        "|_|_|_|_|_|_|_|_|_|_|X|X|X|X|X|_|_|_|_|_|_|_|_|_|\n" +
                        "|X|_|_|_|_|_|_|_|_|_|X|X|X|X|X|_|_|_|_|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|\n" +
                        "|X|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|X|\n" +
                        "|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|X|\n" +
                        "|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|\n" +
                        "|_|_|_|_|_|_|X|_|X|_|_|_|_|_|_|X|_|X|_|_|_|_|_|_|\n";
        System.out.println(board);



    }

    public static void main(String[] args){
        System.out.println("--------------------------------\n" +
                "\t\t\tCluedo!\n" +
                "--------------------------------\n" +
                "SWEN225 Assignment 1\n" +
                "A group project by:\n" +
                "* Vaibhav Ekambaram \n" +
                "* Cameron Li\n" +
                "* Baxter Kirikiri\n" +
                "--------------------------------\n" +
                "\n");
        new Game();
    }

    public void initializeGame() {
        System.out.println("How many players wish to participate? (3 - 6)");
        int numPlayers = 0;
        Scanner sc = new Scanner(System.in);
        while (numPlayers < 3 || numPlayers > 6) {
            boolean isNumber = true;
            String number = sc.nextLine();
            try {
                numPlayers = Integer.parseInt(number);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number");
                isNumber = false;
            }
            if (isNumber) {
                System.out.println("Please enter a number between 3 and 6");
            }
        }

        Scenario murderScenario = initialiseDeck();

        for (int n = 0; n < numPlayers; n++) {
            players.add(new Player(characters.get(n)));
        }
    }

    private Scenario initialiseDeck() {
        // Adding cards
        deck = new ArrayList<>();

        // Weapons
        String[] wepNames = {"Candlestick", "Dagger", "Lead Pipe", "Revolver", "Rope", "Spanner"};
        weapons = new ArrayList<>();
        for (String w : wepNames) {
            WeaponCard weapon = new WeaponCard(w);
            weapons.add(weapon);
            deck.add(weapon);
        }

        // Rooms
        String[] roomNames = {"Kitchen", "Dining Room", "Lounge", "Hall", "Study",
                "Library", "Billiard Room", " Conservatory", "Ballroom"};
        roomCards = new ArrayList<>();
        for (String r : roomNames) {
            Room newRoom = new Room(r);
            rooms.add(newRoom);
            RoomCard newRoomCard = new RoomCard(r, newRoom);
            roomCards.add(newRoomCard);
            deck.add(newRoomCard);
        }

        // Characters
        String[] characterNames = {"Mrs. White", "Mr. Green", "Mrs. Peacock", "Prof. Plum",
                "Ms. Scarlet", "Col. Mustard"};
        characters = new ArrayList<>();
        for (String c : characterNames) {
            CharacterCard character = new CharacterCard(c);
            characters.add(character);
            deck.add(character);
        }

        Collections.shuffle(deck);
        // Murder Scenario of Random Cards
        Scenario murderScenario = new Scenario(weapons.get(new Random().nextInt(wepNames.length - 1) + 1),
                roomCards.get(new Random().nextInt(roomNames.length - 1) + 1),
                characters.get(new Random().nextInt(characterNames.length - 1) + 1));

        return murderScenario;
    }


    //------------------------
    // INTERFACE
    //------------------------

    /* Code from template association_MinimumNumberOfMethod */
    public static int minimumNumberOfScenarios() {
        return 0;
    }

    public boolean setBoard(Board aBoard) {
        boolean wasSet = false;
        board = aBoard;
        wasSet = true;
        return wasSet;
    }

    public boolean setCurrentPlayer(Player aCurrentPlayer) {
        boolean wasSet = false;
        currentPlayer = aCurrentPlayer;
        wasSet = true;
        return wasSet;
    }

    public boolean setMurderScenario(Scenario aMurderScenario) {
        boolean wasSet = false;
        murderScenario = aMurderScenario;
        wasSet = true;
        return wasSet;
    }

    /* Code from template attribute_SetMany */
    public boolean addPlayer(Player aPlayer) {
        boolean wasAdded = false;
        wasAdded = players.add(aPlayer);
        return wasAdded;
    }

    public boolean removePlayer(Player aPlayer) {
        boolean wasRemoved = false;
        wasRemoved = players.remove(aPlayer);
        return wasRemoved;
    }

    public Board getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Scenario getMurderScenario() {
        return murderScenario;
    }

    /* Code from template attribute_GetMany */
    public Player getPlayer(int index) {
        Player aPlayer = players.get(index);
        return aPlayer;
    }

    public Player[] getPlayers() {
        Player[] newPlayers = players.toArray(new Player[players.size()]);
        return newPlayers;
    }

    public int numberOfPlayers() {
        int number = players.size();
        return number;
    }

    public boolean hasPlayers() {
        boolean has = players.size() > 0;
        return has;
    }

    public int indexOfPlayer(Player aPlayer) {
        int index = players.indexOf(aPlayer);
        return index;
    }

    /* Code from template association_GetMany */
    public Scenario getScenario(int index) {
        Scenario aScenario = scenarios.get(index);
        return aScenario;
    }

    public List<Scenario> getScenarios() {
        List<Scenario> newScenarios = Collections.unmodifiableList(scenarios);
        return newScenarios;
    }

    public int numberOfScenarios() {
        int number = scenarios.size();
        return number;
    }

    public boolean hasScenarios() {
        boolean has = scenarios.size() > 0;
        return has;
    }

    public int indexOfScenario(Scenario aScenario) {
        int index = scenarios.indexOf(aScenario);
        return index;
    }

    /* Code from template association_AddManyToManyMethod */
    public boolean addScenario(Scenario aScenario) {
        boolean wasAdded = false;
        if (scenarios.contains(aScenario)) {
            return false;
        }
        scenarios.add(aScenario);
        if (aScenario.indexOfGame(this) != -1) {
            wasAdded = true;
        } else {
            wasAdded = aScenario.addGame(this);
            if (!wasAdded) {
                scenarios.remove(aScenario);
            }
        }
        return wasAdded;
    }

    /* Code from template association_RemoveMany */
    public boolean removeScenario(Scenario aScenario) {
        boolean wasRemoved = false;
        if (!scenarios.contains(aScenario)) {
            return wasRemoved;
        }

        int oldIndex = scenarios.indexOf(aScenario);
        scenarios.remove(oldIndex);
        if (aScenario.indexOfGame(this) == -1) {
            wasRemoved = true;
        } else {
            wasRemoved = aScenario.removeGame(this);
            if (!wasRemoved) {
                scenarios.add(oldIndex, aScenario);
            }
        }
        return wasRemoved;
    }

    /* Code from template association_AddIndexControlFunctions */
    public boolean addScenarioAt(Scenario aScenario, int index) {
        boolean wasAdded = false;
        if (addScenario(aScenario)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfScenarios()) {
                index = numberOfScenarios() - 1;
            }
            scenarios.remove(aScenario);
            scenarios.add(index, aScenario);
            wasAdded = true;
        }
        return wasAdded;
    }

    public boolean addOrMoveScenarioAt(Scenario aScenario, int index) {
        boolean wasAdded = false;
        if (scenarios.contains(aScenario)) {
            if (index < 0) {
                index = 0;
            }
            if (index > numberOfScenarios()) {
                index = numberOfScenarios() - 1;
            }
            scenarios.remove(aScenario);
            scenarios.add(index, aScenario);
            wasAdded = true;
        } else {
            wasAdded = addScenarioAt(aScenario, index);
        }
        return wasAdded;
    }

    public void delete() {
        ArrayList<Scenario> copyOfScenarios = new ArrayList<Scenario>(scenarios);
        scenarios.clear();
        for (Scenario aScenario : copyOfScenarios) {
            aScenario.removeGame(this);
        }
    }

    // line 61 "model.ump"
    public int rollDice() {

        // find a random number in the range of 0 to 5, then add 1 as an offset for 1 to 6
        int firstResult = new Random().nextInt(6) + 1;
        int secondResult = new Random().nextInt(6) + 1;

        System.out.println("first dice throw: "+firstResult);
        System.out.println("second dice throw: "+secondResult);
        return firstResult + secondResult;
    }

    // line 64 "model.ump"
    public void dealCards() {

    }

    // line 67 "model.ump"
    public void suggestion() {

    }

    // line 70 "model.ump"
    public boolean accusation() {
        return false;
    }


    public String toString() {
        return super.toString() + "[" + "]" + System.getProperties().getProperty("line.separator") +
                "  " + "board" + "=" + (getBoard() != null ? !getBoard().equals(this) ? getBoard().toString().replaceAll("  ", "    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "currentPlayer" + "=" + (getCurrentPlayer() != null ? !getCurrentPlayer().equals(this) ? getCurrentPlayer().toString().replaceAll("  ", "    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
                "  " + "murderScenario" + "=" + (getMurderScenario() != null ? !getMurderScenario().equals(this) ? getMurderScenario().toString().replaceAll("  ", "    ") : "this" : "null");
    }
}