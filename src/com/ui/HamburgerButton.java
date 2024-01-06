package com.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HamburgerButton extends JButton {
    private boolean isClicked = false;

    public HamburgerButton() {
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isClicked = !isClicked;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();

        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(5)); // Set the thickness of the bars
        g2d.setColor(Color.GRAY); // Set the color of the bars

        // Draw 3 bars
        g2d.drawLine(5, 10, width - 5, 10);
        g2d.drawLine(5, height / 2, width - 5, height / 2);
        g2d.drawLine(5, height - 10, width - 5, height - 10);

    }
}