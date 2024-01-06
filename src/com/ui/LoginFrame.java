package com.ui;

import com.APIs.SendEmail;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class LoginFrame extends JFrame {
    private JCheckBox showPasswordCheckBox;
    public int page;
    private RoundedTextField usernameField;
    private RoundedPasswordField passwordField;
    public LoginFrame() {
        page = 0;
        int width = 1960;
        int height = 1080;
        setTitle("ROBLIB");
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        ImageIcon icon = new ImageIcon(getClass().getResource("/com/Resources/Icon_Logo.png"));
        setIconImage(icon.getImage());

        //THE LOGO OF THE LOGIN PAGE
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("/com/Resources/LOGO1.png"));
        JLabel logoLabel = new JLabel(logoIcon);

        int logoWidth = 500;
        int logoHeight = 500;
        logoLabel.setBounds((width - logoWidth) / 2, 10, logoWidth, logoHeight);
        add(logoLabel);

        //THE USERNAME BAR
        usernameField = new RoundedTextField(20, "username");
        usernameField.setForeground(Color.BLACK);
        int userWidth = 500;
        int userHeight = 40;
        usernameField.setBounds((width - userWidth) / 2, logoHeight - 10, userWidth, userHeight);
        Font userFont = new Font("Arial", Font.PLAIN, 20); // Change "Arial" to your desired font family and adjust the font size (16)
        usernameField.setFont(userFont);
        usernameField.setBackground(new Color(189, 237, 255));
        add(usernameField);

        //THE PASSWORD BAR
        passwordField = new RoundedPasswordField(20, "password");
        passwordField.setForeground(Color.BLACK);
        int passWidth = 500;
        int passHeight = 40;
        passwordField.setBounds((width - passWidth) / 2, logoHeight + 60, passWidth, passHeight);
        Font passFont = new Font("Arial", Font.PLAIN, 20); // Change "Arial" to your desired font family and adjust the font size (16)
        passwordField.setFont(passFont);
        passwordField.setBackground(new Color(189, 237, 255));
        showPasswordCheckBox = new JCheckBox("Show Password");
        showPasswordCheckBox.setBounds((width + passWidth + 30) / 2, logoHeight + 60, 200, 40);
        showPasswordCheckBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        showPasswordCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JCheckBox cb = (JCheckBox) e.getSource();
                passwordField.setEchoChar(cb.isSelected() ? 0 : '\u2022'); // Show or hide the password
            }
        });
        add(showPasswordCheckBox);
        add(passwordField);

        //THE CONNECT BUTTON
        JButton connectButton = new JButton("Connect");
        connectButton.setBackground(new Color(0, 173, 239)); // Set the background color to blue
        connectButton.setForeground(Color.WHITE); // Set the text color to white
        Font buttonFont = new Font("Arial", Font.BOLD, 20); // Adjust font properties as needed
        connectButton.setFont(buttonFont);
        int buttonWidth = 250;
        int buttonHeight = 40;
        connectButton.setBounds((width - buttonWidth) / 2, logoHeight + 150, buttonWidth, buttonHeight);
        // Add an ActionListener to handle the button click
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onConnectButtonClicked(); // Call your method to handle the "Connect" button click
            }
        });
        add(connectButton); // Add the button to the frame

        //FORGOT PASSWORD ?
        JLabel forgotPasswordLabel = new JLabel("forgot password ?");
        forgotPasswordLabel.setForeground(Color.BLUE); // Set the text color to blue
        forgotPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        int fpassWidth = 200;
        int fpassHeight = 30;
        forgotPasswordLabel.setBounds(((width - fpassWidth) + 80 ) / 2, logoHeight + 205, fpassWidth, fpassHeight);
        forgotPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 16));// Adjust the position and size as needed

        forgotPasswordLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SendEmail forgotPasswordFrame = new SendEmail(LoginFrame.this);
                System.out.println("FORGOT");
            }
        });
        add(forgotPasswordLabel); // Add the label to the frame

        //THE NEW ACCOUNT BUTTON
        JButton newAccountButton = new JButton("New Account");
        newAccountButton.setBackground(new Color(66, 183, 42)); // Set the background color to blue
        newAccountButton.setForeground(Color.WHITE); // Set the text color to white
        Font newAccountButtonFont = new Font("Arial", Font.BOLD, 20); // Adjust font properties as needed
        newAccountButton.setFont(newAccountButtonFont);
        int newAccountButtonWidth = 250;
        int newAccountButtonHeight = 40;
        newAccountButton.setBounds((width - newAccountButtonWidth) / 2, logoHeight + 265, newAccountButtonWidth, newAccountButtonHeight);
        newAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the LoginFrame
                new NewAccountFrame().setVisible(true);
            }
        });
        add(newAccountButton);

        //ABOUT
        JLabel aboutLabel = new JLabel("<html><u>about</u></html>"); // Use HTML to underline the text
        aboutLabel.setForeground(Color.BLUE);
        aboutLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Font aboutFont = new Font("Arial", Font.PLAIN, 16);
        aboutLabel.setFont(aboutFont);
        aboutLabel.setHorizontalAlignment(JLabel.RIGHT);
        int aboutWidth = 100;
        int aboutHeight = 30;
        aboutLabel.setBounds(width - aboutWidth - 100, height - aboutHeight - 100, aboutWidth, aboutHeight);
        aboutLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new AboutFrame().setVisible(true);
                dispose();
            }
        });
        add(aboutLabel);

        //VERSION
        JLabel versionLabel = new JLabel("v 1.0");
        versionLabel.setForeground(Color.GRAY);
        Font versionFont = new Font("Arial", Font.PLAIN, 16);
        versionLabel.setFont(versionFont);
        int versionWidth = 100;
        int versionHeight = 30;
        versionLabel.setBounds(100, height - versionHeight - 100, versionWidth, versionHeight);
        add(versionLabel);

        setVisible(true);
    }
    private void onConnectButtonClicked() {
        String username = usernameField.getText();
        char[] password = passwordField.getPassword();

        if (authenticateUser(username, new String(password))) {
            // Authentication successful, open the MainFrame
            new MainFrame(username).setVisible(true);
            dispose(); // Close the LoginFrame
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Authentication Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean authenticateUser(String username, String password) {
        // Replace these with your database connection details.
        String dbUrl = "jdbc:mysql://sql5.freesqldatabase.com:3306/sql5670955";
        String dbUser = "sql5670955";
        String dbPassword = "IWVruy5Li1";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String sql = "SELECT password FROM ROBLIB WHERE username = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);

            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                // Replace this password check with your actual password hashing and verification logic.
                if (password.equals(storedPassword)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

