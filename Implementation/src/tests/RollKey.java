package tests;

import model.Game;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import view.GUI;

import java.awt.event.KeyEvent;

public class RollKey {
    GUI gui;
    @Before
    public void setup(){
        gui = new GUI(new Game(false));
    }

    @Test
    public void postRoll(){
        gui.gameFrame.requestFocusInWindow();
        gui.gameFrame.dispatchEvent(new KeyEvent(gui.gameFrame,
                KeyEvent.KEY_TYPED, System.currentTimeMillis(), 0,
                KeyEvent.VK_UNDEFINED, 'r'));
        Assert.assertEquals(GUI.KeyStates.PRE_ROLL, gui.keyTracker);
    }

}
