package com.ui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AboutFrame1 extends JFrame {
    private static final int width = 1960;
    private static final int height = 1080;
    String loggedInUsername;
    AboutFrame1(String username) {
        this.loggedInUsername = username;
        setTitle("ROBLIB");
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        ImageIcon icon = new ImageIcon(getClass().getResource("/com/Resources/Icon_Logo.png"));
        setIconImage(icon.getImage());

        //BACK TO MAIN FRAME
        ImageIcon backButtonIcon = new ImageIcon(getClass().getResource("/com/Resources/back_button.png")); // Replace with your back button image
        JLabel backButton = new JLabel(backButtonIcon);
        backButton.setBounds(30, 30, backButtonIcon.getIconWidth(), backButtonIcon.getIconHeight());
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new MainFrame(loggedInUsername).setVisible(true);
                dispose();

            }
        });
        add(backButton);

        //VERSION
        JLabel versionLabel = new JLabel("v 1.0");
        versionLabel.setForeground(Color.GRAY);
        Font versionFont = new Font("Arial", Font.PLAIN, 16);
        versionLabel.setFont(versionFont);
        int versionWidth = 100;
        int versionHeight = 30;
        versionLabel.setBounds(100, height - versionHeight - 100, versionWidth, versionHeight);
        add(versionLabel);

        // Create a JLabel for your text
        Image img = new ImageIcon(getClass().getResource("/com/Resources/about_v1.png")).getImage();
        img.getScaledInstance(10,10, Image.SCALE_DEFAULT);
        JLabel label = new JLabel("ABOUT");
        label.setIcon(new ImageIcon(img));
        label.setVerticalAlignment(JLabel.TOP);

        // Set the bounds to center the label
        int labelWidth = 1000; // Adjust the width as needed
        int labelHeight = 343; // Adjust the height as needed
        int x = (width - labelWidth) / 2;
        int y = (height - labelHeight) / 2;
        label.setBounds(x, y-70, labelWidth, labelHeight);

        // Add the label to the frame
        add(label);

        setVisible(true);
    }
}
