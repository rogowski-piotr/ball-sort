import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ball Sort Game");
        Dimension dimension = new Dimension(550, 700);
        Board b = new Board();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(dimension);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.add(b);
    }

}
