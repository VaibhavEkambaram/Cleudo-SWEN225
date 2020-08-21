package View;

import javax.swing.*;
import java.awt.*;

public class SetupPlayers {
    public void setPlayers(String[] characterNames){
        for(int i=0; i < 6; i++) {
            JPanel fields = new JPanel(new GridLayout(3, 1));
            JLabel label = new JLabel("Enter your name then select your player token");
            JTextField nameField = new JTextField();

            ButtonGroup b = new ButtonGroup();
            JRadioButton jRadioButton1 = new JRadioButton();
            b.add(jRadioButton1);

            JRadioButton jRadioButton2 = new JRadioButton();
            b.add(jRadioButton2);
            fields.add(jRadioButton1);
            fields.add(jRadioButton2);

            fields.add(label);
            fields.add(nameField);
            JOptionPane.showMessageDialog(null, fields, "Set Player Preferences", JOptionPane.DEFAULT_OPTION);
        }
    }
}
