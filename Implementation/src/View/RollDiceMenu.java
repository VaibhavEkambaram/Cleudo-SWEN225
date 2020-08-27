
package View;


import javax.swing.*;
import java.awt.*;


public class RollDiceMenu extends JFrame {
    private ImageIcon image1;
    private JLabel label1;
    private ImageIcon image2;
    private JLabel label2;

    public RollDiceMenu() {
        setLayout(new FlowLayout());

        image1 = new ImageIcon(getClass().getResource("/resources/dice_1.png"));
        label1 = new JLabel(image1);
        add(label1);

        image2 = new ImageIcon(getClass().getResource("/resources/dice_2.png"));
        label2 = new JLabel(image2);
        add(label2);

    }

    public static void main(String args[]) {
        RollDiceMenu gui = new RollDiceMenu();
        gui.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gui.setVisible(true);
        gui.pack();
        gui.setTitle("Image Program");
    }


}