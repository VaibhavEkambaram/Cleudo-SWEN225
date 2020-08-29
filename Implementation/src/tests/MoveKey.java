package tests;

import model.Game;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import view.GUI;

import java.awt.event.KeyEvent;

public class MoveKey {
    GUI gui;
    @Before
    public void setup(){
        gui = new GUI(new Game(false));
    }

    @Test
    public void postMove(){
        gui.gameFrame.requestFocusInWindow();
        gui.gameFrame.dispatchEvent(new KeyEvent(gui.gameFrame,
                KeyEvent.KEY_TYPED, System.currentTimeMillis(), 0,
                KeyEvent.VK_UP));
        Assert.assertEquals(GUI.KeyStates.MOVEMENT, gui.keyTracker);
    }

}
