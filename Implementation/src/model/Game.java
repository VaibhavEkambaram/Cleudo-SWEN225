package model;/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.0.5071.d9da8f6cd modeling language!*/

import util.AutoSetup;
import view.AccusationMenu;
import view.GUI;
import view.PlayerSetupMenu;
import view.SuggestionMenu;

import java.util.*;

/**
 * Main Game Class
 */
public class Game {

    //------------------------
    // MEMBER VARIABLES
    //------------------------

    private States gameState = States.IDLE;
    private subStates subState;
    public GUI userInterface;

    /**
     * Check if States are matching within their appropriate sub-states
     * If incorrect, throw an error with respective states
     */

    private final String[] weaponNames = {"Candlestick", "Dagger", "Lead Pipe", "Revolver", "Rope", "Spanner"};
    private final String[] roomNames = {"Kitchen", "Dining Room", "Lounge", "Hall", "Study", "Library", "Billiard Room", "Conservatory", "Ball Room"};
    private final String[] characterNames = {"Miss Scarlett", "Col. Mustard", "Mrs. White", "Mr. Green", "Mrs. Peacock", "Prof. Plum"};

    private final Map<String, WeaponCard> weaponCardsMap = new HashMap<>();
    private final Map<String, RoomCard> roomCardsMap = new HashMap<>();
    private final Map<String, CharacterCard> characterCardsMap = new HashMap<>();

    private List<Card> deck;
    private List<Room> rooms;
    public List<Player> players;

    private Board board;
    private Player currentPlayer;
    private Scenario murderScenario;  // Murder Scenario that players must find

    // track number of moves for current player
    int currentPlayerIndex = 0;
    int movesRemaining = -1;
    public int numPlayers;

    // boolean to prevent infinite loop while testing
    boolean runGraphicalOutput;


    /**
     * Game Constructor
     * Primary game springboard for all other methods of the game
     *
     * @param run boolean validator
     * @author Cameron Li, Vaibhav Ekambaram
     */
    public Game(boolean run) {
        runGraphicalOutput = run;
        initDeck();
        if (run) {
            initPlayers();
        } else {
            new AutoSetup(this);
        }
        initBoard(); // generate board
        dealCards();
        runGame(); // start up the game
    }


    /**
     * Create deck of cards and shuffle
     * Generate game murder scenario
     *
     * @author Cameron Li
     */
    private void initDeck() {
        idleToInit();
        if (!subState.equals(subStates.DECK)) {
            throw new Error("Expecting DECK Sub State but " + subState);
        }

        // Adding cards
        deck = new ArrayList<>();

        // Weapons

        List<WeaponCard> weaponCards = new ArrayList<>();
        for (String weaponName : weaponNames) {
            WeaponCard weapon = new WeaponCard(weaponName);


            weaponCards.add(weapon);
            weaponCardsMap.put(weaponName, weapon);
            deck.add(weapon);
        }

        // Rooms
        rooms = new ArrayList<>();
        List<RoomCard> roomCards = new ArrayList<>();
        for (String r : roomNames) {
            Room newRoom = new Room(r);
            rooms.add(newRoom);
            RoomCard newRoomCard = new RoomCard(r, newRoom);
            roomCards.add(newRoomCard);
            roomCardsMap.put(r, newRoomCard);
            deck.add(newRoomCard);
        }

        // Characters
        List<CharacterCard> characterCards = new ArrayList<>();
        // Only create cards where there are players
        for (String c : characterNames) {
            CharacterCard character = new CharacterCard(c);
            characterCards.add(character);
            characterCardsMap.put(c, character);
            deck.add(character);
        }

        Collections.shuffle(deck);
        // Murder Scenario of Random Cards
        WeaponCard murderWeapon = weaponCards.get(new Random().nextInt(weaponNames.length - 1) + 1);
        RoomCard murderRoom = roomCards.get(new Random().nextInt(roomNames.length - 1) + 1);
        CharacterCard murderer = characterCards.get(new Random().nextInt(characterNames.length - 1) + 1);
        murderScenario = new Scenario(murderWeapon, murderRoom, murderer);
        deck.remove(murderWeapon);
        deck.remove(murderRoom);
        deck.remove(murderer);
    }

    /**
     * Ask how many people want to play the game, then display character preferences menu
     * Using this information, set the players up for the game
     *
     * @author Vaibhav Ekambaram
     */
    private void initPlayers() {
        playerTransition(); // Transition from DECK to PLAYERS

        if (!subState.equals(subStates.PLAYERS)) {
            throw new Error("Expecting PLAYERS Sub State but " + subState);
        }

        if (runGraphicalOutput) {
            userInterface = new GUI(this);
        }

        PlayerSetupMenu p = new PlayerSetupMenu();
        numPlayers = p.setPlayerCount();
        players = new ArrayList<>();
        players = p.setPlayers(characterNames, numPlayers, players, characterCardsMap, false);
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
     * @author Cameron Li, Vaibhav Ekambaram
     */
    private void initBoard() {
        boardTransition(); // Transition from DECK to BOARD

        if (!subState.equals(subStates.BOARD)) {
            throw new Error("Expecting BOARD Sub State but " + subState);
        }

        String boardLayout =
                " x x x x x x x x x 3 x x x x 4 x x x x x x x x x \n" +
                        " k k k k k k x _ _ _ b b b b _ _ _ x c c c c c c \n" +
                        " k $K K K K k _ _ b b b %B B b b b _ _ c C C C C c \n" +
                        " k K K K K k _ _ b B B B B B B b _ _ c C C C C c \n" +
                        " k K K K K k _ _ b B B B B B B b _ _ vC c C C c c \n" +
                        " k k K K K k _ _ <B B B B B B B >B _ _ _ c c c c x \n" +
                        " x k k k vK k _ _ b B B B B B B b _ _ _ _ _ _ _ 5 \n" +
                        " _ _ _ _ _ _ _ _ b vB b b b b vB b _ _ _ _ _ _ _ x \n" +
                        " x _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ i i i i i i \n" +
                        " d d d d d _ _ _ _ _ _ _ _ _ _ _ _ _ <I @I I I I i \n" +
                        " d !D D D d d d d _ _ x x x x x _ _ _ i I I I I i \n" +
                        " d D D D D D D d _ _ x x x x x _ _ _ i I I I I i \n" +
                        " d D D D D D D >D _ _ x x x x x _ _ _ i i i i vI i \n" +
                        " d D D D D D D d _ _ x x x x x _ _ _ _ _ _ _ _ x \n" +
                        " d D D D D D D d _ _ x x x x x _ _ _ l l ^L l l x \n" +
                        " d d d d d d vD d _ _ x x x x x _ _ l l L L L l l \n" +
                        " x _ _ _ _ _ _ _ _ _ x x x x x _ _ <L L L L L L l \n" +
                        " 2 _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ l l L L L l l \n" +
                        " x _ _ _ _ _ _ _ _ h h ^H ^H h h _ _ _ l l l l l x \n" +
                        " o o o o o o ^O _ _ h H H H H h _ _ _ _ _ _ _ _ 6 \n" +
                        " o ?O O O O O o _ _ h H H H H >H _ _ _ _ _ _ _ _ x \n" +
                        " o O O O O O o _ _ h H H H H h _ _ ^Y y y y y y y \n" +
                        " o O O O O O o _ _ h H H H H h _ _ y &Y Y Y Y Y y \n" +
                        " o O O O O o o _ _ h H H H H h _ _ y y Y Y Y Y y \n" +
                        " o o o o o o x 1 x h h h h h h x _ x y y y y y y \n";

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
                            newPosition = new Position(x, y, true, true, null, r, null);
                            break;
                        } else if (positionName.equals("?" + r.getRoomChar())) {
                            newPosition = new Position(x, y, true, true, null, r, weaponCardsMap.get("Candlestick"));
                            break;
                        } else if (positionName.equals("!" + r.getRoomChar())) {
                            newPosition = new Position(x, y, true, true, null, r, weaponCardsMap.get("Dagger"));
                            break;
                        } else if (positionName.equals("$" + r.getRoomChar())) {
                            newPosition = new Position(x, y, true, true, null, r, weaponCardsMap.get("Lead Pipe"));
                            break;
                        } else if (positionName.equals("%" + r.getRoomChar())) {
                            newPosition = new Position(x, y, true, true, null, r, weaponCardsMap.get("Revolver"));
                            break;
                        } else if (positionName.equals("@" + r.getRoomChar())) {
                            newPosition = new Position(x, y, true, true, null, r, weaponCardsMap.get("Rope"));
                            break;
                        } else if (positionName.equals("&" + r.getRoomChar())) {
                            newPosition = new Position(x, y, true, true, null, r, weaponCardsMap.get("Spanner"));
                            break;
                        } else if (positionName.equals("^" + r.getRoomChar())) { // Up door
                            newPosition = new Position(x, y, true, true, Move.Direction.UP, r, null);
                            break;
                        } else if (positionName.equals(">" + r.getRoomChar())) { // Right door
                            newPosition = new Position(x, y, true, true, Move.Direction.RIGHT, r, null);
                            break;
                        } else if (positionName.equals("v" + r.getRoomChar())) { // Down door
                            newPosition = new Position(x, y, true, true, Move.Direction.DOWN, r, null);
                            break;
                        } else if (positionName.equals("<" + r.getRoomChar())) {
                            newPosition = new Position(x, y, true, true, Move.Direction.LEFT, r, null);
                            break;
                        } else if (positionName.equals(r.getRoomChar().toLowerCase())) { // Is this an outer room position?
                            newPosition = new Position(x, y, true, false, null, r, null);
                            break;
                        }
                    }
                }

                if (newPosition == null) { // Add in remaining Character Positions
                    for (CharacterCard c : characterCardsMap.values()) {
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

    /**
     * Deal Cards
     * Add cards from the deck list to a stack, then deal them to each player until the stack is empty
     *
     * @author Cameron Li
     */
    public void dealCards() {
        Stack<Card> toBeDealt = new Stack<>();
        for (Card card : this.deck) {
            toBeDealt.push(card);
        }

        while (!toBeDealt.isEmpty()) {
            for (Player p : this.players) {
                // Make sure not null pointer exception
                if (toBeDealt.isEmpty()) break;
                p.addHand(toBeDealt.pop());
            }
        }
    }


    /**
     * Start the game
     *
     * @author Cameron Li, Vaibhav Ekambaram
     */
    public void runGame() {
        initToRunning();
        movementTransition();
        if (runGraphicalOutput) {
            userInterface.updateDisplay();
        }
    }

    /**
     * Roll two dice and then return the overall number
     *
     * @return Cumulative dice throw result
     * @author Vaibhav Ekambaram
     */
    public int rollDice() {
        setMovesRemaining(-1);
        // find a random number in the range of 0 to 5, then add 1 as an offset for 1 to 6
        int firstResult = new Random().nextInt(6) + 1;
        int secondResult = new Random().nextInt(6) + 1;

        if (runGraphicalOutput) {
            userInterface.RollDiceMenu(firstResult, secondResult);
        }

        setMovesRemaining((firstResult + secondResult));
        return firstResult + secondResult;
    }

    /**
     * Handle Movement
     * Check validity
     * Transition states if no more moves
     *
     * @param move move
     * @return boolean validator
     * @author Cameron Li
     */
    public boolean movementInput(Move move) {
        if (!subState.equals(subStates.MOVEMENT)) {
            throw new Error("Expected MOVEMENT sub state but : " + subState);
        }

        if (move.getSpaces() > movesRemaining) {
            return false;
        }
        Board newBoard = board.apply(currentPlayer, move);
        if (newBoard != null) {
            movesRemaining = movesRemaining - move.getSpaces();
            if (movesRemaining < 1) {
                actionTransition();
            }
            board = newBoard;
            return true;
        }
        return false;
    }

    /**
     * Handle Accusations
     *
     * @param p                      player
     * @param accusationStringPreset preset for testing
     * @return integer (-1 invalid, 0 failed, 1 successful)
     * @author Baxter Kirikiri, Vaibhav Ekambaram
     */
    public int makeAccusation(Player p, String accusationStringPreset) {
        String accusationString;
        AccusationMenu a = null;
        if (runGraphicalOutput) {
            a = new AccusationMenu();
            if (!p.getCanAccuse()) {
                a.unableToAccuse(p);
                return -1;
            }
            accusationString = a.makeAccusation(characterNames, weaponNames, roomNames);
        } else {
            accusationString = accusationStringPreset;
        }
        String[] accusationStringSplit = accusationString.split("\t");

        // create scenario and compare to the original murder scenario
        Scenario accusationScenario = new Scenario(weaponCardsMap.get(accusationStringSplit[1]), roomCardsMap.get(accusationStringSplit[2]), characterCardsMap.get(accusationStringSplit[0]));


        if (murderScenario.equals(accusationScenario)) {
            if (runGraphicalOutput) {
                assert a != null;
                a.successfulAccusation(p, murderScenario);
            }
            return 1;
        } else {
            if (runGraphicalOutput) {
                assert a != null;
                a.incorrectAccusation(p);
            }
            p.setCanAccuse(false);
            return 0;
        }
    }

    /**
     * Handles player suggestions.
     *
     * @param p                      player
     * @param suggestionStringPreset preset for testing
     * @param refutationPresets      preset for testing
     * @param couldntRefuteCommand   preset for testing
     * @return integer validator
     * @author Baxter Kirikiri, Vaibhav Ekambaram, Cameron Li
     */
    public int makeSuggestion(Player p, String suggestionStringPreset, String[] refutationPresets, int couldntRefuteCommand) {

        SuggestionMenu s = new SuggestionMenu();


        //initialize required fields
        Room room;
        RoomCard suggestionRoom = null;
        WeaponCard suggestionWeapon;
        CharacterCard suggestionCharacter;


        // check if player is currently in a room, can not suggest if not
        if (p.getCurrentPosition().getRoom() == null) {
            if (runGraphicalOutput) {
                s.unableToSuggest(p);
            }
            return -1;
        }

        room = p.getCurrentPosition().getRoom();

        if (roomCardsMap.containsKey(p.getCurrentPosition().getRoom().toString())) {
            suggestionRoom = roomCardsMap.get(p.getCurrentPosition().getRoom().toString());
        }

        String suggestionString;

        if (runGraphicalOutput) {
            suggestionString = s.makeSuggestion(characterNames, weaponNames, roomNames, Objects.requireNonNull(suggestionRoom));
        } else {
            suggestionString = suggestionStringPreset;
        }
        String[] suggestionStringSplit = suggestionString.split("\t");

        suggestionWeapon = weaponCardsMap.get(suggestionStringSplit[1]);
        suggestionCharacter = characterCardsMap.get(suggestionStringSplit[0]);


        // teleport the suggested player to the suggested room
        for (Player findP : players) {
            if (findP.getCharacter().getCharacterName().equals(suggestionCharacter.getCharacterName())) {

                Room otherPlayerRoom = findP.getCurrentPosition().getRoom();

                if (otherPlayerRoom == null || !findP.getCurrentPosition().getRoom().equals(room)) {
                    Board newBoard = board.teleportPlayer(findP, room);
                    if (newBoard != null) {
                        board = newBoard;
                        if (runGraphicalOutput) {
                            userInterface.updateDisplay();
                        }
                    }
                }
            }
        }

        // teleport the suggested board to the suggested room
        Board newBoard = board.teleportWeapon(suggestionWeapon, room);
        if (newBoard != null) {
            board = newBoard;
            if (runGraphicalOutput) {
                userInterface.updateDisplay();
            }
        }

        // place all other players in a stack so that they can take turns at refuting
        Stack<Player> refuters = new Stack<>();
        for (Player addToStack : players) {
            if (!addToStack.equals(p)) {
                refuters.add(addToStack);
            }
        }

        // iterate through the stack, asking each player to produce a refutation.
        boolean refuted = false;
        int count = 0;
        while (!refuters.isEmpty()) {
            Player currentTurn = refuters.pop();


            String refute;

            if (runGraphicalOutput) {
                refute = s.makeRefutation(currentTurn, currentPlayer, new Scenario(suggestionWeapon, suggestionRoom, suggestionCharacter));
            } else {
                refute = refutationPresets[count];
            }


            count++;

            if (refute.equals("-1")) {
                continue;
            }


            List<Card> refuteCards = currentTurn.getHand();

            Card refutation = null;

            if (runGraphicalOutput) {
                for (Card c : refuteCards) {
                    if (c.toString().equals(refute)) {
                        refutation = c;
                    }
                }
            } else {
                refutation = weaponCardsMap.get(refute);
            }

            // if the player successfully refutes the suggestion, end the loop
            if (Objects.requireNonNull(refutation).toString().equals(suggestionCharacter.toString()) || refutation.toString().equals(Objects.requireNonNull(suggestionRoom).toString()) || refutation.toString().equals(suggestionWeapon.toString())) {
                if (runGraphicalOutput) {
                    s.successfulRefutation(p.getCharacter().getCharacterName());
                }
                refuted = true;
                movementTransition();
                return 1;
            } else { //if the card the player used to refute is in their hand but it does not match any of the suggested cards
                s.failedRefutation(currentTurn.getPlayerVanityName());

            }
        }

        //if the stack is empty and no other player could refute, offer the option of making an Accusation to player who suggested
        if (!refuted) {
            int i;

            if (runGraphicalOutput) {
                i = s.nobodyCouldRefute();
            } else {
                i = couldntRefuteCommand;
            }

            if (i == 0) {
                int accuse;
                accuse = makeAccusation(p, null);
                if (accuse == 1) {
                    gameState = States.FINISHED;
                } else {
                    movementTransition();
                }
            } else {
                movementTransition();
            }
        }
        return 0;
    }

    /**
     * Switch current player to the next player
     */
    public void changePlayer() {
        if (currentPlayerIndex + 1 >= numPlayers) {
            currentPlayerIndex = 0;
        } else {
            currentPlayerIndex++;
        }
        currentPlayer = players.get(currentPlayerIndex);
    }

    /**
     * Transition from Idle Game State to Init Game State
     *
     * @author Cameron Li
     */
    private void idleToInit() {
        if (!gameState.equals(States.IDLE)) {
            throw new Error("Expected IDLE game state but " + gameState);
        }
        gameState = States.INIT;
        subState = subStates.DECK;
    }

    /**
     * Must be within Init Game State
     * Transition from Deck Sub State to Player Sub State
     *
     * @author Cameron li
     */
    public void playerTransition() {
        if (!gameState.equals(States.INIT)) {
            throw new Error("Expected INIT game state but : " + gameState);
        }
        if (!subState.equals(subStates.DECK)) {
            throw new Error("Expected DECK sub state but : " + subState);
        }
        subState = subStates.PLAYERS;
    }

    /**
     * Must be within Init Game State
     * Transition from Player Sub State to Board Sub State
     *
     * @author Cameron li
     */
    private void boardTransition() {
        if (!gameState.equals(States.INIT)) {
            throw new Error("Expected INIT game state but : " + gameState);
        }
        if (!subState.equals(subStates.PLAYERS)) {
            throw new Error("Expected PLAYER sub state but : " + subState);
        }
        subState = subStates.BOARD;
    }

    /**
     * Transition from Init Game State to Running Game State
     *
     * @author Cameron Li
     */
    private void initToRunning() {
        if (!gameState.equals(States.INIT)) {
            throw new Error("Expected INIT game state but " + gameState);
        }
        gameState = States.RUNNING;
    }

    /**
     * Must be within Running Game State
     * Transition from ANY Sub State to Movement Sub State
     * If specifically, Action Sub State, change player as well
     *
     * @author Cameron li
     */
    public void movementTransition() {
        if (!gameState.equals(States.RUNNING)) {
            throw new Error("Expected RUNNING game state but : " + gameState);
        }
        if (subState.equals(subStates.ACTION)) {
            changePlayer();
        }

        subState = subStates.MOVEMENT;
        currentPlayer = players.get(currentPlayerIndex);
        movesRemaining = -1;
    }

    /**
     * Must be within Running Game State
     * Transition from Movement Sub State to Action Sub State
     *
     * @author Cameron li
     */
    public void actionTransition() {
        if (!gameState.equals(States.RUNNING)) {
            throw new Error("Expected RUNNING game state but : " + gameState);
        }
        if (!subState.equals(subStates.MOVEMENT)) {
            throw new Error("Expected Movement sub state but : " + subState);
        }
        movesRemaining = -1;
        subState = subStates.ACTION;
    }

    /**
     * Transition from Running Game State to Finished Game State
     *
     * @author Cameron Li
     */
    public void finishTransition() {
        if (!gameState.equals(States.RUNNING)) {
            throw new Error("Expected RUNNING game state but : " + gameState);
        }
        gameState = States.FINISHED;
        if (runGraphicalOutput) {
            userInterface.updateDisplay();
        }
    }


    /**
     * Get game state for use with gui
     *
     * @return state enum
     */
    public States getGameState() {
        return gameState;
    }

    /**
     * Get game substate for use with gui
     *
     * @return substate enum
     */
    public subStates getSubState() {
        return subState;
    }

    /**
     * Get board
     *
     * @return board
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * Get current player
     *
     * @return player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Get number of moves remaining
     *
     * @return moves remaining
     */
    public int getMovesRemaining() {
        return movesRemaining;
    }

    /**
     * Get the generated murder scenario
     *
     * @return scenario
     */
    public Scenario getMurderScenario() {
        return murderScenario;
    }

    /**
     * Retrieve character names as an array
     *
     * @return array of character names
     */
    public String[] getCharacterNames() {
        return characterNames;
    }

    /**
     * Get map of character card names
     *
     * @return string, weaponCard map
     */
    public Map getCharacterCardsMap() {
        return characterCardsMap;
    }

    /**
     * Get map of weapon card names
     *
     * @return string, weaponCard map
     */
    public Map getWeaponCardsMap() {
        return weaponCardsMap;
    }

    /**
     * Get map of room card names
     *
     * @return string, roomCard map
     */
    public Map getRoomCardsMap() {
        return roomCardsMap;
    }


    /**
     * Set number of player moves remaining
     *
     * @param value number of moves
     */
    public void setMovesRemaining(int value) {
        this.movesRemaining = value;
    }

    /**
     * Set murder scenario
     *
     * @param newScenario scenario
     */
    public void setMurderScenario(Scenario newScenario) {
        murderScenario = newScenario;
    }


    /**
     * Main Game States
     */
    public enum States {
        IDLE, INIT, RUNNING, FINISHED
    }


    /**
     * Game SubStates
     */
    public enum subStates {
        PLAYERS, DECK, BOARD, MOVEMENT, ACTION
    }
}