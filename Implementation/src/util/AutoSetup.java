package util;

import model.Game;
import view.GUI;
import view.PlayerSetupMenu;

import java.util.ArrayList;
import java.util.Collections;

public class AutoSetup {
    /**
     * Automatically performs a player setup of 3 players for testing
     *
     * @author Baxter Kirikiri
     */
    public AutoSetup(Game game){
        game.playerTransition();

        if (!game.getSubState().equals(Game.subStates.PLAYERS)) {
            throw new Error("Expecting PLAYERS Sub State but " + game.getSubState());
        }

        game.userInterface = new GUI(game);
        PlayerSetupMenu p = new PlayerSetupMenu();
        game.numPlayers = 3;
        game.players = new ArrayList<>();
        game.players = p.setPlayers(game.getCharacterNames(), game.numPlayers, game.players, game.getCharacterCardsMap(), true);
        Collections.sort(game.players);
    }
}
