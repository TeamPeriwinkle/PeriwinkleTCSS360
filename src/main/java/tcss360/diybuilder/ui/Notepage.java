package tcss360.diybuilder.ui;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import tcss360.diybuilder.models.Note;
import tcss360.diybuilder.models.Project;
import tcss360.diybuilder.models.User;

import javax.swing.Box;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Notepage extends JFrame {
    JFrame window;
    JTextArea textArea;
    JScrollPane scrollPane;
    JMenuBar menuBar;
    JMenu menuFile;
    JMenuItem iSave, iOpen, iDelete;
    private static User currentUser;
    private static Project currentProject;

    public static void main(String[] args) {
        
       
        SwingUtilities.invokeLater(new Runnable() {
           @Override
           public void run() {
         notepage = new Notepage(currentUser, currentProject );
            Notepage.setVisible(true);
            }
        });
    }

    public Notepage(User user, Project project) {
        this.currentUser = user;
        this.currentProject = project;
        createWindow();
        createTextArea();
        createMenuBar();
        createFileMenu();

        window.setVisible(true);
    }

    public void createWindow() {
        window = new JFrame("Notes");
        window.setSize(500, 500);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new BorderLayout());
    }

    public void createTextArea() {
        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        window.add(scrollPane);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        window.add(textArea);
    }

    public void createMenuBar() {
        menuBar = new JMenuBar();
        window.setJMenuBar(menuBar);

        menuFile = new JMenu("File");
        menuBar.add(menuFile);

        // Add a home button to go back to the user's home page
        JMenu menuHome = new JMenu();
        window.getContentPane().add(menuHome, BorderLayout.NORTH);
        ImageIcon homeIcon = new ImageIcon("homeicon.png");
        Image resizedHomeIcon = homeIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedHomeIcon);
        menuHome.setIcon(resizedIcon);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(menuHome);
        menuHome.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                UserHomePage userHomePage = new UserHomePage();
                userHomePage.setVisible(true);
                window.dispose();
            }
        });
    }

    public void createFileMenu() {
        iSave = new JMenuItem("Save");
        menuFile.add(iSave);
        iSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //String username = User.getUserName();
                //String title = Project.getTitle();
                //String noteContent = textArea.getText();
                String noteContent = textArea.getText();
                String username = currentUser.getUserName();
                String title = currentProject.getTitle();
                Note.saveNoteToDatabase(username, title, noteContent);
            }
        });

        iOpen = new JMenuItem("Open");
        menuFile.add(iOpen);
        iOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //String username = User.getUserName();
                //String title = Project.getTitle();
                //String noteContent = textArea.getText();
        String username = currentUser.getUserName();
        String title = currentProject.getTitle();
                String inputUsername = JOptionPane.showInputDialog(window, "Enter username:");
                String inputTitle = JOptionPane.showInputDialog(window, "Enter title:");

                if (inputUsername != null && inputTitle != null) {
                    String noteContent = test.openNoteFromDatabase(inputUsername, inputTitle);
                    if (noteContent != null) {
                        textArea.setText(noteContent);
                    } else {
                        JOptionPane.showMessageDialog(window, "No note found with the given username and title.");
                    }
                }
            }
        });

        iDelete = new JMenuItem("Delete");
        menuFile.add(iDelete);
        iDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //String username = User.getUserName();
                //String title = Project.getTitle();
                //String noteContent = textArea.getText();
        String username = currentUser.getUserName();
        String title = currentProject.getTitle();
                String inputUsername = JOptionPane.showInputDialog(window, "Enter username:");
                String inputTitle = JOptionPane.showInputDialog(window, "Enter title:");

                if (inputUsername != null && inputTitle != null) {
                    Note.deleteNoteFromDatabase(inputUsername, inputTitle);
                }
            }
        });
    }
}
