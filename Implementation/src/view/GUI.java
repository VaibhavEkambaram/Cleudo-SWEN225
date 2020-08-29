package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.*;

import static model.Game.subStates.MOVEMENT;

public class GUI extends Observable {
    private final int BORDER_SIZE = 20;
    private int RECT_SIZE;

    final JFrame gameFrame;
    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(700, 700);

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

    private final JPanel infoPanel;
    private final JPanel displayPanel;
    private final JPanel handPanel;
    private final JPanel actionPanel;
    private final JPanel movementPanel;

    private ImageIcon firstDiceImage;
    private JLabel firstDiceImageLabel;
    private JLabel secondDiceImageLabel;

    private int movesRemaining;
    Player currentPlayer;
    Player previousPlayer;


    Graphics g;

    Image cachedWindow;
    Graphics2D cachedGraphics;


    final Game game;
    // Display
    private final JTextArea info;

    public GUI(Game game) {
        this.game = game;
        gameFrame = new JFrame("Cluedo");
        gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        gameFrame.setJMenuBar(tableMenuBar);
        gameFrame.setSize(OUTER_FRAME_DIMENSION);
        gameFrame.setVisible(true);

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
        displayPanel.setBackground(Color.WHITE);
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
        //scrollHandPane = new JScrollPane(handPanel);
        //scrollHandPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //scrollHandPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
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

        rollDiceButton = new JButton("Roll Dice");
        actionPanel.add(rollDiceButton);
        rollDiceButton.addActionListener(e -> {
            game.setMovesRemaining(-1);
            game.setMovesRemaining(game.rollDice());
            setRollDiceButtonVisibility(false);
            setFinishedButtonVisibility(true);
            movementPanel.setVisible(true);
        });


        // Buttons
        suggestionButton = new JButton("Make Suggestion");
        actionPanel.add(suggestionButton);
        suggestionButton.addActionListener(e -> {
            int suggest = game.makeSuggestion(game.getCurrentPlayer());
            if (suggest == -1) {
                game.movementTransition();
                setRollDiceButtonVisibility(true);
                createHand();
                firstDiceImageLabel.setVisible(false);
                secondDiceImageLabel.setVisible(false);
            }
        });


        accusationButton = new JButton("Make Accusation");
        actionPanel.add(accusationButton);
        accusationButton.addActionListener(e -> {
            int accuse = game.makeAccusation(game.getCurrentPlayer());
            if (accuse == 1) {
                game.finishTransition();
            } else {
                game.movementTransition();
                setRollDiceButtonVisibility(true);
                createHand();
                firstDiceImageLabel.setVisible(false);
                secondDiceImageLabel.setVisible(false);
            }
        });


        passButton = new JButton("Pass");
        actionPanel.add(passButton);
        passButton.addActionListener(e -> {
            game.movementTransition();
            setRollDiceButtonVisibility(true);

            createHand();
            firstDiceImageLabel.setVisible(false);
            secondDiceImageLabel.setVisible(false);
        });

        finishedButton = new JButton("Finished");
        finishedButton.addActionListener(e -> {
            game.actionTransition();
            setFinishedButtonVisibility(false);
            movementPanel.setVisible(false);
            setSuggestionAccusationVisibility(true);
        });


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

        gameFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                int closeDialogButton = JOptionPane.YES_NO_OPTION;
                int closeDialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Warning", closeDialogButton);
                if (closeDialogResult == JOptionPane.YES_OPTION) {
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
     * Java Swing Elements
     */


    public void RollDiceMenu(int firstNumber, int secondNumber) {
        firstDiceImage = new ImageIcon(getClass().getResource("/resources/dice_" + firstNumber + ".png"));
        firstDiceImageLabel = new JLabel(firstDiceImage);
        handPanel.add(firstDiceImageLabel);

        ImageIcon secondDiceImage = new ImageIcon(getClass().getResource("/resources/dice_" + secondNumber + ".png"));
        secondDiceImageLabel = new JLabel(secondDiceImage);
        firstDiceImageLabel.setVisible(true);
        secondDiceImageLabel.setVisible(true);
        handPanel.add(secondDiceImageLabel);
    }

    public void setRollDiceButtonVisibility(boolean value) {
        if (value) {
            actionPanel.add(rollDiceButton);
        } else {
            actionPanel.remove(rollDiceButton);
        }
        rollDiceButton.setVisible(value);
    }

    public void setFinishedButtonVisibility(boolean value) {
        if (value) {
            actionPanel.add(finishedButton);
        } else {
            actionPanel.remove(finishedButton);
        }
        actionPanel.revalidate();
        actionPanel.repaint();
    }

    public void setSuggestionAccusationVisibility(boolean value) {
        if (value) {
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

    public void showMovement(boolean value) {
        if (value) {
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

    private JMenuBar createTableMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createGameMenu());
        tableMenuBar.add(createDebugMenu());
        tableMenuBar.add(createHelpMenu());
        return tableMenuBar;
    }

    private JMenu createGameMenu() {
        final JMenu gameMenu = new JMenu("Game");

        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(e -> {
            int closeDialogButton = JOptionPane.YES_NO_OPTION;
            int closeDialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Warning", closeDialogButton);
            if (closeDialogResult == JOptionPane.YES_OPTION) System.exit(0);
        });
        gameMenu.add(exitMenuItem);
        return gameMenu;
    }
    private JMenu createDebugMenu(){
        final JMenu debugMenu = new JMenu("Debug");
        final JMenuItem scenarioMenuItem = new JMenuItem("Murder Scenario");
        scenarioMenuItem.addActionListener(e -> {

            String message;
            if(game.getMurderScenario()!=null){
                message = "[Character] "+game.getMurderScenario().getMurderer()+"\n[Weapon] "+game.getMurderScenario().getWeapon()+"\n[Room] "+game.getMurderScenario().getRoom();
            } else {
                message = "Game has not been loaded!";
            }
            JOptionPane.showMessageDialog(null, message, "Murder Scenario", JOptionPane.PLAIN_MESSAGE);
        });
        debugMenu.add(scenarioMenuItem);


        return debugMenu;
    }


    private JMenu createHelpMenu() {
        final JMenu helpMenu = new JMenu("Help");
        final JMenuItem about = new JMenuItem("About");
        about.addActionListener(e -> JOptionPane.showMessageDialog(null, "Cluedo!\nSWEN225 Assignment 2\nA group project by:\nCameron Li Vaibhav Ekambaram Baxter Kirikiri", "About", JOptionPane.PLAIN_MESSAGE));
        helpMenu.add(about);
        return helpMenu;
    }

    private void createHand() {
        if (currentPlayer == null) return;
        if (previousPlayer != currentPlayer) {
            handPanel.removeAll();
            currentPlayer.getHand().forEach(c -> {
                firstDiceImage = new ImageIcon(getClass().getResource("/resources/card_" + c.toString() + ".png"));
                firstDiceImageLabel = new JLabel(firstDiceImage);
                handPanel.add(firstDiceImageLabel);
            });

            previousPlayer = currentPlayer;
        }
    }


    /**
     * Update Visual Elements
     */

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
                createHand();
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

        g = displayPanel.getGraphics();

        paint((Graphics2D) g);

    }

    /*
    /**
     * Draw all the relevant Display Panel Elements
     * Grid Board, Players, Weapons
     *
     * @author Vaibhav Ekambaram
     */
    public void paint(Graphics2D g) {
        if (g != null) {

            int border = BORDER_SIZE / 2;

            int BOARD_WIDTH = 24;
            for (int i = 0; i < BOARD_WIDTH; i++) {
                int BOARD_HEIGHT = 25;
                for (int j = 0; j < BOARD_HEIGHT; j++) {

                    Position currentPosition = game.getBoard().getPositions()[j][i];
                    int xValue = border + RECT_SIZE * i;
                    int yValue = border + RECT_SIZE * j;

                    currentPosition.draw(g);
                    g.fillRect(xValue, yValue, RECT_SIZE, RECT_SIZE);

                    if (currentPosition.getCharacter() != null) {
                        g.setColor(currentPosition.getCharacter().getCharacterBoardColor());
                        g.fillOval(xValue, yValue, RECT_SIZE, RECT_SIZE);
                    }

                    if (currentPosition.getWeapon() != null) {
                        g.drawImage(resize(currentPosition.getWeapon().getWeaponImage(), RECT_SIZE - 4, RECT_SIZE - 4), xValue + 2, yValue + 2, displayPanel);
                    }

                    g.setColor(Color.BLACK);
                    g.setStroke(new BasicStroke(1));
                    g.drawRect(xValue, yValue, RECT_SIZE, RECT_SIZE);
                    drawWalls(currentPosition, g, i, j, xValue, yValue);
                }
            }
        }

    }



    public void drawWalls(Position currentPosition, Graphics2D g2, int i, int j, int xValue, int yValue) {
        if (!currentPosition.isPassableTile() && currentPosition.getRoom() != null) {
            g2.setColor(Color.DARK_GRAY);
            g2.setStroke(new BasicStroke(2));


            if (i == 0) {
                g2.drawLine(xValue, yValue, xValue, yValue + RECT_SIZE);
                g2.drawLine(xValue, yValue, xValue + RECT_SIZE, yValue);
            }
            if (i == 23)
                g2.drawLine(xValue + RECT_SIZE, yValue, xValue + RECT_SIZE, yValue + RECT_SIZE);
            if (j == 24)
                g2.drawLine(xValue, yValue + RECT_SIZE, xValue + RECT_SIZE, yValue + RECT_SIZE);

            if (i > 0 && game.getBoard().getPositions()[j][i - 1].getRoom() == null) {
                g2.drawLine(xValue, yValue, xValue, yValue + RECT_SIZE);
            }
            if (i < 23 && game.getBoard().getPositions()[j][i + 1].getRoom() == null) {
                g2.drawLine(xValue + RECT_SIZE, yValue, xValue + RECT_SIZE, yValue + RECT_SIZE);
            }
            if (j > 0 && game.getBoard().getPositions()[j - 1][i].getRoom() == null) {
                g2.drawLine(xValue, yValue, xValue + RECT_SIZE, yValue);
            }
            if (j < 24 && game.getBoard().getPositions()[j + 1][i].getRoom() == null) {
                g2.drawLine(xValue, yValue + RECT_SIZE, xValue + RECT_SIZE, yValue + RECT_SIZE);
            }
        }

        if (currentPosition.isDoor()) {
            g2.setColor(Color.DARK_GRAY);
            g2.setStroke(new BasicStroke(2));

            if (currentPosition.getDisplayName().equals("^") || currentPosition.getDisplayName().equals("v")) {

                if (game.getBoard().getPositions()[j][i + 1].getRoom() == null) {
                    g2.drawLine(xValue + RECT_SIZE, yValue, xValue + RECT_SIZE, yValue + RECT_SIZE);
                }

                if (game.getBoard().getPositions()[j][i - 1].getRoom() == null) {
                    g2.drawLine(xValue, yValue, xValue, yValue + RECT_SIZE);
                }
            }
        }
    }


    private static BufferedImage resize(BufferedImage img, int height, int width) {
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(img.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
        g2d.dispose();
        return resized;
    }


    /**
     * Setup Helpers
     */

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

            // create radio button for each token option
            charNames.forEach(character -> {
                JRadioButton radButton = new JRadioButton();
                if (used.contains(character)) radButton.setEnabled(false);
                radButton.setText(character);
                radButton.setActionCommand(character);
                buttonGroup.add(radButton);
                fields.add(radButton);
            });

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
     * Movement
     */

    /**
     * Move one tile in specified direction given by Button Press
     *
     * @param direction
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
        if (game.getBoard() == null) {
            return;
        }
        if (!game.getSubState().equals(MOVEMENT)) {
            return;
        }
        if (game.getMovesRemaining() < 1) {
            return;
        }
        movesRemaining = game.getMovesRemaining();

        // Find specified Position of Mouse Click
        double x = (e.getX() - (BORDER_SIZE / 2)) / RECT_SIZE;
        double y = (e.getY() - (BORDER_SIZE / 2)) / RECT_SIZE;
        Position select = game.getBoard().findNearest((int) x, (int) y);
        if (select != null) {
            game.setSelectedTile((int) x, (int) y);
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
                    if (xDif < 0) {
                        move = new Move(Move.Direction.RIGHT, Math.abs(xDif));
                    } else {
                        move = new Move(Move.Direction.LEFT, xDif);
                    }
                } else {
                    if (yDif < 0) {
                        move = new Move(Move.Direction.DOWN, Math.abs(yDif));
                    } else {
                        move = new Move(Move.Direction.UP, yDif);
                    }
                }
                // Apply Move
                game.movementInput(move);
            }
        }
    }
}