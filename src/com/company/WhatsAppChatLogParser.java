package com.company;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.filechooser.FileSystemView;
import java.io.File;



public class WhatsAppChatLogParser {

    public static void main(String[] args) {
        LoginUI.main(args);
    }

    public static void processChatLogs(String desiredPerson, String inputFile, String outputFile) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

            String line;
            boolean isFirstMessage = true;
            int totalMessagesSent = 0;
            long totalCharactersTyped = 0;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                // Check if the line starts with a date and time pattern
                if (line.matches("\\[\\d{1,2}/\\d{1,2}/\\d{2}, \\d{1,2}:\\d{2}:\\d{2} (AM|PM)] .*")) {
                    // Extract the sender from the line
                    int startIndex = line.indexOf("]") + 2;
                    int endIndex = line.indexOf(":", startIndex);
                    if (endIndex == -1) {
                        endIndex = line.length();
                    }
                    String sender = line.substring(startIndex, endIndex);

                    // Check if the sender matches the desired person
                    if (sender.equals(desiredPerson)) {
                        // Write the separator line and empty lines
                        if (!isFirstMessage) {
                            writer.newLine();
                            writer.write("---------------------------------------------------------------");
                            writer.newLine();
                            writer.newLine();
                        }

                        // Write the formatted message to the output file
                        writer.write(line);
                        writer.newLine();

                        isFirstMessage = false;
                        totalMessagesSent++;
                        totalCharactersTyped += line.length() - endIndex;
                    }
                } else {
                    // Check if the line starts with a square bracket and a date pattern
                    if (line.matches("\\[\\d{1,2}/\\d{1,2}/\\d{2}, .*")) {
                        // Write a separator line before a new message starts
                        writer.newLine();
                        writer.write("---------------------------------------------------------------");
                        writer.newLine();
                    }
                }
            }

            // Write the statistics at the bottom of the document
            writer.newLine();
            writer.write("---------------------------------------------------------------");
            writer.newLine();
            writer.newLine();
            writer.write("Total Messages Sent: " + String.format("%.0f", (double) totalMessagesSent));
            writer.newLine();
            writer.newLine();
            writer.write("Total Characters Typed: " + String.format("%.0f", (double) totalCharactersTyped));
            writer.newLine();

            reader.close();
            writer.close();

            System.out.println("Chat logs of " + desiredPerson + " exported successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String getOutputFilePath(String outputFileName) {
        String userHome = System.getProperty("user.home");
        String downloadsDir = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + File.separator + "Downloads";
        return downloadsDir + File.separator + outputFileName;
    }

}
