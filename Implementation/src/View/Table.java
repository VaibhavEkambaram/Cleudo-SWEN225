package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;
import java.util.Observable;


public class Table extends Observable {
    final JFrame gameFrame;
    public int numPlayers = -1;
    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    private Canvas display;
    private JPanel mainPanel;
    private JPanel displayPanel;
    private JPanel handPanel;
    private JPanel actionPanel;

    public Table() {
        gameFrame = new JFrame("Cluedo");
        gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        gameFrame.setJMenuBar(tableMenuBar);
        gameFrame.setSize(OUTER_FRAME_DIMENSION);
        gameFrame.setVisible(true);
        //gameFrame.setPreferredSize(new Dimension(600, 750));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;

        mainPanel = new JPanel(new GridBagLayout());

        actionPanel = new JPanel();
        constraints.weighty = .8;
        constraints.gridx = 0;
        constraints.gridy = 0;
        actionPanel.setPreferredSize(new Dimension(200, 500));

        mainPanel.add(actionPanel, constraints);

        display = new Canvas();


        constraints.weightx = .8;
        constraints.weighty = .8;
        constraints.gridx = 1;
        constraints.gridy = 0;

        displayPanel = new JPanel();
        displayPanel.add(display);
        displayPanel.setPreferredSize(new Dimension(500, 500));

        //mainPanel.add(display, constraints);
        mainPanel.add(displayPanel, constraints);

        handPanel = new JPanel();
        handPanel.setPreferredSize(new Dimension(500, 100));
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        displayPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        handPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        actionPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        constraints.weightx = 1;
        constraints.weighty = .2;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        mainPanel.add(handPanel, constraints);
        gameFrame.add(mainPanel);

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

        gameFrame.setMinimumSize(new Dimension(800, 750));
        gameFrame.pack();
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

    public void updateDisplay(String text) {
        int rows = 25;
        int columns = 24;
        int rectSize = 20;
        if (displayPanel.getWidth() > displayPanel.getHeight()) {
            rectSize = displayPanel.getHeight() / 25;
        } else {
            rectSize = displayPanel.getWidth() / 25;
        }

        //displayPanel.setPreferredSize(new Dimension(rows));
        Rectangle half = new Rectangle(0, 0, 50, 50);
        //paint(display.getGraphics());
        paint(displayPanel.getGraphics(), rectSize);
    }

    public void paint(Graphics g, int rectSize) {
        int border = 10;
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
