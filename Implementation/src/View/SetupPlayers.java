package View;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class SetupPlayers {
    public void setPlayers(String[] characterNames) {
        ArrayList<String> charNames = new ArrayList<>();
        ArrayList<String> used = new ArrayList<>();

        charNames.addAll(Arrays.asList(characterNames));

        for (int i = 0; i < 6; i++) {
            JPanel fields = new JPanel(new GridLayout(5, 2));
            JLabel label = new JLabel("Enter your name then select your player token ");
            JTextField nameField = new JTextField();
            nameField.setText("Player " + (i + 1));

            ButtonGroup b = new ButtonGroup();

            fields.add(label);
            fields.add(nameField);

            for (String character : charNames) {
                JRadioButton jRadioButton1 = new JRadioButton();
                if (used.contains(character)) {
                    jRadioButton1.setEnabled(false);
                }

                jRadioButton1.setText(character);
                jRadioButton1.setActionCommand(character);
                b.add(jRadioButton1);
                fields.add(jRadioButton1);
            }

            JOptionPane.showMessageDialog(null, fields, "Set Player Preferences", JOptionPane.OK_OPTION);
            System.out.println(b.getSelection().getActionCommand());
            used.add(b.getSelection().getActionCommand());
        }
    }
}
