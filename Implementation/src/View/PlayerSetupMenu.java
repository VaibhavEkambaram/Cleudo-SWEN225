package View;

import Model.CharacterCard;
import Model.Player;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;


/**
 * User Interface for Player Setup
 * Allows game players to select the number of players, then select their desired tokens
 *
 * @author Vaibhav Ekambaram
 */
public class PlayerSetupMenu {


    /**
     * Set player vanity name and tokens
     * @param characterNames array of character name strings
     * @param numPlayers number of players
     * @param players list of players to write to
     * @param characterCardsMap map of characters
     * @return list of players
     */
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
}
