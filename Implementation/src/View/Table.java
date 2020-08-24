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

    public Table() {
        gameFrame = new JFrame("Cluedo");
        gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();
        gameFrame.setJMenuBar(tableMenuBar);
        gameFrame.setSize(OUTER_FRAME_DIMENSION);
        gameFrame.setVisible(true);


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
        setupGameMenuItem.addActionListener(e -> setPlayerCount());


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
