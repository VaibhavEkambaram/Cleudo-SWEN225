package View;

import Model.Player;

import javax.swing.*;

public class SuggestionMenu {
    public void unableToSuggest(Player currentPlayer) {
        JOptionPane.showMessageDialog(null, "You (" + currentPlayer.getPlayerVanityName() + ") are not able to make a suggestion as you are not in a room.", "Suggestion Failed", JOptionPane.ERROR_MESSAGE);
    }
}
