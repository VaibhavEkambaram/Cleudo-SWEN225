package tests;

import model.Game;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class Tests {

    @Test
    public void checkGeneratedBoard() {
        Game game = new Game(false);

        String validBoard =
                "|x|x|x|x|x|x|x|x|x|3|x|x|x|x|4|x|x|x|x|x|x|x|x|x|\n" +
                "|k|k|k|k|k|k|x|_|_|_|b|b|b|b|_|_|_|x|c|c|c|c|c|c|\n" +
                "|k|_|_|_|_|k|_|_|b|b|b|_|_|b|b|b|_|_|c|_|_|_|_|c|\n" +
                "|k|_|_|_|_|k|_|_|b|_|_|_|_|_|_|b|_|_|c|_|_|_|_|c|\n" +
                "|k|_|_|_|_|k|_|_|b|_|_|_|_|_|_|b|_|_|v|c|_|_|c|c|\n" +
                "|k|k|_|_|_|k|_|_|<|_|_|_|_|_|_|>|_|_|_|c|c|c|c|x|\n" +
                "|x|k|k|k|v|k|_|_|b|_|_|_|_|_|_|b|_|_|_|_|_|_|_|5|\n" +
                "|_|_|_|_|_|_|_|_|b|v|b|b|b|b|v|b|_|_|_|_|_|_|_|x|\n" +
                "|x|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|i|i|i|i|i|i|\n" +
                "|d|d|d|d|d|_|_|_|_|_|_|_|_|_|_|_|_|_|<|_|_|_|_|i|\n" +
                "|d|_|_|_|d|d|d|d|_|_|x|x|x|x|x|_|_|_|i|_|_|_|_|i|\n" +
                "|d|_|_|_|_|_|_|d|_|_|x|x|x|x|x|_|_|_|i|_|_|_|_|i|\n" +
                "|d|_|_|_|_|_|_|>|_|_|x|x|x|x|x|_|_|_|i|i|i|i|v|i|\n" +
                "|d|_|_|_|_|_|_|d|_|_|x|x|x|x|x|_|_|_|_|_|_|_|_|x|\n" +
                "|d|_|_|_|_|_|_|d|_|_|x|x|x|x|x|_|_|_|l|l|^|l|l|x|\n" +
                "|d|d|d|d|d|d|v|d|_|_|x|x|x|x|x|_|_|l|l|_|_|_|l|l|\n" +
                "|x|_|_|_|_|_|_|_|_|_|x|x|x|x|x|_|_|<|_|_|_|_|_|l|\n" +
                "|2|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|_|l|l|_|_|_|l|l|\n" +
                "|x|_|_|_|_|_|_|_|_|h|h|^|^|h|h|_|_|_|l|l|l|l|l|x|\n" +
                "|o|o|o|o|o|o|^|_|_|h|_|_|_|_|h|_|_|_|_|_|_|_|_|6|\n" +
                "|o|_|_|_|_|_|o|_|_|h|_|_|_|_|>|_|_|_|_|_|_|_|_|x|\n" +
                "|o|_|_|_|_|_|o|_|_|h|_|_|_|_|h|_|_|^|y|y|y|y|y|y|\n" +
                "|o|_|_|_|_|_|o|_|_|h|_|_|_|_|h|_|_|y|_|_|_|_|_|y|\n" +
                "|o|_|_|_|_|o|o|_|_|h|_|_|_|_|h|_|_|y|y|_|_|_|_|y|\n" +
                "|o|o|o|o|o|o|x|1|x|h|h|h|h|h|h|x|_|x|y|y|y|y|y|y|\n";

        assertEquals(validBoard, game.getBoard().toString());
    }


    @Test
    public void checkDiceThrowResults(){
        Game game = new Game(false);
    }


}
