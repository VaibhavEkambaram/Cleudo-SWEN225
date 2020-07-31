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
        initGame();

        String boardLayout = " x x x x x x x x x 1 x x x x 2 x x x x x x x x x \n" +
                " k k k k k k x _ _ _ b b b b _ _ _ x c c c c c c \n" +
                " k K K K K k _ _ b b b B B b b b _ _ c C C C C c \n" +
                " k K sK K K k _ _ b B B sB B B B b _ _ c C C sC c C \n" +
                " k K K K K k _ _ b B B B B B B b _ _ dC c C C c c \n" +
                " k K K K K k _ _ dB B B B B B B dB _ _ _ c c c c x \n" +
                " x k k k dK k _ _ b B B B B B B b _ _ _ _ _ _ _ 3 \n" +
                " _ _ _ _ _ _ _ _ b dB b b b b dB b _ _ _ _ _ _ _ x \n" +
                " x _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ i i i i i i \n" +
                " d d d d d _ _ _ _ _ _ _ _ _ _ _ _ _ dI I I I I i \n" +
                " d D D D d d d d _ _ x x x x x _ _ _ i I I I I i \n" +
                " d D D D D D D d _ _ x x x x x _ _ _ i I I I I i \n" +
                " d D D D D D D dD _ _ x x x x x _ _ _ i i i i dI i \n" +
                " d D D D D D D d _ _ x x x x x _ _ _ _ _ _ _ _ x \n" +
                " d D D D D D D d _ _ x x x x x _ _ _ l l dL l l x \n" +
                " d d d d d d dD d _ _ x x x x x _ _ l l L L L l l \n" +
                " x _ _ _ _ _ _ _ _ _ x x x x x _ _ dL L L L L L l \n" +
                " 6 _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ l l L L L l l \n" +
                " x _ _ _ _ _ _ _ _ h h dH dH h h _ _ _ l l l l l x \n" +
                " o o o o o o dO _ _ h H H H H h _ _ _ _ _ _ _ _ 4 \n" +
                " o O O O O O o _ _ h H H H H dH _ _ _ _ _ _ _ _ x \n" +
                " o O O sO O O o _ _ h H H H H h _ _ dY y y y y y y \n" +
                " o O O O O O o _ _ h H H H H h _ _ y Y Y Y Y Y y \n" +
                " o O O O O O o _ _ h H H H H h _ _ y y Y Y Y Y y \n" +
                " o o o o o o x 5 x h h h h h h x _ x y y y y y y \n";

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

        initBoard(boardLayout);
    }

    public static void main(String[] args) {
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

    // Ask user for number of players
    public void initGame() {
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


        initDeck();
        players = new ArrayList<>();
        for (int n = 0; n < numPlayers; n++) {
            players.add(new Player(characters.get(n)));
        }
        this.dealCards();
    }

    // Create the deck and murder scenario
    private void initDeck() {
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
                "Library", "Billiard Room", "Conservatory", "Ball Room"};
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
            CharacterCard character = new CharacterCard(c, null);
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

    private Board initBoard(String boardLayout) {
        Board board = new Board();
        Scanner layoutScan = new Scanner(boardLayout);
        int y = -1;
        while (layoutScan.hasNextLine()) {
            String scanLine = layoutScan.nextLine();
            // Increment column, and setup rows
            y++;
            int x = -1;
            Scanner positionScan = new Scanner(scanLine);
            while (positionScan.hasNext()) {
                String positionName = positionScan.next();
                x++; // Increment row
                Position newPosition = null;
                // Add a but load of if statements here :D
                if (positionName.equals("x")) {
                    newPosition = new Position(x, y, false);
                }
                if (positionName.equals("_")) {
                    newPosition = new Position(x, y, true);
                }

                if (newPosition == null) {
                    for (Room r : rooms) {
                        if (positionName == r.getRoomChar()) {
                            newPosition = new Position(x, y, true, true, false, r);
                            break;
                        } else if (positionName.equals("s" + r.getRoomChar())) { // show
                            newPosition = new Position(x, y, true, true, true, r);
                            break;
                        } else if (positionName.equals("d" + r.getRoomChar())) { // door
                            newPosition = new Position(x, y, true, true, false, r);
                            break;
                        } else if (positionName.equals(r.getRoomChar().toLowerCase())) { // wall
                            newPosition = new Position(x, y, true, false, false, r);
                            break;
                        }
                    }
                }

                if (newPosition == null) {
                    for (CharacterCard c : characters) {
                        //System.out.println(positionName + c.getCharacterBoardChar());
                        if (positionName == c.getCharacterBoardChar()) {
                            newPosition = new Position(x, y, true, c);
                            break;
                        }
                    }
                }

            }
            positionScan.close();
        }
        return null;
    }

    private void genPositions() {
        //Create a position object for each co-ordinate on the board. These are stored in a map as a field of the game class.
        for (int x = 1; x <= 24; x++) {
            for (int y = 1; y <= 25; y++) {
                String key = x + ", " + y;
                Room noRoom = new Room("No Room"); //there are no rooms yet so I'll just use this as filler for the room parameter
                Position newPosition = new Position(noRoom, true, x, y);
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

        System.out.println("first dice throw: " + firstResult);
        System.out.println("second dice throw: " + secondResult);
        return firstResult + secondResult;
    }

    // line 64 "model.ump"
    public void dealCards() {
        //Add cards from the deck list to a stack, then deal them to each player until the stack is empty
        Stack<Card> toBeDealt = new Stack<>();
        for (Card c : this.deck) {
            toBeDealt.push(c);
        }

        while (!toBeDealt.isEmpty()) {
            for (Player p : this.players) {
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


}