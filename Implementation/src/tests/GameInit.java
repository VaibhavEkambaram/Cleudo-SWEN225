package tests;

import model.Game;
import org.junit.*;

public class GameInit{
    /**
     * Tests if the game starts without issue
     *
     * @author Baxter Kirikiri
     */
    @Test
    public void gameInits(){
        new Game(false);
    }
}
