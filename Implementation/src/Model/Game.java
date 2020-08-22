package Model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5071.d9da8f6cd modeling language!*/

import View.AccusationMenu;

import java.util.*;
import java.util.stream.IntStream;

// line 50 "model.ump"
// line 166 "model.ump"
// line 224 "model.ump"
public class Game {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    private States gameState;
    private subStates subState;

    private void initGameState() {
        this.gameState = States.IDLE;
    }

    /**
     * Check if States are matching within their appropiate substates
     * If incorrect, throw an error with respective states
     */
    private void checkGameState() {
        if (gameState.equals(States.IDLE)) {
            if (!subState.equals(subStates.INIT)) {
                throw new Error(gameState.ordinal() + " State but incorrect subState: " + subState.ordinal());
            }
        } else if (gameState.equals(States.RUNNING)) {
            if (subState.equals(subStates.INIT)) {
                throw new Error(gameState.ordinal() + " State but incorrect subState: " + subState.ordinal());
            }
        }
    }

    private List<Card> deck;
    private List<Player> players;
    private List<Room> rooms;

    private List<WeaponCard> weaponCards;
    private List<RoomCard> roomCards;
    private List<CharacterCard> characterCards;

    private final Map<String, WeaponCard> weaponCardsMap = new HashMap<>();
    private final Map<String, RoomCard> roomCardsMap = new HashMap<>();
    private final Map<String, CharacterCard> characterCardsMap = new HashMap<>();


    //Model.Game Attributes
    private Board board;
    private Player currentPlayer;
    private Scenario murderScenario;  // Murder Model.Scenario that players must find

    // check if the game has been won yet
    //        - still playing: true
    //        - game has ended: false
    boolean gameRunning = true;

    // track number of moves for current player
    int movesRemaining = -1;
    int numPlayers;
    View.Table gui;

    private final String[] weaponNames = {"Candlestick", "Dagger", "Lead Pipe", "Revolver", "Rope", "Spanner"};
    private final String[] roomNames = {"Kitchen", "Dining Model.Room", "Lounge", "Hall", "Study", "Library", "Billiard Model.Room", "Conservatory", "Ball Model.Room"};
    private final String[] characterNames = {"Miss Scarlett", "Col. Mustard", "Mrs. White", "Mr. Green", "Mrs. Peacock", "Prof. Plum"};


    /**
     * GAME OBJECT CONSTRUCTOR
     * Primary game object springboard for all other methods of the game
     *
     * @author Cameron Li, Vaibhav Ekambaram
     *
     * <p>
     * ----------------------------------------------------------------------------------------------------
     * Model.Board Representation Guide
     * ----------------------------------------------------------------------------------------------------
     * ** Rooms **              ** Players **        ** Model.Board **                    ** Weapons **
     * C - Conservatory         1 - Mrs. White       x - Null Area (Off limits)     ? - Candlestick
     * B - Ball Model.Room            2 - Mr. Green        # - Model.Room Wall                  ! - Dagger
     * K - Kitchen              3 - Mrs. Peacock                                    $ - Lead Pipe
     * I - Billiard Model.Room        4 - Prof. Plum                                      % - Revolver
     * D - Dining Model.Room          5 - Ms. Scarlett                                    @ - Rope
     * L - Library              6 - Col. Mustard                                    & - Spanner
     * O - Lounge
     * H - Hall
     * Y - Study
     * ----------------------------------------------------------------------------------------------------
     */
    public Game() {
        String boardLayout =
                " x x x x x x x x x 3 x x x x 4 x x x x x x x x x \n" +
                        " k k k k k k x _ _ _ b b b b _ _ _ x c c c c c c \n" +
                        " k K K K K k _ _ b b b B B b b b _ _ c C C C C c \n" +
                        " k K K K K k _ _ b B B B B B B b _ _ c C C C C c \n" +
                        " k K K K K k _ _ b B B B B B B b _ _ <C c C C c c \n" +
                        " k k K K K k _ _ <B B B B B B B >B _ _ _ c c c c x \n" +
                        " x k k k vK k _ _ b B B B B B B b _ _ _ _ _ _ _ 5 \n" +
                        " _ _ _ _ _ _ _ _ b vB b b b b vB b _ _ _ _ _ _ _ x \n" +
                        " x _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ i i i i i i \n" +
                        " d d d d d _ _ _ _ _ _ _ _ _ _ _ _ _ <I I I I I i \n" +
                        " d D D D d d d d _ _ x x x x x _ _ _ i I I I I i \n" +
                        " d D D D D D D d _ _ x x x x x _ _ _ i I I I I i \n" +
                        " d D D D D D D >D _ _ x x x x x _ _ _ i i i i vI i \n" +
                        " d D D D D D D d _ _ x x x x x _ _ _ _ _ _ _ _ x \n" +
                        " d D D D D D D d _ _ x x x x x _ _ _ l l ^L l l x \n" +
                        " d d d d d d vD d _ _ x x x x x _ _ l l L L L l l \n" +
                        " x _ _ _ _ _ _ _ _ _ x x x x x _ _ <L L L L L L l \n" +
                        " 2 _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ l l L L L l l \n" +
                        " x _ _ _ _ _ _ _ _ h h ^H ^H h h _ _ _ l l l l l x \n" +
                        " o o o o o o ^O _ _ h H H H H h _ _ _ _ _ _ _ _ 6 \n" +
                        " o O O O O O o _ _ h H H H H >H _ _ _ _ _ _ _ _ x \n" +
                        " o O O O O O o _ _ h H H H H h _ _ ^Y y y y y y y \n" +
                        " o O O O O O o _ _ h H H H H h _ _ y Y Y Y Y Y y \n" +
                        " o O O O O o o _ _ h H H H H h _ _ y y Y Y Y Y y \n" +
                        " o o o o o o x 1 x h h h h h h x _ x y y y y y y \n";
        //this.numPlayers = numPlayers;


        initGame(); // initialize cards and players
        initBoard(boardLayout); // generate board

        View.Table gui = new View.Table();
        gui.setPlayerCount();
        new View.SetupPlayers().setPlayers(characterNames);




        mainGameLoop(); // main game logic loop
    }


    // Initial Model.Game Instance Creation

    /**
     * Initialise the game
     * Ask user for number of players
     * Initialise Deck
     *
     * @author Cameron Li
     */
    public void initGame() {
        System.out.println("**Model.Game Startup Parameters**\nHow many players wish to participate? (3 - 6):");
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
        int bound = numPlayers;
        for (int n = 0; n < bound; n++) {
            players.add(new Player(characterCards.get(n)));
        }

        this.dealCards();
    }

    /**
     * Deal Cards
     * Add cards from the deck list to a stack, then deal them to each player until the stack is empty
     *
     * @author Cameron Li
     */
    public void dealCards() {
        Stack<Card> toBeDealt = new Stack<>();
        this.deck.forEach(toBeDealt::push);

        while (!toBeDealt.isEmpty()) {
            for (Player p : this.players) {
                // Make sure not null pointer exception
                if (toBeDealt.isEmpty()) break;
                p.addHand(toBeDealt.pop());
            }
        }
        System.out.println("Cards Dealt");
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
        weaponCards = new ArrayList<>();
        for (String weaponName : weaponNames) {
            WeaponCard weapon = new WeaponCard(weaponName);
            weaponCards.add(weapon);
            weaponCardsMap.put(weaponName, weapon);
            deck.add(weapon);
        }

        // Rooms
        rooms = new ArrayList<>();
        roomCards = new ArrayList<>();
        for (String r : roomNames) {
            Room newRoom = new Room(r);
            rooms.add(newRoom);
            RoomCard newRoomCard = new RoomCard(r, newRoom);
            roomCards.add(newRoomCard);
            roomCardsMap.put(r, newRoomCard);
            deck.add(newRoomCard);
        }

        // Characters
        characterCards = new ArrayList<>();
        // Only create cards where there are players
        for (String c : characterNames) {
            CharacterCard character = new CharacterCard(c);
            characterCards.add(character);
            characterCardsMap.put(c, character);
            deck.add(character);
        }

        Collections.shuffle(deck);
        // Murder Model.Scenario of Random Cards
        WeaponCard murderWeapon = weaponCards.get(new Random().nextInt(weaponNames.length - 1) + 1);
        RoomCard murderRoom = roomCards.get(new Random().nextInt(roomNames.length - 1) + 1);
        CharacterCard murderer = characterCards.get(new Random().nextInt(characterNames.length - 1) + 1);
        murderScenario = new Scenario(murderWeapon, murderRoom, murderer);
        deck.remove(murderWeapon);
        deck.remove(murderRoom);
        deck.remove(murderer);
        System.out.println("Generated Model.Scenario");
    }


    /**
     * Load and create the Cluedo board
     * "x" = Forbidden Model.Position
     * "_" = Standard Model.Position
     * number = Model.Player starting Model.Position
     * uppercase letter = Inner Model.Room Model.Position
     * lowercase letter = Outer Model.Room Model.Position
     * "d" + letter = Model.Room Door Model.Position
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
                if (positionName.equals("_")) { // Check for "_" the basic movable position
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
                                } else { // Else, is a basic movable position "_"
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

    // Gameplay

    /**
     * MAIN GAME LOOP
     * This method contains the main game logic. After the game as been setup in the game constructor, this method then
     * loops through, carrying out the game functions
     *
     * @author Vaibhav Ekambaram
     */
    public void mainGameLoop() {
        System.out.println("\n\tMurder Model.Scenario (SECRET, DO NOT LOOK!)");
        System.out.println("\t\t[Character] " + murderScenario.getMurderer().getCharacterName() + "\n\t\t[Model.Room] " + murderScenario.getRoomCard().getRoomName() + "\n\t\t[Weapon] " + murderScenario.getWeapon().getWeaponName());
        System.out.println("Starting game...");

        while (gameRunning) {
            for (Player p : players) {
                movesRemaining = -1;

                System.out.println("\n------------------------------------------------------------------------\n" + this.board + "\n");
                System.out.println("**************************************************");
                System.out.println("Current Model.Player: " + p.getCharacter().getCharacterName() + " (" + p.getCharacter().getCharacterBoardChar() + " on board)");
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
                    if (move != null) {
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
                        System.out.println("\t\t[Model.Room] " + c.toString());
                    }
                }
                System.out.println("\t\t________________________________");


                System.out.println("Would you like to make a suggestion, accusation or pass?");
                System.out.println("Available commands - [suggestion][accusation][pass]:");
                String answer = suggestionValidateInput();
                if (answer.equalsIgnoreCase("a") || answer.equalsIgnoreCase("accusation")) {
                    int accuse = makeAccusation(p);
                    if (accuse == 1) {
                        gameRunning = false;
                        break;
                    }
                } else if (answer.equalsIgnoreCase("s") || answer.equalsIgnoreCase("suggestion")) {
                    makeSuggestion(p);
                }

                System.out.println("[Hit Enter to move to the next player]");
                Scanner wait = new Scanner(System.in);
                wait.nextLine();

            }
        }
    }


    /**
     * Roll two dice and then return the overall number
     *
     * @return Cumulative dice throw result
     * @author Vaibhav Ekambaram
     */
    public int rollDice() {
        Scanner diceRollScanner = new Scanner(System.in);
        String readString = "";
        while (!readString.equalsIgnoreCase("roll")) {
            System.out.println("Type 'roll' to roll dice for " + currentPlayer.getCharacter().toString());
            readString = diceRollScanner.nextLine();
        }

        // find a random number in the range of 0 to 5, then add 1 as an offset for 1 to 6
        int firstResult = new Random().nextInt(6) + 1;
        int secondResult = new Random().nextInt(6) + 1;

        System.out.println("first dice throw: " + firstResult);
        System.out.println("second dice throw: " + secondResult);
        return firstResult + secondResult;
    }


    /**
     * Asks current player to perform an action
     * Returns a move to apply to the board
     *
     * @return Model.Move
     * @author Cameron Li, Vaibhav Ekambaram
     */
    public Move movementInput(int movesRemaining) {
        Move.Direction direction = null;
        int spaces = 0;
        Scanner inputScan = new Scanner(System.in);
        boolean valid = false;
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
                } else if (command.equals("finish")) {
                    return null;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a correct number of spaces");
            }
            if (direction != null && spaces > 0 && spaces <= movesRemaining) {
                valid = true;
            }

            if (!valid) {
                if (spaces > movesRemaining) {
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
     * Handle Accusations
     *
     * @param p player
     * @return integer (-1 invalid, 0 failed, 1 successful)
     * @author Baxter Kirikiri, Vaibhav Ekambaram
     */
    private int makeAccusation(Player p) {
        new AccusationMenu().makeAccusation(characterNames,weaponNames,roomNames);
        if (!p.getCanAccuse()) {
            System.out.println("You have already made a failed accusation! Therefore, you can no longer make accusations during this game.");
            return -1;
        }

        RoomCard accusationRoomCard = null;
        CharacterCard accusationCharacterCard = null;
        WeaponCard accusationWeaponCard = null;

        // get accusation room
        System.out.println("Please enter an accusation room: ");
        while (accusationRoomCard == null) {
            String accusationRoomInput = new Scanner(System.in).nextLine();

            if (roomCardsMap.containsKey(accusationRoomInput)) {
                accusationRoomCard = roomCardsMap.get(accusationRoomInput);
            }

            if (accusationRoomCard == null) System.out.println("Model.Room not found! Please enter a valid room name:");
        }

        // get accusation character
        System.out.println("Please enter a character to accuse: ");
        while (accusationCharacterCard == null) {
            String accusationCharacterInput = new Scanner(System.in).nextLine();

            if (characterCardsMap.containsKey(accusationCharacterInput)) {
                accusationCharacterCard = characterCardsMap.get(accusationCharacterInput);
            }

            if (accusationCharacterCard == null)
                System.out.println("Character not found! Please enter a valid character name:");
        }

        // get accusation character
        System.out.println("Please enter an accusation murder weapon: ");
        while (accusationWeaponCard == null) {
            String accusationWeaponInput = new Scanner(System.in).nextLine();

            if (weaponCardsMap.containsKey(accusationWeaponInput)) {
                accusationWeaponCard = weaponCardsMap.get(accusationWeaponInput);
            }

            if (accusationWeaponCard == null)
                System.out.println("Weapon not found! Please enter a valid weapon name:");
        }

        // create scenario and compare to the original murder scenario
        System.out.println("\nChecking Model.Scenario...\n");
        Scenario accusationScenario = new Scenario(accusationWeaponCard, accusationRoomCard, accusationCharacterCard);

        if (murderScenario.equals(accusationScenario)) {
            System.out.println(p.getCharacter().getCharacterName() + " was successful in their accusation. They have won the game!!!");
            System.out.println("The murder scenario was: " + murderScenario.toString());
            System.out.println("\nThank you for playing Cluedo! To play a new game, please launch the program again.");
            return 1;
        } else {
            System.out.println("You were incorrect in your accusation. You may remain playing the game and offering suggestions, but are no longer able to make further accusations.");
            p.setCanAccuse(false);
            return 0;
        }
    }


    /**
     * Handles player suggestions.
     *
     * @author Baxter Kirikiri, Vaibhav Ekambaram, Cameron Li
     */
    private void makeSuggestion(Player p) {
        //initialize required fields
        Room room;
        RoomCard suggestionRoom = null;
        WeaponCard suggestionWeapon = null;
        CharacterCard suggestionCharacter = null;


        // check if player is currently in a room, can not suggest if not
        System.out.println("[Checking to see if player is currently in a room]");
        if (p.getCurrentPosition().getRoom() == null) {
            System.out.println("[Current player |" + p.getCharacter().getCharacterName() + "| is not currently in a room, suggestion can NOT continue]");
            return;
        }

        System.out.println("[Current player |" + p.getCharacter().getCharacterName() + "| is in room |" + p.getCurrentPosition().getRoom().toString() + "|]\n This room will be used in the suggestion");

        room = p.getCurrentPosition().getRoom();

        if (roomCardsMap.containsKey(p.getCurrentPosition().getRoom().toString())) {
            suggestionRoom = roomCardsMap.get(p.getCurrentPosition().getRoom().toString());
        }


        // ask for a suggestion character
        System.out.println("Please suggest a character: ");
        while (suggestionCharacter == null) {
            String suggestionCharacterInput = new Scanner(System.in).nextLine();
            if (characterCardsMap.containsKey(suggestionCharacterInput)) {
                suggestionCharacter = characterCardsMap.get(suggestionCharacterInput);
            }
            if (suggestionCharacter == null) {
                System.out.println("Character not found! Please enter a valid character name:");
            }
        }

        // ask for a suggestion weapon
        System.out.println("Please suggest murder weapon: ");
        while (suggestionWeapon == null) {
            String suggestionWeaponInput = new Scanner(System.in).nextLine();

            if (weaponCardsMap.containsKey(suggestionWeaponInput)) {
                suggestionWeapon = weaponCardsMap.get(suggestionWeaponInput);
            }

            if (suggestionWeapon == null)
                System.out.println("Weapon not found! Please enter a valid weapon name:");
        }

        System.out.println("Moving " + suggestionCharacter.getCharacterName() + " to " + room.toString() + "...");

        // teleport the suggested player to the suggested room
        for (Player findP : players) {
            if (findP.getCharacter().getCharacterName().equals(suggestionCharacter.getCharacterName())) {

                Room otherPlayerRoom = findP.getCurrentPosition().getRoom();

                if (otherPlayerRoom == null || !findP.getCurrentPosition().getRoom().equals(room)) {
                    Board newBoard = board.teleportPlayer(findP, room);
                    if (newBoard != null) {
                        board = newBoard;
                    } else {
                        System.out.println("An error occurred while teleporting the suggested player");
                    }
                }
            }
        }

        System.out.println("\n" + this.board + "\n");

        Scenario suggestion = new Scenario(suggestionWeapon, suggestionRoom, suggestionCharacter);
        System.out.println(p.getCharacter().getCharacterName() + "'s suggestion: [" + suggestion.toString() + "]");

        // place all other players in a stack so that they can take turns at refuting
        Stack<Player> refuters = new Stack<>();
        for (Player addToStack : players) {
            if (!addToStack.equals(p)) {
                refuters.add(addToStack);
            }
        }

        // iterate through the stack, asking each player to produce a refutation.
        boolean refuted = false;
        while (!refuters.isEmpty()) {
            Player currentTurn = refuters.pop();
            System.out.println(currentTurn.getCharacter().getCharacterName() + "'s turn to refute");
            System.out.println(currentTurn.getCharacter().getCharacterName() + "'s hand: ");

            List<Card> refuteCards = currentTurn.getHand();

            for (Card c : refuteCards) System.out.println("\t" + c.toString());

            System.out.println("Refute this suggestion with a card from your hand or type pass to skip: ");

            Card refutation = null;
            boolean failedRefutation = false;

            // read the input from the player and match it to a card in their hand. If no matching card exists, the player is prompted to try again
            while (refutation == null && !failedRefutation) {
                String refutationInput = new Scanner(System.in).nextLine();

                if (refutationInput.equalsIgnoreCase("Pass")) {
                    failedRefutation = true;
                } else {
                    for (Card c : refuteCards) {
                        if (c.toString().equals(refutationInput)) refutation = c;
                    }
                }

                if (refutation == null && !failedRefutation) {
                    System.out.println("Model.Card not found in players hand! Please try again: ");
                }
            }

            // if the previous player failed to refute, continue the loop
            if (failedRefutation) continue;

            // if the player successfully refutes the suggestion, end the loop
            if (refutation.toString().equals(suggestionCharacter.toString()) || refutation.toString().equals(suggestionRoom != null ? suggestionRoom.toString() : null) || refutation.toString().equals(suggestionWeapon.toString())) {
                System.out.println(p.getCharacter().getCharacterName() + "'s suggestion was refuted!");
                refuted = true;
                break;
            } else { //if the card the player used to refute is in their hand but it does not match any of the suggested cards
                System.out.println("Invalid refutation!");
            }
        }

        //if the stack is empty and no other player could refute, offer the option of making an Accusation to player who suggested
        if (!refuted) {
            System.out.println(p.getCharacter().getCharacterName() + "'s turn");
            System.out.println("No one could refute your suggestion! Type accuse to make an accusation or type pass to skip: ");

            boolean answered = false;
            while(!answered) {
                String answer = suggestionValidateInput();
                if (answer.equals("accuse")) {
                    answered = true;
                    int accuse = makeAccusation(p);
                    if (accuse == 1) { //if the accusation was successful
                        gameRunning = false;
                    }
                } else if (answer.equals("pass")) {
                    answered = true;
                } else {
                    System.out.println("Please enter either accuse or pass to continue: ");
                }
            }
        }
    }

    /**
     * Reads user text input and returns it
     *
     * @return String
     * @author Baxter Kirikiri
     */
    private String suggestionValidateInput() {
        String answer = "";
        String input = new Scanner(System.in).nextLine();
        try {
            answer = input;
        } catch (Exception e) {
            System.out.println("Please enter 'Accusation', 'Suggestion', or 'Pass'");
        }
        return answer;
    }

    private void transitionGameState() {
        checkGameState();
        if (gameState.equals(States.IDLE)) {
        }
    }

    private enum States {
        IDLE, RUNNING, FINISHED
    }

    private enum subStates {
        INIT, MOVEMENT, ACTION
    }
}