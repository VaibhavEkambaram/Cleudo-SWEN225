package view;

import model.CharacterCard;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class PlayerSetupMenu {
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
        JOptionPane.showMessageDialog(null, fields, "Game Startup Parameters", JOptionPane.PLAIN_MESSAGE);
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
    public java.util.List<Player> setPlayers(String[] characterNames, int numPlayers, List<Player> players, Map<String, CharacterCard> characterCardsMap, boolean show) {
        ArrayList<String> charNames = new ArrayList<>(Arrays.asList(characterNames));
        ArrayList<String> used = new ArrayList<>();
        if(!show){
            players.add(new Player(characterCardsMap.get("Miss Scarlett"), "Player 1"));
            players.add(new Player(characterCardsMap.get("Col. Mustard"), "Player 2"));
            players.add(new Player(characterCardsMap.get("Mrs. White"), "Player 3"));
            return players;
        }
        // menu for each player to select options
        for (int i = 0; i < numPlayers; i++) {
            JPanel fields = new JPanel(new GridLayout(0,1));
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
}
