package tcss360.diybuilder.ui;

import tcss360.diybuilder.models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class NotePage extends JFrame {

    private JTextArea notesTextArea;
    private String fileName;
    private JMenuBar menuBar;
    private User myUser;

    public NotePage(User theUser) {
        setTitle("Notes Page");
        myUser = theUser;
        fileName = "src/main/resources/UserNotes/" + myUser.getUserName() + "Notes.txt";
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        createComponents();
        createMenuBar();

        // Load existing notes from the file
        loadNotesFromFile();
    }

    private void createComponents() {
        notesTextArea = new JTextArea();
        notesTextArea.setFont(new Font("Arial", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(notesTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveNotesToFile();
            }
        });

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(saveButton, BorderLayout.SOUTH);

        setContentPane(contentPane);
    }

    private void loadNotesFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            StringBuilder notes = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                notes.append(line).append("\n");
            }
            notesTextArea.setText(notes.toString());
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private void saveNotesToFile() {
        String notesText = notesTextArea.getText();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(notesText);
            JOptionPane.showMessageDialog(this, "Notes saved successfully!");
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Failed to save notes.");
        }
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();

        ImageIcon backIcon = new ImageIcon("src/main/resources/backicon.png");
        ImageIcon resizedBackIcon = new ImageIcon(backIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
        JButton backIconButton = new JButton(resizedBackIcon);
        backIconButton.setFocusable(false);
        backIconButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                UserHomePage u = new UserHomePage(myUser);
                u.display();
            }
        });

        menuBar.add(backIconButton);

        setJMenuBar(menuBar);
    }

}

