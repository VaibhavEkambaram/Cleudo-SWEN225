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

    private List<Room> rooms;

    private List<CharacterCard> characterCards;

    //Game Attributes
    private Board board;
    private Player currentPlayer;
    private Scenario murderScenario;  // Random Murder Scenario that players must find

    // check if the game has been won yet
    //        - still playing: true
    //        - game has ended: false
    boolean gameRunning = true;

    int movesRemaining = -1;


    /**
     * MAIN METHOD
     * Method to initialise the rest of the program. Shows a welcome message then generates a new game object
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        System.out.println("------------------------------------------------------------------------\n" +
                "\t\t\t\t\t\t\tCluedo!\n" +
                "------------------------------------------------------------------------\n" +
                "\tSWEN225 Assignment 1\n" +
                "\tA group project by:\n" +
                "\t\tCameron Li\tVaibhav Ekambaram\tBaxter Kirikiri\n" +
                "------------------------------------------------------------------------\n");
        new Game();
    }


    /**
     * GAME OBJECT CONSTRUCTOR
     * Primary game object springboard for all other methods of the game
     *
     * <p>
     * ----------------------------------------------------------------------------------------------------
     * Board Representation Guide
     * ----------------------------------------------------------------------------------------------------
     * ** Rooms **              ** Players **        ** Board **                    ** Weapons **
     * C - Conservatory         1 - Mrs. White       x - Null Area (Off limits)     ? - Candlestick
     * B - Ball Room            2 - Mr. Green        # - Room Wall                  ! - Dagger
     * K - Kitchen              3 - Mrs. Peacock                                    $ - Lead Pipe
     * I - Billiard Room        4 - Prof. Plum                                      % - Revolver
     * D - Dining Room          5 - Ms. Scarlett                                    @ - Rope
     * L - Library              6 - Col. Mustard                                    & - Spanner
     * O - Lounge
     * H - Hall
     * Y - Study
     * ----------------------------------------------------------------------------------------------------
     */
    public Game() {
        String boardLayout =
                " x x x x x x x x x 3 x x x x 4 x x x x x x x x x " + "\n" +
                        " k k k k k k x _ _ _ b b b b _ _ _ x c c c c c c " + "\n" +
                        " k K K K K k _ _ b b b B B b b b _ _ c C C C C c " + "\n" +
                        " k K K K K k _ _ b B B B B B B b _ _ c C C C C c " + "\n" +
                        " k K K K K k _ _ b B B B B B B b _ _ dC c C C c c " + "\n" +
                        " k k K K K k _ _ dB B B B B B B dB _ _ _ c c c c x " + "\n" +
                        " x k k k dK k _ _ b B B B B B B b _ _ _ _ _ _ _ 5 " + "\n" +
                        " _ _ _ _ _ _ _ _ b dB b b b b dB b _ _ _ _ _ _ _ x " + "\n" +
                        " x _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ i i i i i i " + "\n" +
                        " d d d d d _ _ _ _ _ _ _ _ _ _ _ _ _ dI I I I I i " + "\n" +
                        " d D D D d d d d _ _ x x x x x _ _ _ i I I I I i " + "\n" +
                        " d D D D D D D d _ _ x x x x x _ _ _ i I I I I i " + "\n" +
                        " d D D D D D D dD _ _ x x x x x _ _ _ i i i i dI i " + "\n" +
                        " d D D D D D D d _ _ x x x x x _ _ _ _ _ _ _ _ x " + "\n" +
                        " d D D D D D D d _ _ x x x x x _ _ _ l l dL l l x " + "\n" +
                        " d d d d d d dD d _ _ x x x x x _ _ l l L L L l l " + "\n" +
                        " x _ _ _ _ _ _ _ _ _ x x x x x _ _ dL L L L L L l " + "\n" +
                        " 2 _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ l l L L L l l " + "\n" +
                        " x _ _ _ _ _ _ _ _ h h dH dH h h _ _ _ l l l l l x " + "\n" +
                        " o o o o o o dO _ _ h H H H H h _ _ _ _ _ _ _ _ 6 " + "\n" +
                        " o O O O O O o _ _ h H H H H dH _ _ _ _ _ _ _ _ x " + "\n" +
                        " o O O O O O o _ _ h H H H H h _ _ dY y y y y y y " + "\n" +
                        " o O O O O O o _ _ h H H H H h _ _ y Y Y Y Y Y y " + "\n" +
                        " o O O O O o o _ _ h H H H H h _ _ y y Y Y Y Y y " + "\n" +
                        " o o o o o o x 1 x h h h h h h x _ x y y y y y y \n";


        initGame(); // initialize cards and players
        initBoard(boardLayout); // generate board
        try {
            mainGameLoop(); // main game logic loop
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * MAIN GAME LOOP
     * This method contains the main game logic. After the game as been setup in the game constructor, this method then
     * loops through, carrying out the game functions
     */
    public void mainGameLoop() throws InterruptedException {
        System.out.println("\nMurder Scenario: " + murderScenario.toString() + " (SECRET DO NOT LOOK!)");
        System.out.println("Starting game...");
        Thread.sleep(1000);


        while (gameRunning) {
            players.forEach(p -> {

                movesRemaining = -1;

                System.out.println("\n------------------------------------------------------------------------");
                System.out.println("\n" + this.board + "\n");
                System.out.println("**************************************************");
                System.out.println("Current Player: " + p.getCharacter().getCharacterName() + " (" + p.getCharacter().getCharacterBoardChar() + " on board)");
                System.out.println("**************************************************");
                movesRemaining = rollDice();
                System.out.println("Result: " + movesRemaining);
                System.out.println("**************************************************");


                //  System.out.println("Select position to move to:");
                //System.out.println("Current Players Hand:");
                // for (Card c : p.getHand()) {
                //    System.out.println("\t" + c.toString());
                //}


                while (movesRemaining > 0) {
                    System.out.println("Please enter a move command (" + movesRemaining + " tiles remaining):");
                    Move move = input(movesRemaining);
                    Board newBoard = this.board.apply(p, move);
                    if (newBoard != null) {
                        this.board = newBoard;
                        movesRemaining = movesRemaining - move.getSpaces();
                        System.out.println(board.toString());
                    }

                }


            });

            // TODO: Temporary break point for testing purposes
            break;
        }
    }

    /**
     * Asks current player to perform an action
     * Returns a move to apply to the board
     *
     * @return Move
     * @author Cameron Li
     */
    public Move input(int movesRemaining) {
        Move.Direction direction = null;
        int spaces = 0;
        Scanner inputScan = new Scanner(System.in);
        boolean valid = false;
        while (!valid) {
            String command = inputScan.nextLine();

            try {
                //   if (command.equals("accusation")) {
                //     System.out.println("accusation");
                //  } else if (command.equals("suggestion")) {
                //     System.out.println("suggestion");
                //
                // } else
                if (command.length() >= 4 && command.startsWith("up-")) {
                    direction = Move.Direction.UP;
                    spaces = Integer.parseInt(command.substring(3));
                } else if (command.length() >= 6 && command.startsWith("left-")) {
                    direction = Move.Direction.LEFT;
                    spaces = Integer.parseInt(command.substring(5));
                } else if (command.length() >= 7 && command.startsWith("right-")) {
                    direction = Move.Direction.RIGHT;
                    spaces = Integer.parseInt(command.substring(6));
                } else if (command.length() >= 6 && command.startsWith("down-")) {
                    direction = Move.Direction.DOWN;
                    spaces = Integer.parseInt(command.substring(5));
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a correct number of spaces");
            }
            if (direction != null && spaces != 0 && spaces <= movesRemaining) {
                valid = true;
            }

            if (!valid) {
                System.out.println("Please enter a proper command");
                System.out.println("Movement is \"direction-spaces\" - e.g. up-4");
            }
        }

        return new Move(direction, spaces);
    }

    /**
     * Initialise the game
     * Ask user for number of players
     * Initialise Deck
     *
     * @author Cameron Li
     */
    public void initGame() {
        System.out.println("**Game Startup Parameters**\nHow many players wish to participate? (3 - 6):");
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
            if (isNumber && numPlayers < 3 || numPlayers > 6) {
                System.out.println("Please enter a number between 3 and 6");
            }
        }

        initDeck();
        players = new ArrayList<>();
        for (int n = 0; n < numPlayers; n++) {
            players.add(new Player(characterCards.get(n)));
        }
        this.dealCards();
    }

    /**
     * Create the deck and then shuffle
     * Generate initial murder scenario
     *
     * @author Cameron Li
     */
    private void initDeck() {
        // Adding cards
        deck = new ArrayList<>();

        // Weapons
        String[] wepNames = {"Candlestick", "Dagger", "Lead Pipe", "Revolver", "Rope", "Spanner"};
        List<WeaponCard> weaponCards = new ArrayList<>();
        for (String w : wepNames) {
            WeaponCard weapon = new WeaponCard(w);
            weaponCards.add(weapon);
            deck.add(weapon);
        }

        // Rooms
        String[] roomNames = {"Kitchen", "Dining Room", "Lounge", "Hall", "Study",
                "Library", "Billiard Room", "Conservatory", "Ball Room"};
        rooms = new ArrayList<>();
        List<RoomCard> roomCards = new ArrayList<>();
        for (String r : roomNames) {
            Room newRoom = new Room(r);
            rooms.add(newRoom);
            RoomCard newRoomCard = new RoomCard(r, newRoom);
            roomCards.add(newRoomCard);
            deck.add(newRoomCard);
        }

        String[] characterNames = {
                "Ms. Scarlett", "Col. Mustard", "Mrs. White", "Mr. Green", "Mrs. Peacock", "Prof. Plum"};
        characterCards = new ArrayList<>();
        for (String c : characterNames) {
            CharacterCard character = new CharacterCard(c);
            characterCards.add(character);
            deck.add(character);
        }


        Collections.shuffle(deck);
        // Murder Scenario of Random Cards
        murderScenario = new Scenario(weaponCards.get(new Random().nextInt(wepNames.length - 1) + 1),
                roomCards.get(new Random().nextInt(roomNames.length - 1) + 1),
                characterCards.get(new Random().nextInt(characterNames.length - 1) + 1));
        System.out.println("Generated Scenario");
    }

    /**
     * Load and create the Cluedo board
     * "x" = Forbidden Position
     * "_" = Standard Position
     * number = Player starting Position
     * uppercase letter = Inner Room Position
     * lowercase letter = Outer Room Position
     * "d" + letter = Room Door Position
     *
     * @param boardLayout layout of board defined in constructor
     * @author Cameron Li
     */
    private void initBoard(String boardLayout) {
        Board board = new Board();
        Scanner layoutScan = new Scanner(boardLayout);
        int y = -1;
        // Scan row (y)
        while (layoutScan.hasNextLine()) {
            String scanLine = layoutScan.nextLine(); // Scanned row (y)
            y++;
            int x = -1;
            Scanner positionScan = new Scanner(scanLine); // Scan Column (x)
            while (positionScan.hasNext()) {
                String positionName = positionScan.next(); // Scanned Column (x)
                x++; // Increment row
                Position newPosition = null;
                if (positionName.equals("x")) { // Check for "x", a forbidden position
                    newPosition = new Position(x, y, false);
                }
                if (positionName.equals("_")) { // Check for "_" the basic moveable position
                    newPosition = new Position(x, y, true);
                }

                if (newPosition == null) { // If still haven't found anything
                    for (Room r : rooms) { // Check for a room
                        if (positionName.equals(r.getRoomChar())) { // Is this an inner room position?
                            newPosition = new Position(x, y, true, true, false, r);
                            break;
                        } else if (positionName.equals("d" + r.getRoomChar())) { // Is this a room door?
                            newPosition = new Position(x, y, true, true, true, r);
                            break;
                        } else if (positionName.equals(r.getRoomChar().toLowerCase())) { // Is this an outer room position?
                            newPosition = new Position(x, y, true, false, false, r);
                            break;
                        }
                    }
                }

                if (newPosition == null) { // If still haven't found anything
                    for (CharacterCard c : characterCards) {
                        if (positionName.equals(c.getCharacterBoardChar())) { // Check if position is a character
                            for (Player p : players) { // Make sure that a player is playing the character
                                if (p.getCharacter().equals(c)) { // Case player for character exists, create Character position
                                    newPosition = new Position(x, y, true, c);
                                    p.setCurrentPosition(newPosition);
                                    break;
                                } else { // Else, is a basic moveable position "_"
                                    newPosition = new Position(x, y, true);
                                }
                            }
                            break;
                        }
                    }
                }
                board.addPosition(y, x, newPosition); // Add found position
            }
            positionScan.close();
        }
        layoutScan.close();
        this.board = board; // Set board
    }

    //------------------------
    // INTERFACE
    //------------------------


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


    /**
     * Roll two dice and then return the overall number
     *
     * @return cumulative dice throw result
     */
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
        this.deck.forEach(toBeDealt::push);

        while (!toBeDealt.isEmpty()) {
            for (Player p : this.players) {
                if (toBeDealt.isEmpty()) { // Make sure not null pointer exception
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