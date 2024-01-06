import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TEST {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Animation Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel();
        panel.setBackground(Color.GREEN);

        ActionListener listener = new ActionListener() {
            int x = 0;
            public void actionPerformed(ActionEvent e) {
                x += 5;
                if (x > 400) {
                    x = 0;
                }
                panel.setLocation(x, 0);
            }};

        Timer timer = new Timer(50, listener);//Timer(int delay , Evènement)delay en millisecondes
        timer.start();
        frame.add(panel);
        frame.setVisible(true);
    }}


