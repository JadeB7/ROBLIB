package com.ui;

import com.roblib.Database;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


public class AccountSettingsFrame extends JFrame {
    private static final int width = 1000;
    private static final int height = 800;
    private final String username;
    Database database = new Database();

    public AccountSettingsFrame(String username) {
        this.username = username;
        setTitle("ROBLIB - Account Settings");
        setSize(width, height);
        setLayout(null);
        ImageIcon icon = new ImageIcon(getClass().getResource("/com/Resources/Icon_Logo.png"));
        setIconImage(icon.getImage());
        setLocation(1960/2 - 500, 1080/2 - 400);

        // Add the components to display user information
        displayUserInformation(username);

        // Add an "OK" button
        JButton okButton = new JButton("OK");
        okButton.setBounds(width - 150, height - 150, 80, 30);
        okButton.setBackground(new Color(0, 173, 239));
        okButton.setForeground(Color.WHITE);
        Font okbuttonFont = new Font("Arial", Font.BOLD, 20); // Adjust font properties as needed
        okButton.setFont(okbuttonFont);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the frame when the "OK" button is clicked
            }
        });
        add(okButton);

        setVisible(true);
    }

    private void displayUserInformation(String username) {
        // Use the Database class to retrieve user information based on the username
        Database database = new Database();
        // Retrieve user information
        String email = database.getEmailByUsername(username);
        byte[] userImageBytes = database.getUserImageBytesByUsername(username);
        String gender = database.getGenderByUsername(username);
        String birthdate = database.getBirthdateByUsername(username);
        String cin = database.getCinByUsername(username);
        String password = database.getPasswordByUsername(username);

        // Display the information on the frame
        // Add the user image (assuming you have a JLabel for the image)
        JLabel imageLabel = new JLabel();
        imageLabel.setBounds(600, 50, 300, 300); // Adjust the position and size as needed
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.RED));

        if (userImageBytes != null) {
            ImageIcon userImageIcon = new ImageIcon(userImageBytes);
            Image scaledImage = userImageIcon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImage));

            // Add a mouse listener to the image to handle the click event
            imageLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Open a file dialog to allow the user to choose a new image
                    JFileChooser fileChooser = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "png", "jpg", "jpeg", "gif");
                    fileChooser.setFileFilter(filter);
                    int returnVal = fileChooser.showOpenDialog(null);

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();

                        try {
                            // Read the selected image file
                            BufferedImage newImage = ImageIO.read(file);

                            // Convert the BufferedImage to byte array
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            ImageIO.write(newImage, "png", baos);
                            byte[] newImageBytes = baos.toByteArray();

                            // Update the database with the new image
                            database.updateUserImage(username, newImageBytes);

                            // Update the displayed image
                            Image scaledNewImage = newImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                            imageLabel.setIcon(new ImageIcon(scaledNewImage));

                            new LoginFrame().setVisible(true);
                            dispose();


                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
        }

        add(imageLabel);

        Font labelFont = new Font("Arial", Font.PLAIN, 30);
        // Add other components
        JLabel usernameLabel = new JLabel("Username: " + username);
        usernameLabel.setBounds(50, 50, 500, 30);
        usernameLabel.setFont(labelFont);
        add(usernameLabel);

        JLabel emailLabel = new JLabel("Email: " + email);
        emailLabel.setBounds(50, 150, 500, 30);
        emailLabel.setFont(labelFont);
        add(emailLabel);

        JLabel genderLabel = new JLabel("Gender: " + gender);
        genderLabel.setBounds(50, 250, 500, 30);
        genderLabel.setFont(labelFont);
        add(genderLabel);

        JLabel birthdateLabel = new JLabel("Birthdate: " + birthdate);
        birthdateLabel.setBounds(50, 350, 500, 30);
        birthdateLabel.setFont(labelFont);
        add(birthdateLabel);

        JLabel cinLabel = new JLabel("CIN: " + cin);
        cinLabel.setBounds(50, 450, 500, 30);
        cinLabel.setFont(labelFont);
        add(cinLabel);

        JLabel passwordLabel = new JLabel("Password: " + password); // Avoid displaying the actual password
        passwordLabel.setBounds(50, 550, 500, 30);
        passwordLabel.setFont(labelFont);
        add(passwordLabel);
    }
}
