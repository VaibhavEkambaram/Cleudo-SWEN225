package tests;

import model.Game;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import view.GUI;

import java.awt.event.KeyEvent;

public class MoveKey {
    GUI gui;
    Game game;
    @Before
    public void setup(){
        game = new Game(false);
        gui = new GUI(game);

        gui.gameFrame.dispatchEvent(new KeyEvent(gui.gameFrame,
                KeyEvent.KEY_TYPED, System.currentTimeMillis(), 0,
                KeyEvent.VK_UNDEFINED, 'r'));

    }

    @Test
    public void moveState(){
        gui.gameFrame.requestFocusInWindow();
        gui.gameFrame.dispatchEvent(new KeyEvent(gui.gameFrame,
                KeyEvent.KEY_TYPED, System.currentTimeMillis(), 0,
                KeyEvent.VK_UP));
        Assert.assertEquals(GUI.KeyStates.MOVEMENT, gui.keyTracker);
    }

}
