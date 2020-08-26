package View;

import Model.Card;
import Model.Game;
import Model.Move;
import Model.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Observable;


public class Table extends Observable {
    private final int BORDER_SIZE = 20;


    final JFrame gameFrame;
    public int numPlayers = -1;
    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);

    // Buttons
    JButton suggestionButton;
    JButton accusationButton;
    JButton passButton;
    JButton upButton;
    JButton downButton;
    JButton leftButton;
    JButton rightButton;
    JButton rollDiceButton;

    // Panels
    private JPanel mainPanel;
    private JPanel infoPanel;
    private JPanel displayPanel;
    private JPanel handPanel;
    private JPanel actionPanel;
    private JPanel movementPanel;

    Player currentPlayer;
    private Font infoFont;

    Game game;
    // Display
    private JTextArea info;

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
        displayPanel.setBackground(Color.BLACK);
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
        handPanel.setPreferredSize(new Dimension(500, 183));
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
        actionPanel.setLayout(new GridLayout(12, 1));


        gameFrame.add(mainPanel);

        rollDiceButton = new JButton("Roll Dice");
        actionPanel.add(rollDiceButton);
        rollDiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.setMovesRemaining(-1);
                game.setMovesRemaining(game.rollDice());
                rollDiceButton.setVisible(false);
            }
        });


        // Buttons
        suggestionButton = new JButton("Make Suggestion");
        actionPanel.add(suggestionButton);

        accusationButton = new JButton("Make Accusation");
        actionPanel.add(accusationButton);

        passButton = new JButton("Pass");
        actionPanel.add(passButton);
        passButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.movementTransition();
                rollDiceButton.setVisible(true);
            }
        });


        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = .3;
        constraints.gridx = 1;
        constraints.gridy = 0;
        upButton = new JButton("Up");
        movementPanel.add(upButton, constraints);
        upButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeMovement(Move.Direction.UP);
            }
        });

        constraints.gridx = 1;
        constraints.gridy = 1;
        downButton = new JButton("Down");
        movementPanel.add(downButton, constraints);
        downButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeMovement(Move.Direction.DOWN);
            }
        });

        constraints.gridx = 0;
        constraints.gridy = 1;
        leftButton = new JButton("Left");
        movementPanel.add(leftButton, constraints);
        leftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeMovement(Move.Direction.LEFT);
            }
        });

        constraints.gridx = 2;
        constraints.gridy = 1;
        rightButton = new JButton("Right");
        movementPanel.add(rightButton, constraints);
        rightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeMovement(Move.Direction.RIGHT);
            }
        });

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

    public void setSuggestionAccusationVisibility(boolean value) {
        if (value) {
            suggestionButton.setVisible(true);
            accusationButton.setVisible(true);
            passButton.setVisible(true);
        } else {
            suggestionButton.setVisible(false);
            accusationButton.setVisible(false);
            passButton.setVisible(false);
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
        final JMenuItem setupGameMenuItem = new JMenuItem("Setup Game");
        gameMenu.add(setupGameMenuItem);

        setupGameMenuItem.addActionListener(e -> {
            //setPlayerCount();
            //new Game();

        });


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
        about.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Cluedo!\nSWEN225 Assignment 2\nA group project by:\nCameron Li Vaibhav Ekambaram Baxter Kirikiri", "About", JOptionPane.PLAIN_MESSAGE);
        });
        helpMenu.add(about);
        return helpMenu;
    }

    private void createHand(Game game){
        //TODO: make it so the hand updates to match current players

        Player currentPlayer = game.getCurrentPlayer();
        if(currentPlayer == null){
            return;
        }

        for(Card c : currentPlayer.getHand()){
            BufferedImage card = null;
            try {
                card = ImageIO.read(new File("assets/cards/card_"+c.toString()+".png"));
            } catch (IOException e) {

            }
            JLabel picLabel = new JLabel(new ImageIcon(card));
            handPanel.add(picLabel);
        }
    }

    public void updateDisplay() {
        int rectSize;
        if (displayPanel.getWidth() > displayPanel.getHeight()) {
            rectSize = (displayPanel.getHeight() - BORDER_SIZE) / 25;
        } else {
            rectSize = (displayPanel.getWidth() - BORDER_SIZE) / 25;
        }

        if (game.getGameState().equals(Game.States.RUNNING)) {
            infoPanel.setVisible(true);
            if (game.getSubState().equals(Game.subStates.MOVEMENT)) {
                if (game.getMovesRemaining() > 0) {
                    showMovement(true);
                }
                setSuggestionAccusationVisibility(false);
            } else {
                showMovement(false);
                setSuggestionAccusationVisibility(true);
            }
        } else {
            showMovement(false);
            setSuggestionAccusationVisibility(false);
            infoPanel.setVisible(false);
        }
        //if (game.getCurrentPlayer() != currentPlayer) {
            info.setText(game.getGameState().toString() + "\n");
            info.append(game.getSubState().toString() + "\n");
            info.append(game.getCurrentPlayer().toString());
            currentPlayer = game.getCurrentPlayer();
        //}
        createHand(game);

        Rectangle half = new Rectangle(0, 0, 50, 50);
        paint(displayPanel.getGraphics(), rectSize);
    }

    public void paint(Graphics g, int rectSize) {
        Graphics2D g2 = (Graphics2D) g;
        int border = BORDER_SIZE / 2;
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 25; j++) {

                game.getBoard().getPositions()[j][i].draw(g);
                g.fillRect(border + rectSize * i, border + rectSize * j, rectSize, rectSize);

                if(game.getBoard().getPositions()[j][i].getCharacter()!=null){
                    switch (game.getBoard().getPositions()[j][i].getCharacter().toString()) {
                        case "Miss Scarlett":
                            g.setColor(Color.RED);
                            break;
                        case "Col. Mustard":
                            g.setColor(Color.YELLOW);
                            break;
                        case "Mrs. White":
                            g.setColor(Color.WHITE);
                            break;
                        case "Mr. Green":
                            g.setColor(Color.GREEN);
                            break;
                        case "Mrs. Peacock":
                            g.setColor(Color.BLUE);
                            break;
                        case "Prof. Plum":
                            g.setColor(new Color(128, 0, 128));
                            break;
                    }

                    g.fillOval(border+rectSize*i,border+rectSize*j,rectSize,rectSize);
                }


                g.setColor(Color.BLACK);

                g.drawRect(border + rectSize * i, border + rectSize * j, rectSize, rectSize);
                /*
                if(game.getBoard().getPositions()[j][i].getDisplayName().equals("o") || game.getBoard().getPositions()[j][i].getDisplayName().equals("o")){
                    g2.setStroke(new BasicStroke(5));
                    if((!game.getBoard().getPositions()[j][i+1].getDisplayName().equals("O") || !game.getBoard().getPositions()[j][i+1].getDisplayName().equals("O"))) {
                        g2.drawLine(border + rectSize * i + rectSize, border + rectSize * j, border + rectSize * i + rectSize, border + rectSize * j + rectSize);
                    }
                }

                 */
            }
        }
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

    public void makeMovement(Move.Direction direction) {
        currentPlayer = game.getCurrentPlayer();
        Move move = new Move(direction, 1);
        game.movementInput(move);
    }
}