package view;

import model.Card;
import model.Player;
import model.RoomCard;
import model.Scenario;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

/**
 * Class to process GUI events related to Suggestions and Refutations
 *
 * @author Vaibhav Ekambaram
 */
public class SuggestionMenu {

    /**
     * Let the current player make a suggestion
     *
     * @param characters     characters string array
     * @param weapons        weapons string array
     * @param rooms          rooms string array
     * @param suggestionRoom room the player is located in (player must be in room to make a suggestion)
     * @return selection
     */
    public String makeSuggestion(String[] characters, String[] weapons, String[] rooms, RoomCard suggestionRoom) {
        JPanel fields = new JPanel(new GridLayout(0, 1));
        fields.add(new JLabel("Make a suggestion, then click okay when ready"));

        JComboBox<String> roomBox = new JComboBox<>(rooms);
        JComboBox<String> charBox = new JComboBox<>(characters);
        JComboBox<String> weaponBox = new JComboBox<>(weapons);

        fields.add(new JLabel("Rooms"));
        fields.add(roomBox);
        fields.add(new JLabel("Characters"));
        fields.add(charBox);
        fields.add(new JLabel("Weapons"));
        fields.add(weaponBox);

        // lock room selection to room the player is currently located in
        roomBox.setSelectedItem(suggestionRoom.toString());
        roomBox.setEnabled(false);

        JOptionPane.showMessageDialog(null, fields, "Make a Suggestion", JOptionPane.PLAIN_MESSAGE);
        return (charBox.getSelectedItem() + "\t" + weaponBox.getSelectedItem());
    }

    /**
     * Let another player try to refute the suggestion made by the current player
     *
     * @param refutePlayer  player making refutation
     * @param currentPlayer current player
     * @param suggestion    suggestion scenario
     * @return -1 if passed or the refutation string if pressing okay
     */
    public String makeRefutation(Player refutePlayer, Player currentPlayer, Scenario suggestion) {
        JPanel fields = new JPanel(new GridLayout(0, 1));
        fields.add(new JLabel("Refute " + currentPlayer.getPlayerVanityName() + "'s Suggestion by making a refutation using a card from your hand."));
        fields.add(new JLabel("Suggestion:  " + suggestion.toString()));
        fields.add(new JLabel("Your Hand"));
        List<Card> refuteCards = refutePlayer.getHand();
        String[] refuteCardsStrings = refuteCards.stream().map(Card::toString).toArray(String[]::new);

        JComboBox<String> hand = new JComboBox<>(refuteCardsStrings);
        fields.add(hand);


        Object[] options = {"OK", "Pass"};
        int n = JOptionPane.showOptionDialog(null, fields, refutePlayer.getPlayerVanityName() + " - Make a Refutation", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);

        return n == 0 ? Objects.requireNonNull(hand.getSelectedItem()).toString() : "-1";
    }


    /**
     * Player is unable to make a suggestion as they are not currently located in a room
     *
     * @param currentPlayer current player
     */
    public void unableToSuggest(Player currentPlayer) {
        JOptionPane.showMessageDialog(null, "You (" + currentPlayer.getPlayerVanityName() + ") are not able to make a suggestion as you are not in a room.", "Suggestion Failed", JOptionPane.ERROR_MESSAGE);
    }


    /**
     * Show options for current player if nobody could refute
     *
     * @return integer option
     */
    public int nobodyCouldRefute() {
        Object[] options = {"Make Accusation", "Pass"};
        return JOptionPane.showOptionDialog(null, "No one could refute your suggestion. What would you like to do?", "Suggestion not Refuted",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,//do not use a custom Icon
                options,//the titles of buttons
                options[1]);
    }

    /**
     * Other player has successfully refuted the current players suggestion
     *
     * @param characterName other player
     */
    public void successfulRefutation(String characterName) {
        JOptionPane.showMessageDialog(null, characterName + "'s suggestion was refuted!", "Suggestion Refuted", JOptionPane.PLAIN_MESSAGE);
        JOptionPane.showMessageDialog(null, "Moving to next player...", "Information", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Failed refutation
     *
     * @param characterName failed to make a refutation, ie. selecting a card that is not valid or passed
     */
    public void failedRefutation(String characterName) {
        JOptionPane.showMessageDialog(null, characterName + "'s refutation failed!", "Failed Refutation", JOptionPane.ERROR_MESSAGE);
    }
}