package view;

import model.Player;
import model.Scenario;

import javax.swing.*;
import java.awt.*;

public class AccusationMenu {
    public String makeAccusation(String[] characters, String[] weapons, String[] rooms) {
        JPanel fields = new JPanel(new GridLayout(0, 1));
        fields.add(new JLabel("Pick out an accusation scenario, then click okay when ready "));

        JComboBox<String> charBox = new JComboBox<>(characters);
        JComboBox<String> weaponBox = new JComboBox<>(weapons);
        JComboBox<String> roomBox = new JComboBox<>(rooms);

        fields.add(new JLabel("Rooms"));
        fields.add(roomBox);
        fields.add(new JLabel("Characters"));
        fields.add(charBox);
        fields.add(new JLabel("Weapons"));
        fields.add(weaponBox);


        JOptionPane.showMessageDialog(null, fields, "Make an Accusation", JOptionPane.PLAIN_MESSAGE);
        return (charBox.getSelectedItem() + "\t" + weaponBox.getSelectedItem() + "\t" + roomBox.getSelectedItem());
    }

    public void unableToAccuse(Player currentPlayer) {
        JOptionPane.showMessageDialog(null, "You (" + currentPlayer.getPlayerVanityName() + ") have already made a failed accusation!\nTherefore, you can no longer make accusations during this game.", "Accusation Failed", JOptionPane.ERROR_MESSAGE);
    }

    public void incorrectAccusation(Player currentPlayer) {
        JOptionPane.showMessageDialog(null, "You (" + currentPlayer.getPlayerVanityName() + ") were incorrect in your accusation.\nYou may remain playing the game and offering suggestions, but are no longer able to make further accusations.", "Incorrect Accusation", JOptionPane.ERROR_MESSAGE);
        JOptionPane.showMessageDialog(null, "Moving to next player...", "Information", JOptionPane.PLAIN_MESSAGE);
    }

    public void successfulAccusation(Player currentPlayer, Scenario murderScenario) {
        JOptionPane.showMessageDialog(null, currentPlayer.getPlayerVanityName() + " was successful in their accusation. They have won the game!!!\nThe murder scenario was: " + murderScenario.toString() + "\nThank you for playing Cluedo!", "Successful Accusation - Game Over!", JOptionPane.PLAIN_MESSAGE);
    }
}
