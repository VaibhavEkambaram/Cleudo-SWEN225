package view;


import model.Game;
import model.Move;
import model.Player;
import model.Position;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;

import static model.Game.subStates.MOVEMENT;

/**
 * Class for GUI Functionality
 *
 * @author Vaibhav Ekambaram, Cameron Li
 */
public class GUI extends Observable {

    // Constants
    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(800, 800);
    private final int BOARD_WIDTH = 24;
    private final int BOARD_HEIGHT = 25;
    private final int BORDER_SIZE = 20;
    private int RECT_SIZE;

    // Frames
    public final JFrame gameFrame;

    // Panels
    private final JPanel infoPanel;
    private final JPanel displayPanel;
    private final JPanel handPanel;
    private final JPanel actionPanel;
    private final JPanel dicePanel;

    // Buttons
    private final JButton suggestionButton;
    private final JButton accusationButton;
    private final JButton passButton;
    private final JButton rollDiceButton;
    private final JButton finishedButton;

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

    public KeyStates keyTracker = KeyStates.PRE_ROLL;


    public enum KeyStates {
        PRE_ROLL, MOVEMENT, DECISION
    }


    /**
     * User Interface Constructor
     *
     * @param game game
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
        actionPanel = new JPanel(new GridLayout(8, 1));
        actionPanel.setBackground(Color.WHITE);
        constraints.weightx = .2;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        actionPanel.setPreferredSize(new Dimension(250, 600));
        mainPanel.add(actionPanel, constraints);

        // Display Panel
        constraints.weightx = .8;
        constraints.weighty = 1;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 1;
        displayPanel = new JPanel(new BorderLayout());
        displayPanel.setBackground(new Color(97, 185, 125));
        displayPanel.setPreferredSize(new Dimension(500, 500));
        displayPanel.setDoubleBuffered(true);
        mainPanel.setDoubleBuffered(true);
        mainPanel.add(displayPanel, constraints);

        // Movement Panel
        /*
        movementPanel = new JPanel(new BorderLayout());
        movementPanel.setBackground(Color.WHITE);
        constraints.weightx = .2;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridheight = 1;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(movementPanel, constraints);

         */
        dicePanel = new JPanel(new GridLayout(0, 2));
        dicePanel.setBackground(Color.WHITE);
        dicePanel.setBorder(new EmptyBorder(5, 0, 5, 0));


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
        infoPanel.setPreferredSize(new Dimension(500, 90));

        // Hand Panel
        handPanel = new JPanel(new GridLayout(1, 0));
        handPanel.setBackground(Color.WHITE);

        constraints.weightx = 1;
        constraints.weighty = 0.2;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        JScrollPane scrollPanel = new JScrollPane(handPanel);
        scrollPanel.setPreferredSize(new Dimension(700, 183));
        scrollPanel.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        mainPanel.add(scrollPanel, constraints);

        // Borders
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        displayPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        handPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        actionPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        infoPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));


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
        rollDiceButton.addActionListener(e -> onRollDice());

        // Finished Button
        finishedButton = new JButton("Finished [f]");
        actionPanel.add(finishedButton);
        finishedButton.addActionListener(e -> onFinish());

        // Suggestion Button
        suggestionButton = new JButton("Make Suggestion [s]");
        actionPanel.add(suggestionButton);
        suggestionButton.addActionListener(e -> onSuggestion());

        // Accusation Button
        accusationButton = new JButton("Make Accusation [a]");
        actionPanel.add(accusationButton);
        accusationButton.addActionListener(e -> onAccusation());

        // Pass Button
        passButton = new JButton("Pass [p]");
        actionPanel.add(passButton);
        passButton.addActionListener(e -> onPass());



        suggestionButton.setFocusable(false);
        accusationButton.setFocusable(false);
        passButton.setFocusable(false);
        rollDiceButton.setFocusable(false);
        finishedButton.setFocusable(false);
        dicePanel.setFocusable(false);
        if(firstDiceImageLabel!=null) {
            firstDiceImageLabel.setFocusable(false);
        }

        if(secondDiceImageLabel!=null) {
            secondDiceImageLabel.setFocusable(false);
        }


        gameFrame.setFocusable(true);
        gameFrame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // not doing anything
            }

            /**
             * Key-Press Shortcuts - Uses keyTracker states
             * @param e key event
             */
            @Override
            public void keyPressed(KeyEvent e) {
                if (keyTracker.equals(KeyStates.PRE_ROLL)) {
                    if (e.getKeyCode() == 82) {
                        onRollDice();
                    }
                } else if (keyTracker.equals(KeyStates.MOVEMENT)) {
                    switch (e.getKeyCode()) {
                        case 70:
                            onFinish();
                            break;
                        case 38:
                            makeMovement(Move.Direction.UP);
                            break;
                        case 40:
                            makeMovement(Move.Direction.DOWN);
                            break;
                        case 37:
                            makeMovement(Move.Direction.LEFT);
                            break;
                        case 39:
                            makeMovement(Move.Direction.RIGHT);
                            break;
                    }
                } else if (keyTracker.equals(KeyStates.DECISION)) {
                    switch (e.getKeyCode()) {
                        case 83:
                            onSuggestion();
                            break;
                        case 65:
                            onAccusation();
                            break;
                        case 80:
                            onPass();
                            break;
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
                updateDisplay();
            }

            public void componentMoved(ComponentEvent componentEvent) {
                updateDisplay();
            }

            public void componentShown(ComponentEvent componentEvent) {
                updateDisplay();
            }

            public void componentHidden(ComponentEvent componentEvent) {
                updateDisplay();
            }
        });

        gameFrame.setMinimumSize(new Dimension(800, 800));
        gameFrame.pack();

        setRollDiceButtonVisibility(false);
        setSuggestionAccusationVisibility(false);
        setFinishedButtonVisibility(false);
        updateDisplay();
    }


    /**
     * Execute Dice Roll Action for Button
     */
    public void onRollDice() {
        game.rollDice();
        movesRemaining = game.getMovesRemaining();
        updateDisplay();
    }


    /**
     * Execute Action for Finish Button
     */
    public void onFinish() {
        game.actionTransition();
        updateDisplay();
    }

    /**
     * Execute Action for Suggestion Button
     */
    public void onSuggestion() {
        int suggest = game.makeSuggestion(game.getCurrentPlayer(), null, null, -1);
        if (suggest == -1) {
            game.movementTransition();
        }
        updateDisplay();
    }

    /**
     * Execute Action for Accusation Button
     */
    public void onAccusation() {
        int accuse = game.makeAccusation(game.getCurrentPlayer(), null);
        if (accuse == 1) {
            game.finishTransition();
        } else {
            game.movementTransition();
        }
        updateDisplay();
    }

    /**
     * Execute Action for Pass Button
     */
    public void onPass() {
        game.movementTransition();
        updateDisplay();
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

    }

    /**
     * Set visibility of dice roll button
     *
     * @param value boolean
     * @author Vaibhav Ekambaram
     */
    public void setRollDiceButtonVisibility(boolean value) {
        if (value) {
            setFinishedButtonVisibility(false);
            keyTracker = KeyStates.PRE_ROLL;
            actionPanel.add(rollDiceButton);
            rollDiceButton.setVisible(true);
        } else {
            rollDiceButton.setVisible(false);
            actionPanel.remove(rollDiceButton);
        }
    }

    /**
     * Set visibility of finished button
     *
     * @param value boolean
     * @author Vaibhav Ekambaram
     */
    public void setFinishedButtonVisibility(boolean value) {
        if (value) {
            keyTracker = KeyStates.MOVEMENT;
            actionPanel.add(finishedButton);
            finishedButton.setVisible(true);
        } else {
            finishedButton.setVisible(false);
            actionPanel.remove(finishedButton);
        }
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
                handLabel.setToolTipText("Card Type: " + c.getClass().getSimpleName());
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
            currentPlayer = game.getCurrentPlayer();
            if (currentPlayer != null) {
                drawHand();
                info.setText(currentPlayer.toString() + "\n");
                if (currentPlayer.getCurrentPosition().getRoom() != null) {
                    info.append("Currently in Room: " + currentPlayer.getCurrentPosition().getRoom().toString() + "\n");
                }
                if (game.getSubState().equals(Game.subStates.MOVEMENT)) {
                    setSuggestionAccusationVisibility(false);
                    if (game.getMovesRemaining() > 0) {
                        info.append(game.getMovesRemaining() + " moves remaining\n\n");
                        info.append("Click on board or use arrow keys to move\n");
                        setRollDiceButtonVisibility(false);
                        setFinishedButtonVisibility(true);
                        firstDiceImageLabel.setVisible(true);
                        secondDiceImageLabel.setVisible(true);
                        dicePanel.add(firstDiceImageLabel);
                        dicePanel.add(secondDiceImageLabel);
                        actionPanel.add(dicePanel);
                    } else {
                        setRollDiceButtonVisibility(true);
                    }
                } else if (game.getSubState().equals(Game.subStates.ACTION)) {
                    dicePanel.removeAll();
                    actionPanel.remove(dicePanel);
                    setSuggestionAccusationVisibility(true);
                    setFinishedButtonVisibility(false);
                    setRollDiceButtonVisibility(false);

                }
            }
        }
        paint();
    }


    /**
     * Draw all the relevant Display Panel Elements
     * Grid Board, Players, Weapons
     *
     * @author Vaibhav Ekambaram
     */
    public void paint() {
        if (game.getBoard() == null) {
            return;
        }
        Graphics2D g = (Graphics2D) displayPanel.getGraphics();
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

            if(j==0){
                g2.drawLine(xValue, yValue, xValue+RECT_SIZE, yValue);
            }

            if (i == 23)
                g2.drawLine(xValue + RECT_SIZE, yValue, xValue + RECT_SIZE, yValue + RECT_SIZE);
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
     * Move one tile in specified direction given by Button Press
     *
     * @param direction dir
     * @author Cameron Li
     */
    public void makeMovement(Move.Direction direction) {
        currentPlayer = game.getCurrentPlayer();
        game.movementInput(new Move(direction, 1));
        updateDisplay();
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
        updateDisplay();
    }


}