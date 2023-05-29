package tcss360.diybuilder.ui;
/**
 * Mey
 */
import tcss360.diybuilder.models.Project;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class UserHomePage extends JFrame {

    private JMenuBar menuBar;
    private JMenu settingsSection;
    private JPanel projectListPanel;
    private ArrayList<Project> projects;
    private String username; 
   
    

    public UserHomePage(String theName) {
        super("DIY Control");
        projects = new ArrayList<Project>();
        username = theName;
        createMenuBar();
    }

    public void display() {
        //set layout
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Welcome Label
        JLabel welcomeUser = new JLabel("Welcome Back " + username, null, SwingConstants.CENTER);
        welcomeUser.setFont(new Font("Arial", Font.PLAIN, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Make this component span across two columns
        gbc.insets = new Insets(10, 10, 50, 10); // Change this to create desired space below the label
        this.add(welcomeUser, gbc);

        // Reset insets 
        

        // Panel of  "+" and "Create new project" labels
        JPanel projectCreatePanel = new JPanel();
        projectCreatePanel.setLayout(new BoxLayout(projectCreatePanel, BoxLayout.X_AXIS));
        gbc.insets = new Insets(10, 10, 10, 10);
        // creat "+" create new project
        JLabel createProjectLabel = new JLabel("+");
        createProjectLabel.setToolTipText("Create new project");
        createProjectLabel.setFont(new Font("", Font.BOLD, 20));
        createProjectLabel.setForeground(Color.RED);
        projectCreatePanel.add(createProjectLabel);

        // Add space between "+" and "Create new project" labels
        projectCreatePanel.add(Box.createHorizontalStrut(10)); // Change the number 10 to increase or decrease the space

        // Create "Create new project" label
        JLabel projectLabel = new JLabel("Create new project");
        projectLabel.setHorizontalAlignment(SwingConstants.LEFT);
        projectCreatePanel.add(projectLabel);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        this.add(projectCreatePanel, gbc);


        createProjectLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JPanel projectDetailsPanel = new JPanel(new GridLayout(4, 2));

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

                int result = JOptionPane.showConfirmDialog(UserHomePage.this, projectDetailsPanel, "Create a new project", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String projectName = projectNameField.getText();
                    String budgetText = budgetField.getText();
                    double budget = Double.parseDouble(budgetText);
                    String plan = planField.getText();
                    String description = descriptionField.getText();
                    if (!projectName.trim().isEmpty()) {
                        Project newProject = new Project(projectName, budget, plan, description);
                        projects.add(newProject);
                        JOptionPane.showMessageDialog(UserHomePage.this, "New project created: " + projectName, "Create Project", JOptionPane.INFORMATION_MESSAGE);
                        updateProjectList();
                    } else {
                        JOptionPane.showMessageDialog(UserHomePage.this, "Invalid project name", "Create Project", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        projectListPanel = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(projectListPanel, gbc);
        this.setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateProjectList() {
        projectListPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        for (Project project : projects) {
            final String projectName = project.getName();

            JButton projectButton = new JButton(projectName);
            projectButton.setHorizontalAlignment(SwingConstants.LEFT);
            projectButton.setPreferredSize(new Dimension(220, 25));

            JLabel deleteLabel = new JLabel("-");
            deleteLabel.setFont(new Font("", Font.BOLD, 20));
            deleteLabel.setForeground(Color.RED);
            deleteLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            deleteLabel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    int confirm = JOptionPane.showConfirmDialog(projectListPanel, "Are you sure you want to delete this project?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        boolean deleted = deleteProject(projectName);
                        if (deleted) {
                            JOptionPane.showMessageDialog(projectListPanel, "Project deleted: " + projectName, "Delete Project", JOptionPane.INFORMATION_MESSAGE);
                            updateProjectList();
                        } else {
                            JOptionPane.showMessageDialog(projectListPanel, "Failed to delete the project", "Delete Project", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            });

            projectButton.setLayout(new BorderLayout());
            projectButton.add(deleteLabel, BorderLayout.EAST);
            projectListPanel.add(projectButton, gbc);
            gbc.gridy++;
        }

        projectListPanel.revalidate();
        projectListPanel.repaint();
    }

    private boolean deleteProject(String projectName) {
        for (Project project : projects) {
            if (project.getName().equals(projectName)) {
                projects.remove(project);
                return true;
            }
        }
        return false;
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();

        settingsSection = new JMenu();

        ImageIcon taskIcon = new ImageIcon("src/main/resources/taskicon.png");
        Image resizedTaskIcon = taskIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedTaskIcon);

        menuBar.add(Box.createRigidArea(new Dimension(400, 0)));
        settingsSection.setIcon(resizedIcon);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(settingsSection);

        JMenuItem signOutMenuItem = new JMenuItem("Sign Out");
        settingsSection.add(signOutMenuItem);

        signOutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DIYControl jFrame = new DIYControl();
                jFrame.display();
            }
        });

        JMenuItem aboutMenuItem = new JMenuItem("About");
        settingsSection.add(aboutMenuItem);

        aboutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                About aboutPage = new About();
                aboutPage.display();
            }
        });

        JMenuItem projectMenuItem = new JMenuItem("Project");
        settingsSection.add(projectMenuItem);

        projectMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                ProjectPage projectPage = new ProjectPage();
                projectPage.display();
            }
        });

        settingsSection.addSeparator();
        setJMenuBar(menuBar);
    }
}
