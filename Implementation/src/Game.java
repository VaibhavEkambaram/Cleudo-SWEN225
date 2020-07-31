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
    private Map<String, Position> boardPositions;

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

        String board = "xxxxxxxxx1xxxx2xxxxxxxxx\n" +
                "######x___####___x######\n" +
                "#____#__###__###__#____#\n" +
                "#____#__#______#__#____#\n" +
                "#____#__#______#__C#__##\n" +
                "#____#__b______b___####x\n" +
                "x###K#__#______#_______3\n" +
                "________#B####B#_______x\n" +
                "x_________________######\n" +
                "#####_____________i____#\n" +
                "#___####__xxxxx___#____#\n" +
                "#______#__xxxxx___#____#\n" +
                "#______d__xxxxx___####I#\n" +
                "#______#__xxxxx________x\n" +
                "#______#__xxxxx___##L##x\n" +
                "######D#__xxxxx__##___##\n" +
                "x_________xxxxx__l_____#\n" +
                "6________________##___##\n" +
                "x________##HH##___#####x\n" +
                "######O__#____#________4\n" +
                "#_____#__#____h________x\n" +
                "#_____#__#____#__Y######\n" +
                "#_____#__#____#__#_____#\n" +
                "#____##__#____#__##____#\n" +
                "######x5x######x_x######\n";

                        /*

                        ** Players **
                            1 - Mrs. White
                            2 - Mr. Green
                            3 - Mrs. Peacock
                            4 - Prof. Plum
                            5 - Ms. Scarlett
                            6 - Col. Mustard

                        ** Board **
                        x - Null Area (Off limits)
                        # - Room Wall

                        ** Rooms **
                        (lower case - only left/right movement, upper case - only up/down)
                        C - Conservatory
                        B - Ball Room
                        K - Kitchen
                        I - Billiard Room
                        D - Dining Room
                        L - Library
                        O - Lounge
                        H - Hall
                        Y - Study

                        ** Weapons **
                        ? - Candlestick
                        ! - Dagger
                        $ - Lead Pipe
                        % - Revolver
                        @ - Rope
                        & - Spanner
                         */


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


        initialiseDeck();
        players = new ArrayList<>();
        for (int n = 0; n < numPlayers; n++) {
            players.add(new Player(characters.get(n)));
        }
        this.dealCards();
    }

    // Create the deck and murder scenario
    private void initialiseDeck() {
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
        rooms = new ArrayList<>();
        roomCards = new ArrayList<>();
        for (String r : roomNames) {
            Room newRoom = new Room(r);
            rooms.add(newRoom);
            RoomCard newRoomCard = new RoomCard(r, newRoom);
            roomCards.add(newRoomCard);
            deck.add(newRoomCard);
        }

        String[] characterNames = {"Mrs. White", "Mr. Green", "Mrs. Peacock", "Prof. Plum",
                "Ms. Scarlett", "Col. Mustard"};
        characters = new ArrayList<>();
        for (String c : characterNames) {
            //TODO: add position here
            CharacterCard character = new CharacterCard(c,null);
            characters.add(character);
            deck.add(character);
        }

        Collections.shuffle(deck);
        // Murder Scenario of Random Cards
        Scenario newMurderScenario = new Scenario(weapons.get(new Random().nextInt(wepNames.length - 1) + 1),
                roomCards.get(new Random().nextInt(roomNames.length - 1) + 1),
                characters.get(new Random().nextInt(characterNames.length - 1) + 1));

        murderScenario = newMurderScenario;
    }

    private void generatePositions(){
        //Create a position object for each co-ordinate on the board. These are stored in a map as a field of the game class.
        for(int x = 1; x <= 24; x++){
           for(int y = 1; y <= 25; y++){
               String key = x + ", " + y;
               Room noRoom = new Room("No Room"); //there are no rooms yet so I'll just use this as filler for the room parameter
               Position newPosition = new Position(noRoom,true,x,y);
               this.boardPositions.put(key, newPosition);
           }
        }
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
        //Add cards from the deck list to a stack, then deal them to each player until the stack is empty
        Stack<Card> toBeDealt = new Stack<>();
        for(Card c : this.deck){
            toBeDealt.push(c);
        }

        while(!toBeDealt.isEmpty()){
            for(Player p : this.players) {
                if (toBeDealt.isEmpty()) {
                    break;
                }
                p.addHand(toBeDealt.pop());
            }
        }
        System.out.println("Cards Dealt");
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