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


    private List<WeaponCard> weaponCards;
    private List<RoomCard> roomCards;
    private List<CharacterCard> characterCards;

    //Game Attributes
    private Board board;
    private Player currentPlayer;
    private Scenario murderScenario;  // Random Murder Scenario that players must find

    // check if the game has been won yet
    //        - still playing: true
    //        - game has ended: false
    boolean gameRunning = true;

    boolean gameWon = false;

    int movesRemaining = -1;


    /**
     * MAIN METHOD
     * Method to initialise the rest of the program. Shows a welcome message then generates a new game object
     *
     * @param args arguments
     * @author Vaibhav
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
     * @author Cameron, Vaibhav
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
                        " k K K K K k _ _ b B B B B B B b _ _ <C c C C c c " + "\n" +
                        " k k K K K k _ _ <B B B B B B B >B _ _ _ c c c c x " + "\n" +
                        " x k k k vK k _ _ b B B B B B B b _ _ _ _ _ _ _ 5 " + "\n" +
                        " _ _ _ _ _ _ _ _ b vB b b b b vB b _ _ _ _ _ _ _ x " + "\n" +
                        " x _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ i i i i i i " + "\n" +
                        " d d d d d _ _ _ _ _ _ _ _ _ _ _ _ _ <I I I I I i " + "\n" +
                        " d D D D d d d d _ _ x x x x x _ _ _ i I I I I i " + "\n" +
                        " d D D D D D D d _ _ x x x x x _ _ _ i I I I I i " + "\n" +
                        " d D D D D D D >D _ _ x x x x x _ _ _ i i i i vI i " + "\n" +
                        " d D D D D D D d _ _ x x x x x _ _ _ _ _ _ _ _ x " + "\n" +
                        " d D D D D D D d _ _ x x x x x _ _ _ l l ^L l l x " + "\n" +
                        " d d d d d d vD d _ _ x x x x x _ _ l l L L L l l " + "\n" +
                        " x _ _ _ _ _ _ _ _ _ x x x x x _ _ <L L L L L L l " + "\n" +
                        " 2 _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ l l L L L l l " + "\n" +
                        " x _ _ _ _ _ _ _ _ h h ^H ^H h h _ _ _ l l l l l x " + "\n" +
                        " o o o o o o ^O _ _ h H H H H h _ _ _ _ _ _ _ _ 6 " + "\n" +
                        " o O O O O O o _ _ h H H H H >H _ _ _ _ _ _ _ _ x " + "\n" +
                        " o O O O O O o _ _ h H H H H h _ _ ^Y y y y y y y " + "\n" +
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



    public void gameWon() {
        System.out.println("game has been won!");
    }


    /**
     * MAIN GAME LOOP
     * This method contains the main game logic. After the game as been setup in the game constructor, this method then
     * loops through, carrying out the game functions
     *
     * @author Vaibhav
     */
    public void mainGameLoop() throws InterruptedException {

        System.out.println("\n\tMurder Scenario (SECRET, DO NOT LOOK!)");
        System.out.println("\t\t[Character] " + murderScenario.getMurderer().getCharacterName() +
                "\n\t\t[Room] " + murderScenario.getRoomCard().getRoomName() +
                "\n\t\t[Weapon] " + murderScenario.getWeapon().getWeaponName());

        System.out.println("\nPress ENTER to start game:");
        Scanner start = new Scanner(System.in);
        start.nextLine();

        System.out.println("Starting game...");
        Thread.sleep(1000);


        while (gameRunning) {
            for (Player p : players) {
                movesRemaining = -1;

                if (gameWon) {
                    gameWon();
                    return;
                }

                System.out.println("\n------------------------------------------------------------------------");
                System.out.println("\n" + this.board + "\n");
                System.out.println("**************************************************");
                System.out.println("Current Player: " + p.getCharacter().getCharacterName() + " (" + p.getCharacter().getCharacterBoardChar() + " on board)");
                currentPlayer = p;
                System.out.println("**************************************************");
                movesRemaining = rollDice();
                System.out.println("Result: " + movesRemaining);
                System.out.println("**************************************************");

                while (movesRemaining > 0) {
                    if (p.getCurrentPosition().getRoom() != null) {
                        System.out.println("Currently in " + p.getCurrentPosition().getRoom());
                        System.out.println("**************************************************");
                    }
                    System.out.println("Please enter a move command or type \"finish\" to complete move (" + movesRemaining + " tiles remaining):");
                    Move move = movementInput(movesRemaining);
                    if(move!=null) {
                        Board newBoard = this.board.apply(p, move);
                        if (newBoard != null) {
                            this.board = newBoard;
                            movesRemaining = movesRemaining - move.getSpaces();
                            System.out.println(board.toString());
                        }
                    } else {
                        break;
                    }
                }

                System.out.println("\t\t________________________________");
                System.out.println("\t\tCurrent Players Hand:");
                System.out.println("\t\t________________________________");
                for (Card c : p.getHand()) {
                    if (c instanceof CharacterCard) {
                        System.out.println("\t\t[Character] " + c.toString());
                    } else if (c instanceof WeaponCard) {
                        System.out.println("\t\t[Weapon] " + c.toString());
                    } else if (c instanceof RoomCard) {
                        System.out.println("\t\t[Room] " + c.toString());
                    }
                }
                System.out.println("\t\t________________________________");


                System.out.println("Would you like to make a suggestion, accusation or pass?");
                System.out.println("Available commands - [suggestion][accusation][pass]:");
                String answer = accSuggInput();
                if (answer.equalsIgnoreCase("a") || answer.equalsIgnoreCase("accusation")) {
                    gameRunning = accusation(p);
                } else if (answer.equalsIgnoreCase("s") || answer.equalsIgnoreCase("suggestion")) {
                    suggestion(p);
                }

                System.out.println("[Hit Enter to move to the next player]");
                Scanner wait = new Scanner(System.in);
                wait.nextLine();

            }
        }
    }

    /**
     * Asks current player to perform an action
     * Returns a move to apply to the board
     *
     * @return Move
     * @author Cameron Li
     */
    public Move movementInput(int movesRemaining) {
        Move.Direction direction = null;
        int spaces = 0;
        Scanner inputScan = new Scanner(System.in);
        boolean valid = false;
        boolean tooFar = true;
        while (!valid) {
            String command = inputScan.nextLine();

            try {
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
                } else if(command.equals("finish")){
                    return null;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a correct number of spaces");
            }
            if (direction != null && spaces > 0 && spaces <= movesRemaining) {
                tooFar = false;
                valid = true;
            }

            if (!valid) {
                if (tooFar && spaces > movesRemaining) {
                    System.out.println("Insufficient amount of moves left");
                } else {
                    System.out.println("Please enter a proper command");
                    System.out.println("Movement is \"direction-spaces\" - e.g. up-4");
                }
            }
        }


        return new Move(direction, spaces);
    }

    /**
     * Handles player accusations.
     * Returns true if they are correct and false if they are incorrect
     *
     * @return Boolean
     * @author Baxter Kirikiri
     */
    private boolean accusation(Player p) {
        if (!p.getCanAccuse()) {
            System.out.println("You've already made a failed accusation. You can no longer accuse this game");
            return true;
        }
        ArrayList<String> accusation = new ArrayList<>();

        /**System.out.println("\t" + murderScenario.getRoomCard().toString()); // show the scenario for testing purposes
         System.out.println("\t" + murderScenario.getWeapon().toString());
         System.out.println("\t" + murderScenario.getMurderer().toString());**/

        System.out.println("Please enter a room: ");
        accusation = addCardToPlay(accusation);
        System.out.println("Please enter a weapon: ");
        accusation = addCardToPlay(accusation);
        System.out.println("Please enter a character: ");
        accusation = addCardToPlay(accusation);

        int correct = 0;
        for (String s : accusation) {
            if (murderScenario.getRoomCard().toString().equals(s) || murderScenario.getWeapon().toString().equals(s) || murderScenario.getMurderer().toString().equals(s)) {
                correct++;
            }
        }

        if (correct == 3) {
            System.out.println(p.getCharacter().getCharacterName() + " has won!!");
            return false; //gamerunning
        } else {
            System.out.println(p.getCharacter().getCharacterName() + " was incorrect in their accusation. They can now no longer accuse.");
            p.setCanAccuse(false);
            return true; //gamerunning
        }
    }

    /**
     * Handles player suggestions.
     *
     * @author Baxter Kirikiri, Vaibhav
     */
    private void suggestion(Player p) {
        // additions by Vaibhav
        System.out.println("[Checking to see if player is currently in a room]");
        if (p.getCurrentPosition().getRoom() == null) {
            System.out.println("[Current player |" + p.getCharacter().getCharacterName() + "| is not currently in a room, suggestion can NOT continue]");
            return;
        }

        Room currentRoom = p.getCurrentPosition().getRoom();
        System.out.println("[Current player |" + p.getCharacter().getCharacterName() + "| is in room |" + p.getCurrentPosition().getRoom().toString() + "|]\n This room will be used in the suggestion");
        System.out.println("Please enter a weapon: ");
        WeaponCard weapon = findCard(WeaponCard.class, weaponCards);
        System.out.println("Please enter a character: ");
        CharacterCard character = findCard(CharacterCard.class, characterCards);

        System.out.println("Current Suggestion:");
        for (Player findP : players) {
            if (findP.getCharacter().equals(character)) {
                if (!findP.getCurrentPosition().getRoom().equals(currentRoom)) {
                    Board newBoard = board.teleportPlayer(findP, currentRoom);
                    if (newBoard != null) {
                        board = newBoard;
                    } else {
                        System.out.println("An error occurred while teleporting the player");
                    }
                }
            }
        }

        Stack<Player> refuters = new Stack<>();
        for (Player addToStack : players) {
            if (!addToStack.equals(p)) {
                refuters.add(addToStack);
            }
        }

        boolean refuted = false;
        while (!refuters.isEmpty()) {
            Player currentTurn = refuters.pop();
            System.out.println(currentTurn.getCharacter().getCharacterName() + "'s turn to refute"); //TODO: Update these messages so they follow convention
            System.out.println(currentTurn.getCharacter().getCharacterName() + "'s hand: ");
            Card[] refuteCards = currentTurn.getHand();
            for (Card c : refuteCards) {
                System.out.println("\t" + c.toString());
            }
            System.out.println("Refute this suggestion with a card from your hand: ");
            String refutation = accSuggInput();
            boolean inHand = false;
            for (Card c : refuteCards) {
                if (c.toString().equals(refutation)) {
                    inHand = true;
                }
            }
            if (suggestion.contains(refutation) && inHand) {
                System.out.println(p.getCharacter().getCharacterName() + "'s suggestion was refuted!");
                refuted = true;
                break;
            } else {
                System.out.println("Invalid refutation!");
            }
        }

        if (!refuted) {
            System.out.println(p.getCharacter().getCharacterName() + "'s turn");
            System.out.println("No one could refute your suggestion! Would you like to make an accusation? (y/n)");
            if (accSuggInput().equals("y")) {
                this.gameWon = accusation(p); //TODO: Allow this to win the game (check resolved)
            }
        }

    }

    /**
     * Asks current player if they would like to accuse or suggest
     * Returns their response
     *
     * @return String
     * @author Baxter Kirikiri
     */
    private String accSuggInput() {
        String answer = "";
        Scanner inputScan = new Scanner(System.in);
        String input = inputScan.nextLine();
        try {
            answer = input;
        } catch (Exception e) {
            System.out.println("Please enter 'a', 's', or 'n'");
        }

        return answer;
    }

    /**
     * Asks a player to enter the name of a card (for their accusation or suggestion)
     * Returns an list of card names
     *
     * @return ArrayList<String>
     * @author Baxter Kirikiri
     */
    private ArrayList<String> addCardToPlay(ArrayList<String> play) {
        String cardName = "";
        Scanner inputScan = new Scanner(System.in);
        try {
            cardName = inputScan.nextLine();
        } catch (Exception e) {
            System.out.println("Please enter a valid card name");
        }

        play.add(cardName);
        return play;
    }

    /**
     * Finds and matches a String with a Card in player hand
     * If not found, returns null - indicated not in hand or doesn't exist
     *
     * @param cards
     * @return
     * @author Cameron Li
     */
    private <C extends Card> C findCard(Class cardType, List<C> cards) {
        String cardName = "";

        Scanner inputScan = new Scanner(System.in);
        try {
            cardName = inputScan.nextLine();
        } catch (Exception e) {
            System.out.println("Please enter a valid card name");
        }

        Card foundCard = null;

        for (int c = 0; c < cards.size(); c++) {
            if (cards.get(c).toString().equals(cardName)) {
                foundCard = cards.get(c);
            }
        }

        if (!foundCard.getClass().equals(cardType)) {
            System.out.println("Incorrect input, suggestion requires a: " + cardType.toString());
            return null;
        }

        if (cardName.equals("")) {
            System.out.println("Incorrect card name");
        }
        return (C) foundCard;
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

        initDeck(numPlayers);
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
    private void initDeck(int numPlayers) {
        // Adding cards
        deck = new ArrayList<>();

        // Weapons
        String[] wepNames = {"Candlestick", "Dagger", "Lead Pipe", "Revolver", "Rope", "Spanner"};
        weaponCards = new ArrayList<>();
        for (String w : wepNames) {
            WeaponCard weapon = new WeaponCard(w);
            weaponCards.add(weapon);
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

        // Characters
        String[] characterNames = {
                "Ms. Scarlett", "Col. Mustard", "Mrs. White", "Mr. Green", "Mrs. Peacock", "Prof. Plum"};
        characterCards = new ArrayList<>();
        for (int c = 0; c < numPlayers; c++) { // Only create cards where there are players
            CharacterCard character = new CharacterCard(characterNames[c]);
            characterCards.add(character);
            deck.add(character);
        }


        Collections.shuffle(deck);
        // Murder Scenario of Random Cards
        murderScenario = new Scenario(weaponCards.get(new Random().nextInt(wepNames.length - 1) + 1),
                roomCards.get(new Random().nextInt(roomNames.length - 1) + 1),
                characterCards.get(new Random().nextInt(numPlayers)));
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
                            newPosition = new Position(x, y, true, true, null, r);
                            break;
                        } else if (positionName.equals("^" + r.getRoomChar())) { // Up door
                            newPosition = new Position(x, y, true, true, Move.Direction.UP, r);
                            break;
                        } else if (positionName.equals(">" + r.getRoomChar())) { // Right door
                            newPosition = new Position(x, y, true, true, Move.Direction.RIGHT, r);
                            break;
                        } else if (positionName.equals("v" + r.getRoomChar())) { // Down door
                            newPosition = new Position(x, y, true, true, Move.Direction.DOWN, r);
                            break;
                        } else if (positionName.equals("<" + r.getRoomChar())) {
                            newPosition = new Position(x, y, true, true, Move.Direction.LEFT, r);
                            break;
                        } else if (positionName.equals(r.getRoomChar().toLowerCase())) { // Is this an outer room position?
                            newPosition = new Position(x, y, true, false, null, r);
                            break;
                        }
                    }
                }

                if (newPosition == null) { // Add in remaining Character Positions
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

                if (newPosition == null) { // Position where player was not added due to numPlayers < maxPlayers
                    newPosition = new Position(x, y, true);
                }
                board.addPosition(y, x, newPosition); // Add found position
            }
            positionScan.close();
        }
        layoutScan.close();
        this.board = board; // Set board
    }


    /**
     * Roll two dice and then return the overall number
     *
     * @return cumulative dice throw result
     */
    // line 61 "model.ump"
    public int rollDice() {
        Scanner diceRollScanner = new Scanner(System.in);
        String readString = "";
        while (!readString.equalsIgnoreCase("roll")) {
            System.out.println("Type \"roll\" to roll dice for " + currentPlayer.getCharacter().toString());
            readString = diceRollScanner.nextLine();
        }


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


}