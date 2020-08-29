package View;

import Model.Card;
import Model.Player;
import Model.RoomCard;
import Model.Scenario;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

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

    public String makeRefutation(Player refutePlayer, Player currentPlayer, Scenario suggestion) {
        JPanel fields = new JPanel(new GridLayout(0, 1));
        fields.add(new JLabel("Refute " + currentPlayer.getPlayerVanityName() + "'s Suggestion by making a refutation using a card from your hand."));

        fields.add(new JLabel("Suggestion:  " + suggestion.toString()));

        fields.add(new JLabel("Your Hand"));
        List<Card> refuteCards = refutePlayer.getHand();
        String[] refuteCardsStrings = new String[refuteCards.size()];
        for (int i = 0; i < refuteCards.size(); i++) {
            refuteCardsStrings[i] = refuteCards.get(i).toString();
        }

        JComboBox<String> hand = new JComboBox<>(refuteCardsStrings);
        fields.add(hand);


        Object[] options = {"OK", "Pass"};
        int n = JOptionPane.showOptionDialog(null, fields, refutePlayer.getPlayerVanityName() + " - Make a Refutation", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);

        if (n == 0) return Objects.requireNonNull(hand.getSelectedItem()).toString();
        return "-1";
    }


    public void unableToSuggest(Player currentPlayer) {
        JOptionPane.showMessageDialog(null, "You (" + currentPlayer.getPlayerVanityName() + ") are not able to make a suggestion as you are not in a room.", "Suggestion Failed", JOptionPane.ERROR_MESSAGE);
    }

    public void teleportFailed() {
        JOptionPane.showMessageDialog(null, "An error occurred while attempting to teleport the suggested player and weapon", "Suggestion Failed", JOptionPane.ERROR_MESSAGE);
    }

    public int nobodyCouldRefute() {
        Object[] options = {"Make Accusation", "Pass"};
        int n = JOptionPane.showOptionDialog(null, "No one could refute your suggestion. What would you like to do?", "Suggestion not Refuted",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,//do not use a custom Icon
                options,//the titles of buttons
                options[1]);//default button title
        return n;
    }

    public void refuted(String characterName) {
        JOptionPane.showMessageDialog(null, characterName + "'s suggestion was refuted!", "Suggestion Refuted", JOptionPane.PLAIN_MESSAGE);
    }

    public void refutationFailed(String characterName) {
        JOptionPane.showMessageDialog(null, characterName + "'s refutation failed!", "Failed Refutation", JOptionPane.ERROR_MESSAGE);
    }
}