package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class LoginUI extends JFrame implements ActionListener {
    private JTextField desiredPersonField;
    private JTextField inputFileField;
    private JTextField outputFileField;

    public LoginUI() {
        setTitle("WhatsApp Chat Log Parser");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel with GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Desired Person field
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(new JLabel("Desired Person:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        desiredPersonField = new JTextField(20);
        panel.add(desiredPersonField, gbc);

        // Input File field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(new JLabel("Input File:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        inputFileField = new JTextField(20);
        panel.add(inputFileField, gbc);

        // Input File Browse Button
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        JButton inputFileButton = new JButton("Browse");
        inputFileButton.addActionListener(this);
        panel.add(inputFileButton, gbc);

        // Output File field
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(new JLabel("Output File:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        outputFileField = new JTextField(20);
        panel.add(outputFileField, gbc);

        // Create File Button
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton createFileButton = new JButton("Create File");
        createFileButton.addActionListener(this);
        panel.add(createFileButton, gbc);

        // Add the panel to the content pane
        getContentPane().add(panel);
        pack();

        // Set a larger size for the JFrame
        setSize(600, 300);

        // Center the JFrame on the screen
        setLocationRelativeTo(null);

        // Set the JFrame visible
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginUI();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Browse")) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text Files", "txt"));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                inputFileField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        } else if (e.getActionCommand().equals("Create File")) {
            String desiredPerson = desiredPersonField.getText();
            String inputFile = inputFileField.getText();
            String outputFileName = outputFileField.getText() + ".txt";
            String outputFile = WhatsAppChatLogParser.getOutputFilePath(outputFileName);

            WhatsAppChatLogParser.processChatLogs(desiredPerson, inputFile, outputFile);
        }
    }
}

