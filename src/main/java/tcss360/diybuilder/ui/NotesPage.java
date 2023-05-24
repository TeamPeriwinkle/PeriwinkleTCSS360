package tcss360.diybuilder.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NotesPage extends JFrame {

    private JTextArea notesTextArea;

    public NotesPage() {
        setTitle("Notes Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        createComponents();
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
                saveNotes();
            }
        });

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(saveButton, BorderLayout.SOUTH);

        setContentPane(contentPane);
    }

    private void saveNotes() {
        String notes = notesTextArea.getText();
        // Implement your save logic here

        JOptionPane.showMessageDialog(this, "Notes saved successfully!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NotesPage().setVisible(true);
            }
        });
    }
}

