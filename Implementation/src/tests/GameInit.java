package tests;

import model.Game;
import org.junit.*;

public class GameInit{
    Game game;

    @Test
    public void gameInits(){
        game = new Game(false);
    }
}
