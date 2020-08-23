package View;

import Model.CharacterCard;
import Model.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class SetupPlayers {
    public List<Player> setPlayers(String[] characterNames, int numPlayers, List<Player> players, Map<String, CharacterCard> characterCardsMap) {
        ArrayList<String> used = new ArrayList<>();

        ArrayList<String> charNames = new ArrayList<>(Arrays.asList(characterNames));

        for (int i = 0; i < numPlayers; i++) {
            JPanel fields = new JPanel(new GridLayout(5, 2));
            JLabel label = new JLabel("Enter your name then select your player token ");
            JTextField nameField = new JTextField();
            nameField.setText("Player " + (i + 1));
            ButtonGroup b = new ButtonGroup();
            fields.add(label);
            fields.add(nameField);
            charNames.forEach(character -> {
                JRadioButton radButton = new JRadioButton();
                if (used.contains(character)) {
                    radButton.setEnabled(false);
                }
                radButton.setText(character);
                radButton.setActionCommand(character);
                b.add(radButton);
                fields.add(radButton);
            });
            JOptionPane.showMessageDialog(null, fields, "Set Player Preferences", JOptionPane.OK_OPTION);
            if (b.getSelection() != null || nameField.getText().length()==0) {
                used.add(b.getSelection().getActionCommand());
                players.add(new Player(characterCardsMap.get(b.getSelection().getActionCommand()), nameField.getText()));
            } else {
                i--;
            }
        }
        return players;
    }
}
