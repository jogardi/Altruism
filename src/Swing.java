import oracle.jrockit.jfr.JFR;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class Swing extends JFrame {
    JLabel label = new JLabel("");
    public Swing() {
        setSize(300, 200);
        setTitle("Altruism");
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        add(label);

    }

    public void nextGen(String event) {

        label.setText(event);

        label.setText("");

    }

}
