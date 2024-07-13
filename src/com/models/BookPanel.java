package com.models;

import javax.swing.*;
import java.awt.*;

public class BookPanel extends JPanel {

    private Book book;

    public BookPanel(Book book) {
        this.book = book;

        setLayout(new BorderLayout());

        // Create JLabels, JTextAreas, etc., to display book information
        JLabel titleLabel = new JLabel(book.getTitle());
        JTextArea descriptionArea = new JTextArea(book.getDescription());

        // Add components to the panel
        add(titleLabel, BorderLayout.NORTH);
        add(descriptionArea, BorderLayout.SOUTH);

        // You can add more components based on your Book class properties
    }
}
