package tests;

import model.Game;
import org.junit.Test;
import view.GUI;

import java.awt.event.KeyEvent;

import static org.junit.Assert.assertEquals;


public class programTests {


    /**
     * Check a game is able to initialise with 6 players and draw a proper board
     *
     * @author Vaibhav Ekambaram
     */
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

        // check overall board
        assertEquals(validBoard, game.getBoard().toString());

        // check players are properly associated with board
        assertEquals("Miss Scarlett", game.getBoard().getPositions()[24][7].getCharacter().getCharacterName());
        assertEquals("Col. Mustard", game.getBoard().getPositions()[17][0].getCharacter().getCharacterName());
        assertEquals("Mrs. White", game.getBoard().getPositions()[0][9].getCharacter().getCharacterName());
        assertEquals("Mr. Green", game.getBoard().getPositions()[0][14].getCharacter().getCharacterName());
        assertEquals("Mrs. Peacock", game.getBoard().getPositions()[6][23].getCharacter().getCharacterName());
        assertEquals("Prof. Plum", game.getBoard().getPositions()[19][23].getCharacter().getCharacterName());
    }


    /**
     * check Dice Throw updates number of moves remaining
     */
    @Test
    public void checkDiceThrowResults() {
        Game game = new Game(false);
        int value = game.rollDice();
        assertEquals(game.getMovesRemaining(), value);
    }


    /**
     * Tests if the GUI is in the correct key state when the 'r' key is pressed for rolling the dice
     *
     * @author Baxter Kirikiri
     */
    @Test
    public void rollState() {
        Game game = new Game(false);
        GUI gui = new GUI(game);

        gui.gameFrame.requestFocusInWindow();
        gui.gameFrame.dispatchEvent(new KeyEvent(gui.gameFrame,
                KeyEvent.KEY_TYPED, System.currentTimeMillis(), 0,
                KeyEvent.VK_UNDEFINED, 'r'));
        game.userInterface.updateDisplay();
        assertEquals(GUI.KeyStates.PRE_ROLL, gui.keyTracker);
    }


}
