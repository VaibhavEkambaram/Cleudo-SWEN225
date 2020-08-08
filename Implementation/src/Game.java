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
                        System.out.println("\t\t[Room] " + c.toString());
                    }
                }
                System.out.println("\t\t________________________________");


                System.out.println("Would you like to make a suggestion, accusation or pass?");
                System.out.println("Available commands - [suggestion][accusation][pass]:");
                String answer = accSuggInput();
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
                } else if (command.equals("finish")) {
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
     * Handle Accusations
     *
     * @param p player
     * @return integer (-1 invalid, 0 failed, 1 successful)
     * @author Baxter Kirikiri, Vaibhav Ekambaram
     */
    private int makeAccusation(Player p) {
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
            Scanner accusationRoomScan = new Scanner(System.in);
            String accusationRoomInput = accusationRoomScan.nextLine();

            for (RoomCard r : roomCards) {
                if (r.getRoomName().equals(accusationRoomInput)) accusationRoomCard = r;
            }
            if (accusationRoomCard == null) System.out.println("Room not found! Please enter a valid room name:");
        }

        // get accusation character
        System.out.println("Please enter a character to accuse: ");
        while (accusationCharacterCard == null) {
            Scanner accusationCharacterScan = new Scanner(System.in);
            String accusationCharacterInput = accusationCharacterScan.nextLine();

            for (CharacterCard c : characterCards) {
                if (c.getCharacterName().equals(accusationCharacterInput)) accusationCharacterCard = c;
            }
            if (accusationCharacterCard == null)
                System.out.println("Character not found! Please enter a valid character name:");
        }

        // get accusation character
        System.out.println("Please enter an accusation murder weapon: ");
        while (accusationWeaponCard == null) {
            Scanner accusationWeaponScan = new Scanner(System.in);
            String accusationWeaponInput = accusationWeaponScan.nextLine();

            for (WeaponCard w : weaponCards) {
                if (w.getWeaponName().equals(accusationWeaponInput)) accusationWeaponCard = w;
            }
            if (accusationWeaponCard == null)
                System.out.println("Weapon not found! Please enter a valid weapon name:");
        }

        // create scenario and compare to the original murder scenario
        System.out.println("\nChecking Scenario...\n");
        Scenario accusationScenario = new Scenario(accusationWeaponCard, accusationRoomCard, accusationCharacterCard);

        if (murderScenario.equals(accusationScenario)) {
            System.out.println(p.getCharacter().getCharacterName() + " was successful in their accusation. They have won the game!!!");
            return 1;
        } else {
            System.out.println("You were incorrect in your accusation. You may remain playing the game and offering suggestions, but may no longer be able to make further accusations.");
            p.setCanAccuse(false);
            return 0;
        }
    }


    /**
     * Handles player suggestions.
     *
     * @author Baxter Kirikiri, Vaibhav
     */
    private void makeSuggestion(Player p) {

        Room room = null;
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

        for (RoomCard r : roomCards) {
            if (p.getCurrentPosition().getRoom().toString().equals(r.getRoomName())) {
                suggestionRoom = r;
            }
        }

        // get accusation character
        System.out.println("Please suggest a character: ");
        while (suggestionCharacter == null) {
            Scanner suggestionCharacterScan = new Scanner(System.in);
            String suggestionCharacterInput = suggestionCharacterScan.nextLine();

            for (CharacterCard c : characterCards) {
                if (c.getCharacterName().equals(suggestionCharacterInput)) suggestionCharacter = c;
            }
            if (suggestionCharacter == null)
                System.out.println("Character not found! Please enter a valid character name:");
        }

        // get accusation character
        System.out.println("Please suggest murder weapon: ");
        while (suggestionWeapon == null) {
            Scanner suggestionWeaponScan = new Scanner(System.in);
            String suggestionWeaponInput = suggestionWeaponScan.nextLine();

            for (WeaponCard w : weaponCards) {
                if (w.getWeaponName().equals(suggestionWeaponInput)) suggestionWeapon = w;
            }
            if (suggestionWeapon == null)
                System.out.println("Weapon not found! Please enter a valid weapon name:");
        }

        System.out.println("Moving " + suggestionCharacter.getCharacterName() + " to " + room.toString() + "...");

        for (Player findP : players) {
            if (findP.getCharacter().getCharacterName().equals(suggestionCharacter.getCharacterName())) {

                Room otherPlayerRoom = findP.getCurrentPosition().getRoom();

                if (otherPlayerRoom == null || !findP.getCurrentPosition().getRoom().equals(room)) {
                    System.out.println(findP.toString());
                    System.out.println(room.toString());
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


        // TODO: update refutation to use scenario
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

            List<Card> refuteCards = currentTurn.getHand();

            for (Card c : refuteCards) System.out.println("\t" + c.toString());

            System.out.println("Refute this suggestion with a card from your hand or type pass to skip: ");


            Card refutation = null;
            boolean failedRefutation = false;

            while (refutation == null && !failedRefutation) {
                Scanner refutationScan = new Scanner(System.in);
                String refutationInput = refutationScan.nextLine();

                if (refutationInput.equalsIgnoreCase("Pass")) {
                    failedRefutation = true;
                } else {
                    for (Card c : refuteCards) {
                        if (c.toString().equals(refutationInput)) {
                            refutation = c;
                        }
                    }
                }

                if (refutation == null && !failedRefutation) {
                    System.out.println("Card not found in players hand! Please try again: ");
                }
            }

            if (failedRefutation) {
                continue;
            }


            if (refutation.toString().equals(suggestionCharacter.toString()) || refutation.toString().equals(suggestionRoom != null ? suggestionRoom.toString() : null) || refutation.toString().equals(suggestionWeapon.toString())) {
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

                int accuse = makeAccusation(p);
                if (accuse == 1) {
                    gameRunning = false;
                }
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
     * Finds and matches a String with a Card in player hand
     * If not found, returns null - indicated not in hand or doesn't exist
     *
     * @param cards
     * @return
     * @author Cameron Li
     */
    private <C extends Card> C findCard(List<C> cards) {
        boolean valid = false;
        Card foundCard = null;
        while (!valid) {
            String cardName = "";
            Scanner inputScan = new Scanner(System.in);
            try {
                cardName = inputScan.nextLine();
            } catch (Exception e) {
                System.out.println("Please enter a valid card name");
            }

            // Find Card
            for (int c = 0; c < cards.size(); c++) {
                if (cards.get(c).toString().equals(cardName)) {
                    foundCard = cards.get(c);
                    valid = true;
                }
            }

            if (!valid) {
                System.out.println("Invalid Card Name, please try again");
            }
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
        WeaponCard murderWeapon = weaponCards.get(new Random().nextInt(wepNames.length - 1) + 1);
        RoomCard murderRoom = roomCards.get(new Random().nextInt(roomNames.length - 1) + 1);
        CharacterCard murderer = characterCards.get(new Random().nextInt(numPlayers) + 1);
        murderScenario = new Scenario(murderWeapon, murderRoom, murderer);
        deck.remove(murderWeapon);
        deck.remove(murderRoom);
        deck.remove(murderer);
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
        for (Card card : this.deck) {
            toBeDealt.push(card);
        }

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