package com.APIs;

import com.ui.RoundedTextField;
import com.roblib.Database;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;
import java.util.Random;

public class SendEmail{
    public static  int randomCode;
    Database database = new Database();

    public SendEmail(JFrame your_frame){
        JFrame frame = new JFrame();

        RoundedTextField txt = new RoundedTextField(20, "enter your email");
        txt.setForeground(Color.BLACK);
        int txtWidth = 500;
        int txtHeight = 40;
        txt.setBounds(50,50,txtWidth/2,txtHeight);
        Font userFont = new Font("Arial", Font.PLAIN, 15); // Change "Arial" to your desired font family and adjust the font size (16)
        txt.setFont(userFont);
        txt.setBackground(new Color(189, 237, 255));

        JButton btn = new JButton("Send");
        btn.setBounds(115,100,120,50);
        btn.setBackground(new Color(0, 173, 239));
        btn.setForeground(Color.WHITE);
        Font buttonFont = new Font("Arial", Font.BOLD, 20); // Adjust font properties as needed
        btn.setFont(buttonFont);

        btn.addActionListener(e -> {
            Random rand = new Random();
            randomCode = rand.nextInt(99999);

            String host = "smtp.gmail.com"; // Note the correct spelling of "smtp"
            String user = "data.python13@gmail.com"; // Your Gmail email address
            // Your Gmail password
            String subject = "Resetting password";
            String username = "data.python13";
            String password = "eypwnbmeqnuzicis";
            String to = txt.getText(); // Recipient's email address
            String message = "Your reset code is " + randomCode;

            Properties prop = System.getProperties();
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", host); // Use the 'host' variable here
            prop.put("mail.smtp.port", "587");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.required", "true");


            Session mailSession = Session.getDefaultInstance(prop, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            mailSession.setDebug(true); // Set this to true for debugging

            try {
                Message msg = new MimeMessage(mailSession);
                msg.setFrom(new InternetAddress(user));
                InternetAddress address = new InternetAddress(to);
                msg.setRecipient(Message.RecipientType.TO, address);
                msg.setSubject(subject);
                msg.setText(message);

                Transport.send(msg);

                System.out.println("Email sent successfully.");
            } catch (MessagingException ex) {
                ex.printStackTrace();
            }
        });

        RoundedTextField txt1 = new RoundedTextField(20, "enter the code you received");
        txt1.setForeground(Color.BLACK);
        txt1.setBounds(50,200,txtWidth/2,txtHeight);
        Font user1Font = new Font("Arial", Font.PLAIN, 15); // Change "Arial" to your desired font family and adjust the font size (16)
        txt1.setFont(user1Font);
        txt1.setBackground(new Color(189, 237, 255));

        JButton validate = new JButton("Validate");
        validate.setBounds(115,250,120,50);
        validate.setBackground(new Color(66, 183, 42));
        validate.setForeground(Color.WHITE);
        Font validateFont = new Font("Arial", Font.BOLD, 20); // Adjust font properties as needed
        validate.setFont(validateFont);
        validate.addActionListener(e -> {
            if (Integer.parseInt(txt1.getText()) == randomCode) {
                String newPassword = JOptionPane.showInputDialog("Enter your new password:");
                // Replace 'yourDatabaseInstance' with an instance of your Database class
                try {
                    database.updatePasswordByEmail(txt.getText(), newPassword);
                    JOptionPane.showMessageDialog(null, "Password updated successfully.");
                    frame.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failed to update password. Please try again.");
                }
            }
        });

        frame.add(txt);
        frame.add(txt1);
        frame.add(btn);
        frame.add(validate);
        frame.setSize(370,400);
        ImageIcon icon = new ImageIcon(getClass().getResource("/com/Resources/Icon_Logo.png"));
        frame.setIconImage(icon.getImage());
        frame.setTitle("ROBLIB");
        frame.setLayout(null);
        frame.setLocationRelativeTo(your_frame);
        frame.setVisible(true);

    }
}