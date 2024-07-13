package com.models;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FavoriteFrame extends JFrame {

    private void populateFavoritePanel(List<Book> favoriteBooks) {
        JPanel favoritePanel = new JPanel(new GridLayout(2, favoriteBooks.size() / 2, 10, 10));

        for (Book book : favoriteBooks) {
            // Create a new BookPanel for each favorite book
            BookPanel bookPanel = new BookPanel(book); // Replace with your actual BookPanel constructor
            favoritePanel.add(bookPanel);
        }

        // Set up the scroll pane for the favorite books
        JScrollPane scrollPane = new JScrollPane(favoritePanel);
        add(scrollPane, BorderLayout.CENTER);
    }

    public FavoriteFrame(String username, List<Book> favoriteBooks) {
        setTitle("Favorite Books");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Favorite Books of " + username);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Use the populateFavoritePanel method to add books to the frame
        populateFavoritePanel(favoriteBooks);

        setVisible(true);
    }
}
