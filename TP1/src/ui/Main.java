package ui;

import game.Constants;

import javax.swing.*;
import java.awt.*;

import static game.Constants.BOARD_SIZE;
import static game.Constants.SCALE;

public class Main extends JFrame {

    private static final long serialVersionUID = 1L;

    Main() {
        super("");
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        MainCanvas canvas = new MainCanvas(this);
        add(canvas, BorderLayout.CENTER);

        setBackground(Color.GRAY);
        setSize(Constants.WIDTH * SCALE, (Constants.HEIGHT + 1) * SCALE);
        setVisible(true);
        canvas.createBufferStrategy(2);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}