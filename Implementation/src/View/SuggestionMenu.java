package View;

import Model.Player;
import Model.RoomCard;

import javax.swing.*;
import java.awt.*;

public class SuggestionMenu {

    public String makeSuggestion(String[] characters, String[] weapons, String[] rooms, RoomCard suggestionRoom) {
        JPanel fields = new JPanel(new GridLayout(0, 1));
        JLabel label = new JLabel("Make a suggestion, then click okay when ready");
        fields.add(label);

        JComboBox<String> roomBox = new JComboBox<>(rooms);
        JComboBox<String> charBox = new JComboBox<>(characters);
        JComboBox<String> weaponBox = new JComboBox<>(weapons);

        fields.add(new JLabel("Rooms"));
        fields.add(roomBox);
        fields.add(new JLabel("Characters"));
        fields.add(charBox);
        fields.add(new JLabel("Weapons"));
        fields.add(weaponBox);

        roomBox.setSelectedItem(suggestionRoom.toString());
        roomBox.setEnabled(false);
        JOptionPane.showMessageDialog(null, fields, "Make a Suggestion", JOptionPane.PLAIN_MESSAGE);
        return (charBox.getSelectedItem() + "\t" + weaponBox.getSelectedItem());
    }


    public void unableToSuggest(Player currentPlayer) {
        JOptionPane.showMessageDialog(null, "You (" + currentPlayer.getPlayerVanityName() + ") are not able to make a suggestion as you are not in a room.", "Suggestion Failed", JOptionPane.ERROR_MESSAGE);
    }

    public void teleportFailed() {
        JOptionPane.showMessageDialog(null, "An error occured while attempting to teleport the suggested player", "Suggestion Failed", JOptionPane.ERROR_MESSAGE);
    }

    public int nobodyCouldRefute(){
        Object[] options = {"Make Accusation", "Pass"};
        int n = JOptionPane.showOptionDialog(null, "No one could refute your suggestion. What would you like to do?", "Suggestion not Refuted",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,//do not use a custom Icon
                options,//the titles of buttons
                options[1]);//default button title
        return n;
    }


}


