
package View;


import javax.swing.*;
import java.awt.*;


public class RollDiceMenu {
    private ImageIcon image1;
    private JLabel label1;
    private ImageIcon image2;
    private JLabel label2;

    public RollDiceMenu(int firstNumber, int secondNumber) {
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());

        image1 = new ImageIcon(getClass().getResource("/resources/dice_"+firstNumber+".png"));
        label1 = new JLabel(image1);
        frame.add(label1);

        image2 = new ImageIcon(getClass().getResource("/resources/dice_"+secondNumber+".png"));
        label2 = new JLabel(image2);
        frame.add(label2);

        frame.setVisible(true);
        frame.pack();
        frame.setTitle("Dice Result");
    }
}