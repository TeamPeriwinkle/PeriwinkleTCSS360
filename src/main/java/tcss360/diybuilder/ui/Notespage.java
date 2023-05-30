package tcss360.diybuilder.ui;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Box;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import tcss360.diybuilder.models.*;

public class Notespage implements ActionListener {
    JFrame window;
    JTextArea textArea;
    JScrollPane scrollPane;
    JMenuBar menuBar;
    JMenu menuHome, menuFile, menuEdit, menuFormat;
    JMenuItem iNew, iOpen, iSave, iSaveAs, iExit;


    public static void main(String[] args) {

        new Notespage();

    }

    public Notespage() {
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

        // Create the menu bar
        // menuBar = new JMenuBar();//homeicon
        //
    }

    public void createTextArea() {
        textArea = new JTextArea();

        scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        window.add(scrollPane);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        // window.add(textArea);
    }

    public void createMenuBar() {

        menuBar = new JMenuBar();
        window.setJMenuBar(menuBar);

        menuFile = new JMenu("File");
        menuBar.add(menuFile);

        menuEdit = new JMenu("Edit");
        menuBar.add(menuEdit);

        menuFormat = new JMenu("Format");
        menuBar.add(menuFormat);

        //menuHome = new JMenu("Home");


        menuHome = new JMenu();
        window.getContentPane().add(menuHome, BorderLayout.NORTH);
        // Create the home icon
        ImageIcon homeIcon = new ImageIcon("homeicon.png");
        Image resizedHomeIcon = homeIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedHomeIcon);

        menuHome.setIcon(resizedIcon);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(menuHome);

        // Add a mouse listener to the home icon button
        menuHome.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //need user Home Page to go back
                 UserHomePage userHomePage = new UserHomePage();
                 userHomePage.setVisible(true);
                window.dispose();
            }
        });


    }

    public void createFileMenu() {

        iNew = new JMenuItem("New");
        iNew.addActionListener(this);
        iNew.setActionCommand("New");
        menuFile.add(iNew);

        iOpen = new JMenuItem("Open");
        iOpen.addActionListener(this);
        iOpen.setActionCommand("Open");
        menuFile.add(iOpen);

        iSave = new JMenuItem("Save");
        menuFile.add(iSave);

        iSaveAs = new JMenuItem("SaveAs");
        menuFile.add(iSaveAs);

        iExit = new JMenuItem("Exit");
        menuFile.add(iExit);
    }

    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        switch (command) {

            case "New":
                //file.newFile();
                break;
            // case "Open":file.Open(); break;
        }
    }
}
