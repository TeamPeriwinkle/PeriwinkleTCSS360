package tcss360.diybuilder.ui;
import tcss360.diybuilder.models.User;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * About UI + Object class.
 * @author Soe Lin
 */

public class About extends JFrame {

    private static final String VERSIONNUMBER = " 0.1";
    private String username;
    private String email;
    private JPanel myPanel;
    private User user;

    public About(String theName, String theEmail) {
        super("DIYControl");
        username = theName;
        email = theEmail;
        myPanel = new JPanel();
        user = new User(username, email);
    }

    public void display() {


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 600);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("About");
        titleLabel.setFont(new Font("", Font.BOLD, 24));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        this.add(titleLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(myPanel, gbc);

        JButton backButton = new JButton("Back");

        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(backButton, gbc);

        JButton exportButton = new JButton("Export");
        JButton importButton = new JButton("Import");

        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(importButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        this.add(exportButton, gbc);

        importButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    user.deserialize();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        exportButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try {
                    user.serialize();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });


        JLabel line0 = new JLabel("Version Number:" + VERSIONNUMBER);
        JLabel line1 = new JLabel("This app is registered to: " + username);
        JLabel line2 = new JLabel("Email address of the user: " + email);
        JLabel line3 = new JLabel("This app is provided by Team Periwinkle.");
        JLabel line4 = new JLabel("Members of Team Periwinkle:");
        JLabel line5 = new JLabel("Soe Lin, nickname: redpanda1222");
        JLabel line6 = new JLabel("Alex Garcia, nickname: froabble");
        JLabel line7 = new JLabel("Mahiliet Awasso, nickname: mahi");
        JLabel line8 = new JLabel("Mey Vo, nickname: meyww");
        JLabel line9 = new JLabel("Charmel Mbala, nickname: luckycharms0");

        myPanel.add(line0);
        myPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        myPanel.add(line1);
        myPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        myPanel.add(line2);
        myPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        myPanel.add(line3);
        myPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        myPanel.add(line4);
        myPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        myPanel.add(line5);
        myPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        myPanel.add(line6);
        myPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        myPanel.add(line7);
        myPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        myPanel.add(line8);
        myPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        myPanel.add(line9);
        myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                //need user Home Page to go back
                System.out.println("Mouse clicked!");

            }
        });

        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    public JPanel getPanel() {
        return myPanel;
    }


}