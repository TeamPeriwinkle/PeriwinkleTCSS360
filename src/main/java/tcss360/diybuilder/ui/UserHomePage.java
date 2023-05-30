package tcss360.diybuilder.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class UserHomePage extends JFrame {

    private JButton aboutButton;
    private JButton projectButton;
    private JMenuBar menuBar;
    private JButton createProjectButton;
    private JMenu settingsSection;
    private JPanel projectPanel;
    private JPanel projectListPanel;
    private JLabel titleLabel;

    public UserHomePage() {

        setTitle("DIY Control");
        setSize(500, 500);
        setLayout(new BorderLayout());

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // About button
        aboutButton = new JButton("About");
        aboutButton.setPreferredSize(new Dimension(100, 50));
        buttonPanel.add(aboutButton);

        // Projects button
        projectButton = new JButton("Projects");
        projectButton.setPreferredSize(new Dimension(100, 50));
        buttonPanel.add(projectButton);

        // Add button panel to the frame
        add(buttonPanel, BorderLayout.CENTER);
        //Create the menu bar
        createMenuBar();

        // Button actions

        //About Button
        aboutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create and show the UserHomePage
                dispose();
                About a = new About("Soe", "soehtet2002@gmail.com");
                a.display();
            }
        });

        //Project Button
        projectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create and show the project page
                final JFrame projectPage = new JFrame("DIY CONTROL");
                projectPage.setSize(500, 500);

                projectPanel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(-350, 10, 10, 10);

                titleLabel = new JLabel("DIY CONTROL");
                titleLabel.setFont(new Font("", Font.BOLD, 24));
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridwidth = 2;
                projectPanel.add(titleLabel, gbc);

                // Create a new project button
                createProjectButton = new JButton("Create a new project");
                createProjectButton.setPreferredSize(new Dimension(250, 30));
                createProjectButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JPanel projectDetailsPanel = new JPanel(new GridLayout(4, 2));

                        //Creat new project Tittle, Budget, Plan, Description
                        projectDetailsPanel.add(new JLabel("Title:"));
                        JTextField projectNameField = new JTextField();
                        projectDetailsPanel.add(projectNameField);
                        projectDetailsPanel.add(new JLabel("Budget:"));
                        JTextField budgetField = new JTextField();
                        projectDetailsPanel.add(budgetField);
                        projectDetailsPanel.add(new JLabel("Plan (Optional):"));
                        JTextField planField = new JTextField();
                        projectDetailsPanel.add(planField);
                        projectDetailsPanel.add(new JLabel("Description:"));
                        JTextField descriptionField = new JTextField();
                        projectDetailsPanel.add(descriptionField);

                        int result = JOptionPane.showConfirmDialog(projectPage, projectDetailsPanel, "Create a new project", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                        if (result == JOptionPane.OK_OPTION) {
                            String projectName = projectNameField.getText();
                            String budgetText = budgetField.getText();
                            double budget = Double.parseDouble(budgetText);
                            String plan = planField.getText();
                            String description = descriptionField.getText();

                            if (!projectName.trim().isEmpty()) {
                                boolean saved = saveProject(projectName, budget, plan, description);
                                if (saved) {
                                    JOptionPane.showMessageDialog(projectPage, "New project created and saved: " + projectName, "Create Project", JOptionPane.INFORMATION_MESSAGE);
                                    updateProjectList();
                                } else {
                                    JOptionPane.showMessageDialog(projectPage, "Failed to save the project", "Create Project", JOptionPane.WARNING_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(projectPage, "Invalid project name", "Create Project", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    }
                });
                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.gridwidth = 2;
                gbc.insets = new Insets(-300, 10, 10, 10);
                projectPanel.add(createProjectButton, gbc);
                projectListPanel = new JPanel(new GridBagLayout());
                GridBagConstraints gbcList = new GridBagConstraints();
                gbcList.gridx = 0;
                gbcList.gridy = 0;
                gbcList.insets = new Insets(10, 10, 10, 10);
                projectPanel.add(projectListPanel, gbcList);
                projectPage.add(projectPanel, BorderLayout.CENTER);
                projectPage.setJMenuBar(menuBar);
                projectPage.setVisible(true);
                updateProjectList();
            }
        });
        createMenuBar(); // taskicon
        this.setLocationRelativeTo(null);
        setVisible(true);
    }

    private boolean saveProject(String title, double budget, String plan, String description) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("Data/projects.csv", true))) {
            bw.write(title + "," + budget + "," + plan + "," + description);
            bw.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void updateProjectList() {
        projectListPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        try (BufferedReader br = new BufferedReader(new FileReader("Data/projects.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] projectData = line.split(",");
                if (projectData.length >= 4) {
                    final String projectName = projectData[0];

                    // Create the project button
                    JButton projectButton = new JButton(projectName);
                    projectButton.setHorizontalAlignment(SwingConstants.LEFT);
                    projectButton.setPreferredSize(new Dimension(220, 25));

                    // Create the delete
                    JLabel deleteLabel = new JLabel("-");
                    deleteLabel.setFont(new Font("", Font.BOLD, 20));
                    deleteLabel.setForeground(Color.RED);
                    deleteLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

                    deleteLabel.addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent e) {
                            int confirm = JOptionPane.showConfirmDialog(projectListPanel, "Are you sure you want to delete this project?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                            if (confirm == JOptionPane.YES_OPTION) {
                                // Delete the project
                                boolean deleted = deleteProject(projectName);
                                if (deleted) {
                                    JOptionPane.showMessageDialog(projectListPanel, "Project deleted: " + projectName, "Delete Project", JOptionPane.INFORMATION_MESSAGE);
                                    updateProjectList();
                                } else {
                                    JOptionPane.showMessageDialog(projectListPanel, "Failed to delete the project", "Delete Project", JOptionPane.WARNING_MESSAGE);
                                }
                            }
                        }

                        private boolean deleteProject(String projectName) {
                            return true;
                        }
                    });
                    projectButton.setLayout(new BorderLayout());
                    projectButton.add(deleteLabel, BorderLayout.EAST);
                    projectListPanel.add(projectButton, gbc);
                    gbc.gridy++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        projectListPanel.revalidate();
        projectListPanel.repaint();
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();
        settingsSection = new JMenu();

        // add taskicon image
        ImageIcon taskIcon = new ImageIcon("taskicon.png");
        Image resizedTaskIcon = taskIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedTaskIcon);

        settingsSection.setIcon(resizedIcon);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(settingsSection);

        // Create "Sign Out" menu item
        JMenuItem signOutMenuItem = new JMenuItem("Sign Out");
        settingsSection.add(signOutMenuItem);

        signOutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); //sign up
            }
        });

        settingsSection.addSeparator();
        setJMenuBar(menuBar);
    }
}
