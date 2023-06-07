package tcss360.diybuilder.ui;

import tcss360.diybuilder.models.Project;
import tcss360.diybuilder.models.User;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.Box;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI for notepage
 * @author Mahiliet Awasso
 */
public class NotesPage extends JFrame {

    //datafield
    JTextArea textArea;
    JScrollPane scrollPane;
    JMenuBar menuBar;
    JMenu menuFile;
    JMenuItem iSave, iOpen;

    //current user and project
    private static User currentUser;
    private static Project currentProject;


    /**
     * constructor for notes page
     * @param user currently signed in user
     * @param project currently signed in project
     */
    public NotesPage(User user, Project project) {
        super("Note");
        this.currentUser = user;
        this.currentProject = project;

        //create the window
        createWindow();
        createTextArea();
        createMenuBar();
        createFileMenu();

    }

    public void display(){
        this.setVisible(true);
    }
    /**
     * sets up the ovarall frame
     * @author Mahiliet Awasso
     */
    public void createWindow() {

        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
    }


    /**
     * create text area
     * @author Mahiliet Awasso
     */
    public void createTextArea() {
        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollPane);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        //this.add(textArea);
    }

    /**
     * create the menu bar on the top left
     * @author Mahiliet Awasso
     */
    public void createMenuBar() {
        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        menuFile = new JMenu("File");
        menuBar.add(menuFile);

        // Add a home button to go back to the user's home page
        JMenu menuHome = new JMenu();
        this.getContentPane().add(menuHome, BorderLayout.NORTH);
        ImageIcon homeIcon = new ImageIcon("homeicon.png");
        Image resizedHomeIcon = homeIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedHomeIcon);
        menuHome.setIcon(resizedIcon);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(menuHome);
        menuHome.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                UserHomePage userHomePage = new UserHomePage(currentUser);
                dispose();
                userHomePage.setVisible(true);

            }
        });
    }

    /**
     * creating a menu item(speciffically the save delete open items)
     * @author Mahiliet Awasso
     */
    public void createFileMenu() {
        iSave = new JMenuItem("Save");
        menuFile.add(iSave);
        iSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String noteContent = textArea.getText();
                currentProject.setNote(noteContent);
            }
        });

        iOpen = new JMenuItem("Open");
        menuFile.add(iOpen);
        iOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                //display the note for the current project
                textArea.setText(currentProject.getNote());
            }
        });
    }
}
