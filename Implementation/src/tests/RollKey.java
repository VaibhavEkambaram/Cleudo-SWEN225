package tests;

import model.Game;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import view.GUI;

import java.awt.event.KeyEvent;

public class RollKey {
    Game game;
    GUI gui;

    /**
     * Sets up game and gui fields for the test
     *
     * @author Baxter Kirikiri
     */
    @Before
    public void setup(){
        game = new Game(false);
        gui = new GUI(game);
    }

    /**
     * Tests if the GUI is in the correct key state when the 'r' key is pressed for rolling the dice
     *
     * @author Baxter Kirikiri
     */
    @Test
    public void rollState(){
        gui.gameFrame.requestFocusInWindow();
        gui.gameFrame.dispatchEvent(new KeyEvent(gui.gameFrame,
                KeyEvent.KEY_TYPED, System.currentTimeMillis(), 0,
                KeyEvent.VK_UNDEFINED, 'r'));
        game.userInterface.updateDisplay();
        Assert.assertEquals(GUI.KeyStates.PRE_ROLL, gui.keyTracker);
    }

}
