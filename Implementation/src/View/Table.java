package View;

import Model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.*;

import static Model.Game.subStates.MOVEMENT;


public class Table extends Observable {
    private final int BORDER_SIZE = 20;
    private int RECT_SIZE;

    final JFrame gameFrame;
    public int numPlayers = -1;
    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);

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

    // Panels
    private final JPanel mainPanel;
    private final JPanel infoPanel;
    private final JPanel displayPanel;
    private final JPanel handPanel;
    private final JPanel actionPanel;
    private final JPanel movementPanel;

    private ImageIcon image1;
    private JLabel label1;
    private ImageIcon image2;
    private JLabel label2;


    // Scroll Pane
    private JScrollPane scrollHandPane;

    private int movesRemaining;
    Player currentPlayer;
    Player previousPlayer;
    private final Font infoFont;

    Point p1;
    Point p2;

    final Game game;
    // Display
    private final JTextArea info;

    public Table(Game game) {
        this.game = game;
        gameFrame = new JFrame("Cluedo");
        gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        gameFrame.setJMenuBar(tableMenuBar);
        gameFrame.setSize(OUTER_FRAME_DIMENSION);
        gameFrame.setVisible(true);
        //gameFrame.setPreferredSize(new Dimension(600, 750));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;

        mainPanel = new JPanel(new GridBagLayout());

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
        displayPanel.setPreferredSize(new Dimension(500, 500));
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
        infoFont = info.getFont();
        infoPanel.setPreferredSize(new Dimension(500, 50));

        // Hand Panel
        handPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        handPanel.setBackground(Color.WHITE);
        handPanel.setPreferredSize(new Dimension(600, 183));
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
        //scrollHandPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        actionPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        infoPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        movementPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        actionPanel.setLayout(new GridLayout(12, 2));


        gameFrame.add(mainPanel);

        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                if (!game.getSubState().equals(MOVEMENT)) {
                    return;
                }

                p2 = new Point(e.getX(), e.getY());
                // todo: add method to board class to find closest???

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
                createHand(game);
                label1.setVisible(false);
                label2.setVisible(false);
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
                createHand(game);
                label1.setVisible(false);
                label2.setVisible(false);
            }
        });


        passButton = new JButton("Pass");
        actionPanel.add(passButton);
        passButton.addActionListener(e -> {
            game.movementTransition();
            setRollDiceButtonVisibility(true);
            createHand(game);
            label1.setVisible(false);
            label2.setVisible(false);
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
                if (game.getBoard() != null) {
                    updateDisplay();
                }
            }
        });

        gameFrame.setMinimumSize(new Dimension(750, 750));
        gameFrame.pack();

        setSuggestionAccusationVisibility(false);
        showMovement(false);
        //updateDisplay();
    }


    public Frame getGameFrame() {
        return gameFrame;
    }

    public void RollDiceMenu(int firstNumber, int secondNumber) {


        image1 = new ImageIcon(getClass().getResource("/resources/dice_" + firstNumber + ".png"));
        label1 = new JLabel(image1);
        handPanel.add(label1);

        image2 = new ImageIcon(getClass().getResource("/resources/dice_" + secondNumber + ".png"));
        label2 = new JLabel(image2);
        label1.setVisible(true);
        label2.setVisible(true);
        handPanel.add(label2);
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

    private JMenu createHelpMenu() {
        final JMenu helpMenu = new JMenu("Help");
        final JMenuItem about = new JMenuItem("About");
        about.addActionListener(e -> JOptionPane.showMessageDialog(null, "Cluedo!\nSWEN225 Assignment 2\nA group project by:\nCameron Li Vaibhav Ekambaram Baxter Kirikiri", "About", JOptionPane.PLAIN_MESSAGE));
        helpMenu.add(about);
        return helpMenu;
    }

    private void createHand(Game game) {
        // TODO: make the hand panel scrollable so the player can actually see all of their cards
        if (currentPlayer == null) {
            return;
        }

        if (previousPlayer != currentPlayer) {
            handPanel.removeAll();
            for (Card c : currentPlayer.getHand()) {


                image1 = new ImageIcon(getClass().getResource("/resources/card_" + c.toString() + ".png"));
                label1 = new JLabel(image1);
                handPanel.add(label1);

/*
                //System.out.println(c.toString());
                BufferedImage card = null;
                try {
                    card = ImageIO.read(new File("resources/card_" + c.toString() + ".png"));
                } catch (IOException e) {

                }
                JLabel picLabel = new JLabel(new ImageIcon(card));
        */
                //  handPanel.add(picLabel);
            }
            //scrollHandPane = new JScrollPane(handPanel);
            previousPlayer = currentPlayer;
        }
    }

    /**
     * Update all visual elements on the GUI
     *
     * @author Cameron Li
     */
    public void updateDisplay() {
        int rectSize;
        if (displayPanel.getWidth() > displayPanel.getHeight()) {
            rectSize = (displayPanel.getHeight() - BORDER_SIZE) / 25;
        } else {
            rectSize = (displayPanel.getWidth() - BORDER_SIZE) / 25;
        }

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
                createHand(game);
            }
        } else {
            showMovement(false);
            setSuggestionAccusationVisibility(false);
            infoPanel.setVisible(false);
        }
        if (game.getCurrentPlayer() != currentPlayer) {
            info.setText(game.getCurrentPlayer().toString() + "\n");
            currentPlayer = game.getCurrentPlayer();
        }

        if (game.getCurrentPlayer() == currentPlayer) {
            if (game.getMovesRemaining() != this.movesRemaining && game.getMovesRemaining() > 0) {
                this.movesRemaining = game.getMovesRemaining();
                info.setText(game.getCurrentPlayer().toString() + "\n");
                info.append(this.movesRemaining + " moves remaining");
            } else if (game.getMovesRemaining() < 1) {
                info.setText(game.getCurrentPlayer().toString() + "\n");
            }
        }

        paint(displayPanel.getGraphics(), rectSize);
    }

    public void paint(Graphics g, int rectSize) {
        if (g != null) {
            Graphics2D g2 = (Graphics2D) g;
            int border = BORDER_SIZE / 2;
            for (int i = 0; i < 24; i++) {
                for (int j = 0; j < 25; j++) {

                    game.getBoard().getPositions()[j][i].draw(g);
                    g.fillRect(border + rectSize * i, border + rectSize * j, rectSize, rectSize);


                    if (game.getBoard().getPositions()[j][i].getCharacter() != null) {
                        g.setColor(game.getBoard().getPositions()[j][i].getCharacter().getCharacterBoardColor());
                        g.fillOval(border + rectSize * i, border + rectSize * j, rectSize, rectSize);
                    }

                    if (game.getBoard().getPositions()[j][i].getWeapon() != null) {
                        g2.drawImage(resize(game.getBoard().getPositions()[j][i].getWeapon().getWeaponImage(), rectSize, rectSize), border + rectSize * i, border + rectSize * j, null);

                    }


                    if (!game.getBoard().getPositions()[j][i].isPassableTile() && game.getBoard().getPositions()[j][i].getRoom() != null) {
                        g.setColor(Color.BLACK);


                        g2.setStroke(new BasicStroke(2));

                        int iValue = border + rectSize * i;
                        int jValue = border + rectSize * j;


                        if(i==0){
                            g2.drawLine(iValue, jValue, iValue, jValue + rectSize);
                        }

                        if(i==23){
                            g2.drawLine(iValue + rectSize - 1, jValue, iValue + rectSize - 1, jValue + rectSize);
                        }

                        if(j==24){
                            g2.drawLine(iValue, jValue + rectSize - 1, iValue + rectSize, jValue + rectSize - 1);
                        }




                        if (i > 0 && game.getBoard().getPositions()[j][i - 1].getRoom() == null) {
                            g2.drawLine(iValue, jValue, iValue, jValue + rectSize);
                        }
                        if (i < 23 && game.getBoard().getPositions()[j][i + 1].getRoom() == null) {
                            g2.drawLine(iValue + rectSize - 1, jValue, iValue + rectSize - 1, jValue + rectSize);
                        }

                        if (j > 0 && game.getBoard().getPositions()[j - 1][i].getRoom() == null) {
                            g2.drawLine(iValue, jValue, iValue + rectSize, jValue);
                        }
                        if (j < 23 && game.getBoard().getPositions()[j + 1][i].getRoom() == null) {
                            g2.drawLine(iValue, jValue + rectSize - 1, iValue + rectSize, jValue + rectSize - 1);
                        }


                    }


                    g.setColor(Color.BLACK);
                    g2.setStroke(new BasicStroke(1));
                    // g.drawRect(border + rectSize * i, border + rectSize * j, rectSize, rectSize);
                }
            }
        }

    }


    private static BufferedImage resize(BufferedImage img, int height, int width) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }


    public int setPlayerCount() {
        JPanel fields = new JPanel(new GridLayout(2, 1));
        JLabel label = new JLabel("How many players wish to play?");
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"3", "4", "5", "6"});
        fields.add(label);
        fields.add(comboBox);
        JOptionPane.showMessageDialog(gameFrame, fields, "Game Startup Parameters", JOptionPane.PLAIN_MESSAGE);
        numPlayers = Integer.parseInt(Objects.requireNonNull(comboBox.getSelectedItem()).toString());
        return numPlayers;
    }

    public List<Player> setPlayers(String[] characterNames, int numPlayers, List<Player> players, Map<String, CharacterCard> characterCardsMap) {
        ArrayList<String> charNames = new ArrayList<>(Arrays.asList(characterNames));
        ArrayList<String> used = new ArrayList<>();

        // menu for each player to select options
        for (int i = 0; i < numPlayers; i++) {
            JPanel fields = new JPanel(new GridLayout(5, 2));
            JLabel label = new JLabel("Enter your name then select your player token ");
            JTextField nameField = new JTextField();
            nameField.setText("Player " + (i + 1));
            ButtonGroup b = new ButtonGroup();
            fields.add(label);
            fields.add(nameField);

            // create radio button for each token option
            for (String character : charNames) {
                JRadioButton radButton = new JRadioButton();
                if (used.contains(character)) {
                    radButton.setEnabled(false);
                }
                radButton.setText(character);
                radButton.setActionCommand(character);
                b.add(radButton);
                fields.add(radButton);
            }

            JOptionPane.showMessageDialog(null, fields, "Set Player Preferences", JOptionPane.PLAIN_MESSAGE);

            // if valid selection then add to array of players, otherwise repeat
            if (b.getSelection() != null || nameField.getText().length() == 0) {
                used.add(b.getSelection().getActionCommand());
                players.add(new Player(characterCardsMap.get(b.getSelection().getActionCommand()), nameField.getText()));
            } else {
                i--;
            }
        }
        return players;
    }


    public void makeMovement(Move.Direction direction) {
        currentPlayer = game.getCurrentPlayer();
        Move move = new Move(direction, 1);
        game.movementInput(move);
    }
}