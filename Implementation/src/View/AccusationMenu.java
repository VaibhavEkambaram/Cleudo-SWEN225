package View;

import javax.swing.*;
import java.awt.*;

public class AccusationMenu {
    public String makeAccusation(String[] characters,String[] weapons, String[] rooms){
        JPanel fields = new JPanel(new GridLayout(0, 1));
        JLabel label = new JLabel("Pick out an accusation scenario, then click okay when ready ");
        fields.add(label);

        JComboBox<String> charBox = new JComboBox<>(characters);
        JComboBox<String> weaponBox = new JComboBox<>(weapons);
        JComboBox<String> roomBox = new JComboBox<>(rooms);
        fields.add(new JLabel("Characters"));
        fields.add(charBox);
        fields.add(new JLabel("Weapons"));
        fields.add(weaponBox);
        fields.add(new JLabel("Rooms"));
        fields.add(roomBox);

        JOptionPane.showMessageDialog(null, fields, "Make an Accusation", JOptionPane.PLAIN_MESSAGE);
        return (charBox.getSelectedItem() + "\t" + weaponBox.getSelectedItem() + "\t" + roomBox.getSelectedItem());
    }

}
