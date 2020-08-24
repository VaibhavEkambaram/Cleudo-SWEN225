package View;

import Model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;
import java.util.Observable;


public class Table extends Observable {
    private final int BORDER_SIZE = 20;


    final JFrame gameFrame;
    public int numPlayers = -1;
    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    // Buttons
    JButton suggestionButton;
    private JPanel displayPanel;
    private JPanel handPanel;
    private JPanel actionPanel;
    // Panels
    private JPanel mainPanel;
    private JPanel infoPanel;
    // Display
    private Canvas display;
    private JTextArea info;
    private Font infoFont;
    JButton accusationButton;
    JButton passButton;

    public Table(Game game) {
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
        constraints.weighty = .8;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        actionPanel.setPreferredSize(new Dimension(200, 500));
        mainPanel.add(actionPanel, constraints);

        // Display Panel
        display = new Canvas();
        constraints.weightx = .8;
        constraints.weighty = .9;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 1;
        displayPanel = new JPanel();
        displayPanel.add(display);
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
        infoPanel.add(info, BorderLayout.LINE_START);
        infoFont = info.getFont();

        // Hand Panel
        handPanel = new JPanel();
        handPanel.setBackground(Color.WHITE);
        handPanel.setPreferredSize(new Dimension(500, 100));
        constraints.weightx = 1;
        constraints.weighty = .2;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        mainPanel.add(handPanel, constraints);

        // Borders
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        displayPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        handPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        actionPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        infoPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        actionPanel.setLayout(new GridLayout(12, 1));


        gameFrame.add(mainPanel);


        // Buttons
        suggestionButton = new JButton("Make Suggestion");
        actionPanel.add(suggestionButton);
        suggestionButton.setVisible(true);

        accusationButton = new JButton("Make Accusation");
        actionPanel.add(accusationButton);
        accusationButton.setVisible(true);

        passButton = new JButton("Pass");
        actionPanel.add(passButton);
        passButton.setVisible(true);


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

        gameFrame.setMinimumSize(new Dimension(750, 750));
        gameFrame.pack();
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

    public void updateDisplay() {

        int rows = 25;
        int columns = 24;
        int rectSize = 20;
        if (displayPanel.getWidth() > displayPanel.getHeight()) {
            rectSize = (displayPanel.getHeight() - BORDER_SIZE) / 25;
        } else {
            rectSize = (displayPanel.getWidth() - BORDER_SIZE) / 25;
        }

        //displayPanel.setPreferredSize(new Dimension(rows));
        Rectangle half = new Rectangle(0, 0, 50, 50);
        //paint(display.getGraphics());
        paint(displayPanel.getGraphics(), rectSize);
    }

    public void paint(Graphics g, int rectSize) {
        int border = BORDER_SIZE / 2;
        for (int i = 0; i < 25; i++) {
            for (int j = 0; j < 25; j++) {
                g.drawRect(border + rectSize * i, border + rectSize * j, rectSize, rectSize);
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
}
