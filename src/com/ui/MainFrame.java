package com.ui;

import com.models.Book;
import com.models.Library;
import com.roblib.Database;
import com.models.FavoriteFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;


public class MainFrame extends JFrame {
    private boolean sideSectionVisible = false;
    Database database = new Database();
    String loggedInUsername;
    String loggedInEmail;
    static JLabel userImageLabel;
    public MainFrame(String username) {
        this.loggedInUsername = username;
        this.loggedInEmail = database.getEmailByUsername(this.loggedInUsername);
        int width = 1960;
        int height = 1080;
        setTitle("ROBLIB");
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        Library lib = new Library(this);
        lib.setBounds(50, 400, width - 100, height / 2);
        add(lib);

        ImageIcon icon = new ImageIcon(getClass().getResource("/com/Resources/Icon_Logo.png"));
        setIconImage(icon.getImage());

        //BACK TO LOGIN
        ImageIcon backButtonIcon = new ImageIcon(getClass().getResource("/com/Resources/back_button.png")); // Replace with your back button image
        JLabel backButton = new JLabel(backButtonIcon);
        backButton.setBounds(30, 30, backButtonIcon.getIconWidth(), backButtonIcon.getIconHeight());
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new LoginFrame().setVisible(true);
            }
        });
        add(backButton);

        //THE LOGO OF THE NEW ACCOUNT PAGE
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/com/Resources/LOGO2.png"));
        JLabel logoLabel = new JLabel(logoIcon);

        int logoWidth = 300;
        int logoHeight = 300;
        logoLabel.setBounds((width - logoWidth) / 2, 30, logoWidth, logoHeight);
        add(logoLabel);

        //SEARCH BAR
        RoundedTextField searchField = new RoundedTextField(20, "Search for books");
        searchField.setForeground(Color.BLACK);
        int searchBarWidth = 1000;
        int searchBarHeight = 40;
        searchField.setBounds((width - searchBarWidth) / 2, logoHeight + 20, searchBarWidth, searchBarHeight);
        Font searchFont = new Font("Arial", Font.PLAIN, 20);
        searchField.setFont(searchFont);
        searchField.setBackground(new Color(189, 237, 255));
        add(searchField);
        //SEARCH ICON
        ImageIcon searchIcon = new ImageIcon(getClass().getResource("/com/Resources/search_icon.png"));
        JLabel searchIconLabel = new JLabel(searchIcon);
        searchIconLabel.setBounds((width + 900) / 2 , logoHeight + 20, 40, 40);
        add(searchIconLabel);

        //VERSION
        JLabel versionLabel = new JLabel("v 1.0");
        versionLabel.setForeground(Color.GRAY);
        Font versionFont = new Font("Arial", Font.PLAIN, 16);
        versionLabel.setFont(versionFont);
        int versionWidth = 100;
        int versionHeight = 30;
        versionLabel.setBounds(100, height - versionHeight - 100, versionWidth, versionHeight);
        add(versionLabel);

        // Create a panel for the side section (initially hidden)
        JPanel sideSection = new JPanel();
        sideSection.setLayout(null);
        sideSection.setBackground(new Color(189, 237, 255)); // Set the background color as needed
        sideSection.setBounds(width, 0, 400, height);
        add(sideSection);

        JPanel borders = new JPanel();
        borders.setLayout(null);
        borders.setBackground(Color.GRAY);
        borders.setBounds(0, 0, 10, height);
        sideSection.add(borders);

        JPanel borders1 = new JPanel();
        borders1.setLayout(null);
        borders1.setBackground(Color.GRAY);
        borders1.setBounds(0, 300, 400, 10);
        sideSection.add(borders1);

        JPanel borders2 = new JPanel();
        borders2.setLayout(null);
        borders2.setBackground(Color.GRAY);
        borders2.setBounds(0, 800, 400, 10);
        sideSection.add(borders2);

        JLabel usernameLabel = new JLabel(loggedInUsername);
        usernameLabel.setForeground(Color.BLACK);
        usernameLabel.setBounds(130, 150, 200, 60);
        Font usernamefont = new Font("Arial", Font.PLAIN, 50);
        usernameLabel.setFont(usernamefont);
        sideSection.add(usernameLabel);

        JLabel emailLabel = new JLabel(loggedInEmail);
        emailLabel.setForeground(Color.BLACK);
        emailLabel.setBounds(70, 225, 250, 60);
        Font emailfont = new Font("Arial", Font.PLAIN, 20);
        emailLabel.setFont(emailfont);
        sideSection.add(emailLabel);

        userImageLabel = new JLabel();
        userImageLabel.setBounds(150, 30, 100, 100);  // Adjust the position and size as needed
        sideSection.add(userImageLabel);

        try {
            // Retrieve the image bytes from the database
            byte[] userImageBytes = database.getUserImageBytesByUsername(loggedInUsername);

            // Convert the bytes to an image
            if (userImageBytes != null && userImageBytes.length > 0) {
                BufferedImage userImage = ImageIO.read(new ByteArrayInputStream(userImageBytes));
                userImageLabel.setIcon(new ImageIcon(userImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the IOException as needed
        }


        // Clickable Sign Out Label
        JLabel signOutLabel = new JLabel("Sign out");
        signOutLabel.setForeground(Color.red); // Set text color as needed
        Font signOutFont = new Font("Arial", Font.PLAIN, 30);
        signOutLabel.setFont(signOutFont);
        signOutLabel.setBounds(150, 870, 150, 60); // Adjust position and size as needed
        signOutLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signOutLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new LoginFrame().setVisible(true);
            }
        });
        sideSection.add(signOutLabel);

        Font labelFont = new Font("Arial", Font.PLAIN, 20);

        JLabel accountSettingsLabel = new JLabel("Account settings");
        accountSettingsLabel.setForeground(Color.BLACK); // Set text color as needed
        accountSettingsLabel.setFont(labelFont);
        accountSettingsLabel.setBounds(70, 350, 150, 30); // Adjust position and size as needed
        accountSettingsLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        accountSettingsLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new AccountSettingsFrame(loggedInUsername).setVisible(true);
            }
        });
        sideSection.add(accountSettingsLabel);

        JLabel favoriteBooksLabel = new JLabel("Favorite Books");
        favoriteBooksLabel.setForeground(Color.BLACK);
        favoriteBooksLabel.setFont(labelFont);
        favoriteBooksLabel.setBounds(70, 475, 150, 30);
        favoriteBooksLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        favoriteBooksLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Show the favorite books frame
                List<Book> favoriteBooks = lib.getFavoriteBooks(loggedInUsername);
                new FavoriteFrame(loggedInUsername, favoriteBooks).setVisible(true);
            }
        });
        sideSection.add(favoriteBooksLabel);

        JLabel aboutLabel = new JLabel("About");
        aboutLabel.setForeground(Color.BLACK);
        aboutLabel.setFont(labelFont);
        aboutLabel.setBounds(70, 725, 150, 30);
        aboutLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        aboutLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new AboutFrame1(loggedInUsername).setVisible(true);
            }
        });
        sideSection.add(aboutLabel);

        //HAMBURGER ICON
        HamburgerButton hamburgerButton = new HamburgerButton();
        int hamburgerWidth = 70;
        int hamburgerHeight = 50;
        hamburgerButton.setBounds(width - hamburgerWidth - 70, 30, hamburgerWidth, hamburgerHeight);
        hamburgerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sideSectionVisible) {
                    // Show the side section
                    sideSection.setBounds(width - 400, 0, 400, height);
                    hamburgerButton.setBounds(width - hamburgerWidth - 30 - 400, 30, hamburgerWidth, hamburgerHeight);
                    sideSectionVisible = false;
                    setComponentZOrder(sideSection, 0);
                } else {
                    // Hide the side section
                    sideSection.setBounds(width, 0, 0, height);
                    hamburgerButton.setBounds(width - hamburgerWidth - 70, 30, hamburgerWidth, hamburgerHeight);
                    sideSectionVisible = true;
                    setComponentZOrder(sideSection, 0);
                }
            }
        });
        add(hamburgerButton);

        /////////////
        // Create a BookPanel with the example image and mark it as availabl


        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchTerm = searchField.getText().toLowerCase();
                for (Book bookPanel : lib.books) {
                    boolean containsTerm = bookPanel.getTitle().toLowerCase().contains(searchTerm)
                            || bookPanel.getDescription().toLowerCase().contains(searchTerm);
                    bookPanel.setVisible(containsTerm);
                }
            }
        });
        setVisible(true);
    }
}