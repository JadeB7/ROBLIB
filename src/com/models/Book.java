package com.models;

import com.roblib.Database;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;

public class Book extends JPanel {
    private String dbUrl = "jdbc:mysql://sql5.freesqldatabase.com:3306/sql5670955";
    private String dbUser = "sql5670955";
    private String dbPassword = "IWVruy5Li1";
    private String id;
    private String name;
    private String discription;
    private ImageIcon bookImage;
    private JFrame parent;
    private JToggleButton favoriteButton;
    private boolean isBookAvailable;
    public boolean isFavorite = false;

    public Book(String id,JFrame parent) {
        this.parent= parent;
        String path = null;
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String sql = "SELECT * FROM BOOKS WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);

            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                path = resultSet.getString("image");
                name = resultSet.getString("name");
                discription = resultSet.getString("disc");
                if(resultSet.getString("avai").equals("1")){
                    isBookAvailable = true;
                }else{
                    isBookAvailable = false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        URL url = null;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        BufferedImage image = null;
        try {
            image = ImageIO.read(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        bookImage = new ImageIcon(image.getScaledInstance(100, 100, Image.SCALE_SMOOTH));


        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(200, 300));

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Title: " + name);
        JLabel descriptionLabel = new JLabel("<html>Description: " + discription + "</html>");
        JLabel imageLabel = new JLabel(bookImage);

        JButton reserveButton = new JButton("Reserve");
        reserveButton.setBackground(new Color(66, 183, 42)); // Set the background color to blue
        reserveButton.setForeground(Color.WHITE); // Set the text color to white
        Font reserveButtonFont = new Font("Arial", Font.BOLD, 15); // Adjust font properties as needed
        reserveButton.setFont(reserveButtonFont);
        reserveButton.setSize(50,30);

        JButton addToFavoritesButton = new JButton("Favorites");
        addToFavoritesButton.setBackground(new Color(66, 183, 42)); // Set the background color to blue
        addToFavoritesButton.setForeground(Color.WHITE); // Set the text color to white
        Font addToFavoritesButtonFont = new Font("Arial", Font.BOLD, 15); // Adjust font properties as needed
        addToFavoritesButton.setFont(addToFavoritesButtonFont);
        addToFavoritesButton.setSize(50,30);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 1, 1));
        buttonPanel.add(addToFavoritesButton);
        buttonPanel.add(reserveButton);

        add(titleLabel, BorderLayout.NORTH);
        add(descriptionLabel, BorderLayout.CENTER);
        add(imageLabel, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.SOUTH);

        reserveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(parent, "Book reserved: " + name);
            }
        });

        addToFavoritesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setFavorite(!isFavorite);

                // Show message based on the current favorite status
                String message = isFavorite ? "Book added to favorites: " : "Book removed from favorites: ";
                JOptionPane.showMessageDialog(parent, message + name);
            }
        });
    }
    public void setFavorite(boolean favorite) {
        isFavorite = favorite;

        // Update the UI based on the favorite status
        setBackground(isFavorite ? new Color(189, 237, 255) : Color.WHITE);

        // Update the database with the favorite status
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String sql = "UPDATE BOOKS SET favorite = ? WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, isFavorite ? 1 : 0);
                pstmt.setString(2, id);
                pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void setBookAvailable(boolean bookAvailable) {
        isBookAvailable = bookAvailable;
        setBackground(isBookAvailable ? new Color(189, 237, 255) : Color.GRAY);
    }


    public String getTitle() {
        return name;
    }

    public String getDescription() {
        return discription;
    }

    public boolean isFavorite(String username) {
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String sql = "SELECT id FROM BOOKS WHERE username = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int userId = rs.getInt("id");
                        return userId > 0; // If user_id > 0, the book is a favorite for the user
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
        return false;
    }

    public void setFavorite(String username, boolean favorite) {
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String sql = "UPDATE BOOKS SET user_id = ? WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                Database database = new Database(); // Create an instance of the Database class
                pstmt.setInt(1, favorite ? database.getUserIdByUsername(username) : 0);
                pstmt.setString(2, id);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception as needed
        }
    }
    // You can add more methods to update the book information, image, etc.
}