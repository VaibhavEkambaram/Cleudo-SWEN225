package View;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;



public class Table extends Observable {

    private static final Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);

    public Table() {
        JFrame gameFrame = new JFrame("Cluedo");
        gameFrame.setLayout(new BorderLayout());
        gameFrame.setSize(OUTER_FRAME_DIMENSION);

        gameFrame.setVisible(true);

    }
}
