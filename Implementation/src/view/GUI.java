package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;

import static model.Game.subStates.MOVEMENT;

/**
 * Class for GUI Functionality
 *
 * @author Vaibhav Ekambaram, Cameron Li
 */
public class GUI extends Observable {

    // Constants
    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(700, 700);
    private final int BOARD_WIDTH = 24;
    private final int BOARD_HEIGHT = 25;
    private final int BORDER_SIZE = 20;
    private int RECT_SIZE;

    // Frames
    private final JFrame gameFrame;

    // Panels
    private final JPanel infoPanel;
    private final JPanel displayPanel;
    private final JPanel handPanel;
    private final JPanel actionPanel;
    private final JPanel movementPanel;

    // Buttons
    final JButton suggestionButton;
    final JButton accusationButton;
    final JButton passButton;
    final JButton upButton;
    final JButton downButton;
    final JButton leftButton;
    final JButton rightButton;
    final JButton rollDiceButton;
    final JButton finishedButton;

    // Image Labels
    private JLabel firstDiceImageLabel;
    private JLabel secondDiceImageLabel;

    // Players
    private Player currentPlayer;
    private Player previousPlayer;

    private int movesRemaining;


    final Game game;
    // Display
    private final JTextArea info;

    private KeyStates keyTracker = KeyStates.PRE_ROLL;

    /**
     * User Interface Constructor
     *
     * @param game
     * @author Vaibhav Ekambaram, Cameron Li
     */
    public GUI(Game game) {
        this.game = game;
        this.gameFrame = new JFrame("Cluedo");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.gameFrame.setVisible(true);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;

        // Panels
        JPanel mainPanel = new JPanel(new GridBagLayout());

        // Action Panel
        actionPanel = new JPanel();
        actionPanel.setBackground(Color.WHITE);
        constraints.weightx = .2;
        constraints.weighty = .8;
        constraints.gridx = 0;
        constraints.gridy = 0;
        actionPanel.setPreferredSize(new Dimension(200, 500));
        mainPanel.add(actionPanel, constraints);

        // Display Panel
        constraints.weightx = .8;
        constraints.weighty = .9;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 1;
        displayPanel = new JPanel();
        displayPanel.setBackground(new Color(97, 185, 125));
        displayPanel.setPreferredSize(new Dimension(750, 600));
        displayPanel.setDoubleBuffered(true);
        mainPanel.setDoubleBuffered(true);
        mainPanel.add(displayPanel, constraints);

        // Info Panel
        infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(Color.WHITE);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weighty = .1;
        mainPanel.add(infoPanel, constraints);
        info = new JTextArea();
        info.setText("Hello and welcome to Cluedo\nMade by:\nCameron Li, Vaibhav Ekambaram and Baxter Kirikiri");
        info.setEditable(false);
        infoPanel.add(info, BorderLayout.CENTER);
        infoPanel.setPreferredSize(new Dimension(500, 50));

        // Hand Panel
        handPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        handPanel.setBackground(Color.WHITE);
        handPanel.setPreferredSize(new Dimension(700, 183));
        constraints.weightx = 1;
        constraints.weighty = 0.2;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;

        mainPanel.add(handPanel, constraints);

        // Movement Panel
        movementPanel = new JPanel(new GridBagLayout());
        movementPanel.setBackground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridheight = 1;
        constraints.gridwidth = 1;
        mainPanel.add(movementPanel, constraints);

        // Borders
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        displayPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        handPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        actionPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        infoPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        movementPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        actionPanel.setLayout(new GridLayout(12, 2));


        gameFrame.add(mainPanel);

        displayPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                makeMouseMovement(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
            }
        });

        // Roll Dice Button
        rollDiceButton = new JButton("Roll Dice [r]");
        actionPanel.add(rollDiceButton);
        rollDiceButton.addActionListener(e -> {
            game.setMovesRemaining(-1);
            game.setMovesRemaining(game.rollDice());
            setRollDiceButtonVisibility(false);
            setFinishedButtonVisibility(true);
            movementPanel.setVisible(true);
        });
        rollDiceButton.setFocusable(true);



        // Suggestion Button
        suggestionButton = new JButton("Make Suggestion");
        actionPanel.add(suggestionButton);
        suggestionButton.addActionListener(e -> {
            int suggest = game.makeSuggestion(game.getCurrentPlayer());
            if (suggest == -1) {
                game.movementTransition();
                setRollDiceButtonVisibility(true);
                drawHand();
                firstDiceImageLabel.setVisible(false);
                secondDiceImageLabel.setVisible(false);
            }
        });

        // Accusation Button
        accusationButton = new JButton("Make Accusation");
        actionPanel.add(accusationButton);
        accusationButton.addActionListener(e -> {
            int accuse = game.makeAccusation(game.getCurrentPlayer());
            if (accuse == 1) {
                game.finishTransition();
            } else {
                game.movementTransition();
                setRollDiceButtonVisibility(true);
                drawHand();
                firstDiceImageLabel.setVisible(false);
                secondDiceImageLabel.setVisible(false);
            }
        });

        // Pass Button
        passButton = new JButton("Pass");
        actionPanel.add(passButton);
        passButton.addActionListener(e -> {
            game.movementTransition();
            setRollDiceButtonVisibility(true);

            drawHand();
            firstDiceImageLabel.setVisible(false);
            secondDiceImageLabel.setVisible(false);
            JOptionPane.showMessageDialog(null, "Moving to next player...", "Information", JOptionPane.PLAIN_MESSAGE);
        });

        // Finished Button
        finishedButton = new JButton("Finished");
        finishedButton.addActionListener(e -> {
            game.actionTransition();
            setFinishedButtonVisibility(false);
            movementPanel.setVisible(false);
            setSuggestionAccusationVisibility(true);
        });


        // Movement Buttons
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = .3;
        constraints.gridx = 1;
        constraints.gridy = 0;
        upButton = new JButton("Up");
        movementPanel.add(upButton, constraints);
        upButton.addActionListener(e -> makeMovement(Move.Direction.UP));




        constraints.gridx = 1;
        constraints.gridy = 1;
        downButton = new JButton("Down");
        movementPanel.add(downButton, constraints);
        downButton.addActionListener(e -> makeMovement(Move.Direction.DOWN));

        constraints.gridx = 0;
        constraints.gridy = 1;
        leftButton = new JButton("Left");
        movementPanel.add(leftButton, constraints);
        leftButton.addActionListener(e -> makeMovement(Move.Direction.LEFT));

        constraints.gridx = 2;
        constraints.gridy = 1;
        rightButton = new JButton("Right");
        movementPanel.add(rightButton, constraints);
        rightButton.addActionListener(e -> makeMovement(Move.Direction.RIGHT));



        suggestionButton.setFocusable(false);
        accusationButton.setFocusable(false);
        passButton.setFocusable(false);
        upButton.setFocusable(false);
        downButton.setFocusable(false);
        leftButton.setFocusable(false);
        rightButton.setFocusable(false);
        rollDiceButton.setFocusable(false);
        finishedButton.setFocusable(false);



        gameFrame.setFocusable(true);
        gameFrame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // not doing anything
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(keyTracker == KeyStates.PRE_ROLL){
                    if(e.getKeyCode() == 82){
                        game.setMovesRemaining(-1);
                        game.setMovesRemaining(game.rollDice());
                        setRollDiceButtonVisibility(false);
                        setFinishedButtonVisibility(true);
                        movementPanel.setVisible(true);
                    }

                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // not doing anything
            }
        });






        // Window Close Listener
        gameFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                int closeDialogButton = JOptionPane.YES_NO_OPTION;
                int closeDialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Warning", closeDialogButton);
                if (closeDialogResult == JOptionPane.YES_OPTION) {
                    gameFrame.dispose();
                    gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                } else {
                    gameFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });

        gameFrame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                if (game.getBoard() != null) updateDisplay();
            }
        });

        gameFrame.setMinimumSize(new Dimension(800, 800));
        gameFrame.pack();

        setSuggestionAccusationVisibility(false);
        showMovement(false);
    }


    /**
     * Draw Dice Roll Results
     *
     * @param firstNumber  first dice number
     * @param secondNumber second dice number
     * @author Vaibhav Ekambaram
     */
    public void RollDiceMenu(int firstNumber, int secondNumber) {
        firstDiceImageLabel = new JLabel(new ImageIcon(getClass().getResource("/resources/dice_" + firstNumber + ".png")));
        secondDiceImageLabel = new JLabel(new ImageIcon(getClass().getResource("/resources/dice_" + secondNumber + ".png")));
        handPanel.add(firstDiceImageLabel);
        handPanel.add(secondDiceImageLabel);
        firstDiceImageLabel.setVisible(true);
        secondDiceImageLabel.setVisible(true);
    }

    /**
     * Set visibility of dice roll button
     *
     * @param value boolean
     * @author Vaibhav Ekambaram
     */
    public void setRollDiceButtonVisibility(boolean value) {
        if (value) {
            game.setMovesRemaining(-1);
            keyTracker = KeyStates.PRE_ROLL;
            actionPanel.add(rollDiceButton);
        } else {
            actionPanel.remove(rollDiceButton);
        }
        rollDiceButton.setVisible(value);
    }

    /**
     * Set visibility of finished button
     *
     * @param value boolean
     * @author Vaibhav Ekambaram
     */
    public void setFinishedButtonVisibility(boolean value) {
        if (value) {
            actionPanel.add(finishedButton);
        } else {
            actionPanel.remove(finishedButton);
        }
        actionPanel.revalidate();
        actionPanel.repaint();
    }

    /**
     * Set visibility of suggestion, accusation and pass button visibility
     *
     * @param value boolean
     * @author Vaibhav Ekambaram
     */
    public void setSuggestionAccusationVisibility(boolean value) {
        if (value) {
            keyTracker = KeyStates.DECISION;
            actionPanel.add(suggestionButton);
            actionPanel.add(accusationButton);
            actionPanel.add(passButton);
            suggestionButton.setVisible(true);
            accusationButton.setVisible(true);
            passButton.setVisible(true);
        } else {
            suggestionButton.setVisible(false);
            accusationButton.setVisible(false);
            passButton.setVisible(false);
            actionPanel.remove(suggestionButton);
            actionPanel.remove(accusationButton);
            actionPanel.remove(passButton);
        }
    }

    /**
     * Set visibility of movement control buttons
     *
     * @param value boolean
     * @author Cameron Li
     */
    public void showMovement(boolean value) {
        if (value) {
            keyTracker = KeyStates.MOVEMENT;
            upButton.setVisible(true);
            downButton.setVisible(true);
            leftButton.setVisible(true);
            rightButton.setVisible(true);
        } else {
            upButton.setVisible(false);
            downButton.setVisible(false);
            leftButton.setVisible(false);
            rightButton.setVisible(false);
        }
    }

    /**
     * Create Menu bar for GUI
     *
     * @return menu bar
     */
    private JMenuBar createTableMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createGameMenu());
        tableMenuBar.add(createDebugMenu());
        tableMenuBar.add(createHelpMenu());
        return tableMenuBar;
    }

    /**
     * Create Game Menu Dropdown in menu bar
     *
     * @return menu dropdown
     */
    private JMenu createGameMenu() {
        final JMenu gameMenu = new JMenu("Game");

        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> {
            int closeDialogButton = JOptionPane.YES_NO_OPTION;
            int closeDialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Warning", closeDialogButton);
            gameFrame.dispose();
            if (closeDialogResult == JOptionPane.YES_OPTION) System.exit(0);
        });
        gameMenu.add(exitMenuItem);
        return gameMenu;
    }

    /**
     * Create Debug Menu Dropdown in menu bar
     *
     * @return menu dropdown
     */
    private JMenu createDebugMenu() {
        final JMenu debugMenu = new JMenu("Debug");
        final JMenuItem scenarioMenuItem = new JMenuItem("Murder Scenario");
        scenarioMenuItem.addActionListener(e -> {

            String message;
            if (game.getMurderScenario() != null) {
                message = "[Character] " + game.getMurderScenario().getMurderer() + "\n[Weapon] " + game.getMurderScenario().getWeapon() + "\n[Room] " + game.getMurderScenario().getRoom();
            } else {
                message = "Game has not been loaded!";
            }
            JOptionPane.showMessageDialog(null, message, "Murder Scenario", JOptionPane.PLAIN_MESSAGE);
        });
        debugMenu.add(scenarioMenuItem);


        return debugMenu;
    }


    /**
     * Create Help Menu Dropdown in menu bar
     *
     * @return menu dropdown
     */
    private JMenu createHelpMenu() {
        final JMenu helpMenu = new JMenu("Help");
        final JMenuItem about = new JMenuItem("About");
        about.addActionListener(e -> JOptionPane.showMessageDialog(null, "Cluedo!\nSWEN225 Assignment 2\nA group project by:\nCameron Li Vaibhav Ekambaram Baxter Kirikiri", "About", JOptionPane.PLAIN_MESSAGE));
        helpMenu.add(about);
        return helpMenu;
    }

    /**
     * Draw players hand on screen
     *
     * @author Baxter Kirikiri
     */
    private void drawHand() {
        if (currentPlayer == null) return;
        if (previousPlayer != currentPlayer) {
            handPanel.removeAll();
            currentPlayer.getHand().forEach(c -> {
                JLabel handLabel = new JLabel(new ImageIcon(getClass().getResource("/resources/card_" + c.toString() + ".png")));
                handLabel.setToolTipText("Card Type: "+c.getClass().getSimpleName());
                handPanel.add(handLabel);
            });
            previousPlayer = currentPlayer;
        }
    }


    /**
     * Update all visual elements on the GUI
     * Buttons, Panels and Display
     *
     * @author Cameron Li
     */
    public void updateDisplay() {
        RECT_SIZE = displayPanel.getWidth() > displayPanel.getHeight() ? (displayPanel.getHeight() - BORDER_SIZE) / 25 : (displayPanel.getWidth() - BORDER_SIZE) / 25;

        if (game.getGameState().equals(Game.States.RUNNING)) {
            infoPanel.setVisible(true);
            if (game.getSubState().equals(MOVEMENT)) {
                if (game.getMovesRemaining() > 0) {
                    showMovement(true);
                }
                setSuggestionAccusationVisibility(false);
            } else {
                showMovement(false);
                setFinishedButtonVisibility(false);

            }
            if (game.getSubState().equals(MOVEMENT) || game.getSubState().equals(Game.subStates.ACTION)) {
                drawHand();
            }
        } else {
            showMovement(false);
            setSuggestionAccusationVisibility(false);
            infoPanel.setVisible(false);
        }
        if (game.getCurrentPlayer() != currentPlayer) {
            info.setText(game.getCurrentPlayer().toString() + "\n");
            currentPlayer = game.getCurrentPlayer();
            if (game.getCurrentPlayer().getCurrentPosition().getRoom() != null) {
                info.append(game.getCurrentPlayer().getCurrentPosition().getRoom().toString());
            }
        }

        if (game.getCurrentPlayer() == currentPlayer) {
            if (game.getMovesRemaining() != this.movesRemaining && game.getMovesRemaining() > 0) {
                this.movesRemaining = game.getMovesRemaining();
                info.setText(game.getCurrentPlayer().toString() + "\n");
                info.append(this.movesRemaining + " moves remaining\n");
                if (game.getCurrentPlayer().getCurrentPosition().getRoom() != null) {
                    info.append("Player is currently in " + game.getCurrentPlayer().getCurrentPosition().getRoom().toString());
                }
            } else if (game.getMovesRemaining() < 1) {
                info.setText(game.getCurrentPlayer().toString() + "\n");
                if (game.getCurrentPlayer().getCurrentPosition().getRoom() != null) {
                    info.append("Player is currently in " + game.getCurrentPlayer().getCurrentPosition().getRoom().toString());
                }
            }
        }

        paint((Graphics2D) displayPanel.getGraphics());
    }


    /**
     * Draw all the relevant Display Panel Elements
     * Grid Board, Players, Weapons
     *
     * @author Vaibhav Ekambaram
     */
    public void paint(Graphics2D g) {
        if (g != null) {

            int border = BORDER_SIZE / 2;
            for (int i = 0; i < BOARD_WIDTH; i++) {
                for (int j = 0; j < BOARD_HEIGHT; j++) {

                    Position currentPosition = game.getBoard().getPositions()[j][i];
                    int xValue = border + RECT_SIZE * i;
                    int yValue = border + RECT_SIZE * j;

                    currentPosition.draw(g, xValue, yValue, RECT_SIZE, displayPanel);
                    drawWalls(currentPosition, g, i, j, xValue, yValue);
                }
            }
        }
    }


    /**
     * Draw Walls on board display (sub-method of paint)
     *
     * @param currentPosition currentPosition
     * @param g2              Graphics
     * @param i               i position
     * @param j               j position
     * @param xValue          draw x value
     * @param yValue          draw y value
     * @author Vaibhav Ekambaram
     */
    public void drawWalls(Position currentPosition, Graphics2D g2, int i, int j, int xValue, int yValue) {
        g2.setColor(Color.DARK_GRAY);
        g2.setStroke(new BasicStroke(3));
        if (!currentPosition.isPassableTile() && currentPosition.getRoom() != null) {

            if (i == 0) {
                g2.drawLine(xValue, yValue, xValue, yValue + RECT_SIZE);
            }
            if (i == 23)
                g2.drawLine(xValue + RECT_SIZE - 2, yValue, xValue + RECT_SIZE - 2, yValue + RECT_SIZE);
            if (j == 24)
                g2.drawLine(xValue, yValue + RECT_SIZE, xValue + RECT_SIZE, yValue + RECT_SIZE);

            if (i > 0 && game.getBoard().getPositions()[j][i - 1].getRoom() == null) {
                g2.drawLine(xValue, yValue, xValue, yValue + RECT_SIZE);
            }
            if (i < 23 && game.getBoard().getPositions()[j][i + 1].getRoom() == null) {
                g2.drawLine(xValue + RECT_SIZE - 2, yValue, xValue + RECT_SIZE - 2, yValue + RECT_SIZE - 2);
            }
            if (j > 0 && game.getBoard().getPositions()[j - 1][i].getRoom() == null) {
                g2.drawLine(xValue, yValue, xValue + RECT_SIZE, yValue);
            }
            if (j < 24 && game.getBoard().getPositions()[j + 1][i].getRoom() == null) {
                g2.drawLine(xValue, yValue + RECT_SIZE - 2, xValue + RECT_SIZE - 2, yValue + RECT_SIZE - 2);
            }
        }
        if (currentPosition.isDoor()) {
            if (currentPosition.getDisplayName().equals("^") || currentPosition.getDisplayName().equals("v")) {

                if (game.getBoard().getPositions()[j][i + 1].getRoom() == null) {
                    g2.drawLine(xValue + RECT_SIZE - 2, yValue, xValue + RECT_SIZE - 2, yValue + RECT_SIZE - 2);
                }

                if (game.getBoard().getPositions()[j][i - 1].getRoom() == null) {
                    g2.drawLine(xValue, yValue, xValue, yValue + RECT_SIZE);
                }
            }
        }
    }


    /**
     * Set player count through option pane
     *
     * @return number of players
     * @author Vaibhav Ekambaram
     */
    public int setPlayerCount() {
        JPanel fields = new JPanel(new GridLayout(2, 1));
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"3", "4", "5", "6"});
        fields.add(new JLabel("How many players wish to play?"));
        fields.add(comboBox);
        JOptionPane.showMessageDialog(gameFrame, fields, "Game Startup Parameters", JOptionPane.PLAIN_MESSAGE);
        return Integer.parseInt(Objects.requireNonNull(comboBox.getSelectedItem()).toString());
    }

    /**
     * Set player vanity name and tokens through option pane
     *
     * @param characterNames    array of character name strings
     * @param numPlayers        number of players
     * @param players           list of players to write to
     * @param characterCardsMap map of characters
     * @return list of players
     * @author Vaibhav Ekambaram
     */
    public List<Player> setPlayers(String[] characterNames, int numPlayers, List<Player> players, Map<String, CharacterCard> characterCardsMap) {
        ArrayList<String> charNames = new ArrayList<>(Arrays.asList(characterNames));
        ArrayList<String> used = new ArrayList<>();

        // menu for each player to select options
        for (int i = 0; i < numPlayers; i++) {
            JPanel fields = new JPanel(new GridLayout(5, 2));
            JTextField nameField = new JTextField();
            nameField.setText("Player " + (i + 1));
            ButtonGroup buttonGroup = new ButtonGroup();
            fields.add(new JLabel("Enter your name then select your player token "));

            fields.add(nameField);

            // radio button for each token option
            charNames.forEach(character -> {
                JRadioButton radButton = new JRadioButton();
                if (used.contains(character)) radButton.setEnabled(false);
                radButton.setText(character);
                radButton.setActionCommand(character);
                buttonGroup.add(radButton);
                fields.add(radButton);
            });
            fields.add(new JLabel("Note: regardless of choice, players will start at token start, and the game will play clockwise around the board!"));

            JOptionPane.showMessageDialog(null, fields, "Set Player Preferences", JOptionPane.PLAIN_MESSAGE);

            // if valid selection then add to array of players, otherwise repeat
            if (buttonGroup.getSelection() != null || nameField.getText().length() == 0) {
                used.add(buttonGroup.getSelection().getActionCommand());
                players.add(new Player(characterCardsMap.get(buttonGroup.getSelection().getActionCommand()), nameField.getText()));
            } else {
                i--;
            }
        }
        return players;
    }


    /**
     * Move one tile in specified direction given by Button Press
     *
     * @param direction dir
     * @author Cameron Li
     */
    public void makeMovement(Move.Direction direction) {
        currentPlayer = game.getCurrentPlayer();
        game.movementInput(new Move(direction, 1));
    }

    /**
     * Move to a position on the board given by mouse click
     * Check if position is aligned via X-axis or Y-axis
     * If valid, commence the move and apply to board
     *
     * @param e event
     * @author Cameron Li
     */
    public void makeMouseMovement(MouseEvent e) {
        // Check Game Validity
        if (game.getBoard() == null || !game.getSubState().equals(MOVEMENT) || game.getMovesRemaining() < 1) {
            return;
        }

        movesRemaining = game.getMovesRemaining();

        // Find specified Position of Mouse Click
        double x = (e.getX() - (BORDER_SIZE / 2)) / RECT_SIZE;
        double y = (e.getY() - (BORDER_SIZE / 2)) / RECT_SIZE;
        Position select = game.getBoard().findNearest((int) x, (int) y);
        if (select != null) {
            Position currentPosition = game.getCurrentPlayer().getCurrentPosition();
            // Check X-axis or Y-axis Alignment
            if (currentPosition.checkAligned(select)) {
                // Find number of spaces to move
                int xDif = currentPosition.getLocationX() - select.getLocationX();
                int yDif = currentPosition.getLocationY() - select.getLocationY();

                // Check enough moves left to apply move
                if (Math.abs(xDif) > movesRemaining || Math.abs(yDif) > movesRemaining) {
                    return;
                }

                Move move;
                // Create new move depending on alignment and direction
                if (yDif == 0) {
                    move = xDif < 0 ? new Move(Move.Direction.RIGHT, Math.abs(xDif)) : new Move(Move.Direction.LEFT, xDif);
                } else {
                    move = yDif < 0 ? new Move(Move.Direction.DOWN, Math.abs(yDif)) : new Move(Move.Direction.UP, yDif);
                }
                // Apply Move
                game.movementInput(move);
            }
        }
    }

    public enum KeyStates {
        PRE_ROLL, MOVEMENT, DECISION
    }
}