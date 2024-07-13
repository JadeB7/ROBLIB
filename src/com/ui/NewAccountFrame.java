package com.ui;

import com.roblib.Database;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class NewAccountFrame extends JFrame {


    private static final int width = 1960;
    private static final int height = 1080;
    private static final int fieldWidth = 700;
    private static final int fieldHeight = 50;
    private static final int gap = 50;
    private static final Color lightBlue = new Color(189, 237, 255);
    private static final Font font = new Font("Arial", Font.PLAIN, 20);
    private BufferedImage userImage; // To store the selected user image

    private RoundedTextField usernameField;
    private JCheckBox maleCheckBox;
    private JCheckBox femaleCheckBox;
    private RoundedTextField emailField;
    private RoundedTextField birthDateField;
    private RoundedTextField cinField;
    private RoundedPasswordField passwordField;
    private RoundedPasswordField confirmPasswordField;
    private JPanel imagePanel;
    private JLabel imageLabel;
    private JButton validateButton;
    NewAccountFrame()
    {
        setTitle("ROBLIB");
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

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

        //VERSION
        JLabel versionLabel = new JLabel("v 1.0");
        versionLabel.setForeground(Color.GRAY);
        Font versionFont = new Font("Arial", Font.PLAIN, 16);
        versionLabel.setFont(versionFont);
        int versionWidth = 100;
        int versionHeight = 30;
        versionLabel.setBounds(100, height - versionHeight - 100, versionWidth, versionHeight);
        add(versionLabel);

        // Define constants for field positions.
        int x = 250;
        int y = 190;

        // Username
        usernameField = new RoundedTextField(20, "enter your username");
        usernameField.setBounds(x, y, fieldWidth, fieldHeight);
        usernameField.setFont(font);
        usernameField.setBackground(lightBlue);
        add(usernameField);
        y += fieldHeight + gap;

        // Gender
        maleCheckBox = new JCheckBox("Male");
        maleCheckBox.setBounds(x + 150, y, 100, fieldHeight);
        maleCheckBox.setFont(font);
        femaleCheckBox = new JCheckBox("Female");
        femaleCheckBox.setBounds(x + 460, y, 100, fieldHeight);
        femaleCheckBox.setFont(font);


        // Implement logic to ensure only one checkbox is selected.
        maleCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    femaleCheckBox.setSelected(false);
                }
            }
        });

        femaleCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    maleCheckBox.setSelected(false);
                }
            }
        });

        add(maleCheckBox);
        add(femaleCheckBox);
        y += fieldHeight + gap;

        // Email
        emailField = new RoundedTextField(20, "enter your email");
        emailField.setBounds(x, y, fieldWidth, fieldHeight);
        emailField.setFont(font);
        emailField.setBackground(lightBlue);
        add(emailField);
        y += fieldHeight + gap;

        // Birth Date
        birthDateField = new RoundedTextField(20, "birth date: YYYY-MM-DD");
        birthDateField.setBounds(x, y, fieldWidth, fieldHeight);
        birthDateField.setFont(font);
        birthDateField.setBackground(lightBlue);
        add(birthDateField);
        y += fieldHeight + gap;

        // CIN
        cinField = new RoundedTextField(20, "enter your CIN");
        cinField.setBounds(x, y, fieldWidth, fieldHeight);
        cinField.setFont(font);
        cinField.setBackground(lightBlue);
        add(cinField);
        y += fieldHeight + gap;

        // Password
        passwordField = new RoundedPasswordField(20, "enter your password");
        passwordField.setBounds(x, y, fieldWidth, fieldHeight);
        passwordField.setFont(font);
        passwordField.setBackground(lightBlue);
        add(passwordField);
        y += fieldHeight + gap;

        // Confirm Password
        confirmPasswordField = new RoundedPasswordField(20, "confirm your password");
        confirmPasswordField.setBounds(x, y, fieldWidth, fieldHeight);
        confirmPasswordField.setFont(font);
        confirmPasswordField.setBackground(lightBlue);
        add(confirmPasswordField);

        // User Image Upload
        imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBounds(1300, 190, 300, 300);
        imagePanel.setBackground(Color.GRAY);
        imagePanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        imageLabel = new JLabel("Upload your 300x300 image", SwingConstants.CENTER);
        imageLabel.setFont(font);
        imageLabel.setForeground(Color.WHITE);

        imagePanel.add(imageLabel, BorderLayout.CENTER);
        imagePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Implement a file picker dialog to select an image
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "png");
                fileChooser.setFileFilter(filter);
                int returnVal = fileChooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        userImage = ImageIO.read(file); // Load the selected image
                        imageLabel.setText(""); // Clear the "Upload your image" text
                        imageLabel.setIcon(new ImageIcon(userImage)); // Display the selected image
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        add(imagePanel);

        //VALIDATE BUTTON
        validateButton = new JButton("Validate");
        validateButton.setFont(font);
        validateButton.setBounds(1350, 700, 200, 50);
        validateButton.setBackground(new Color(66, 183, 42)); // Green color
        validateButton.setForeground(Color.WHITE);
        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isPasswordConfirmed()) {
                    // Passwords match, proceed to save user data
                    getNewUser();
                }
                else {
                    // Passwords don't match, show an error message
                    JOptionPane.showMessageDialog(null, "The confirmed password does not match the password.", "Password Mismatch", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(validateButton);

        setVisible(true);
    }
    private Database db = new Database();

    public void getNewUser() {
        String birthDate = birthDateField.getText();
        if (!isValidBirthDateFormat(birthDate)) {
            JOptionPane.showMessageDialog(this, "Invalid birthdate format. Please use 'year-month-day' format.", "Invalid Format", JOptionPane.ERROR_MESSAGE);
            return; // Return early to prevent further processing
        }

        // Validate other fields if needed...

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(userImage, "png", baos);
            byte[] imageInByte = baos.toByteArray();

            // Save the user data
            db.saveUserWithExceptionHandling(usernameField.getText(), new String(passwordField.getPassword()), emailField.getText(), maleCheckBox.isSelected() ? "Male" : "Female", birthDateField.getText(), cinField.getText(), imageInByte);

            // If no exceptions are encountered, open the LoginFrame
            new LoginFrame().setVisible(true);
            dispose(); // Close the current frame
        } catch (Database.UserRegistrationException e) {
            String errorMessage = e.getMessage();
            JOptionPane.showMessageDialog(this, errorMessage, "Registration Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    // Validate birthdate format
    private boolean isValidBirthDateFormat(String birthDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.setLenient(false); // Ensure strict format matching
            dateFormat.parse(birthDate);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    // Add a new method to check if passwords match
    private boolean isPasswordConfirmed() {
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        return password.equals(confirmPassword);
    }
}




