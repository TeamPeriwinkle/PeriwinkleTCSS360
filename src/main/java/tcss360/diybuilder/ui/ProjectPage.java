package tcss360.diybuilder.ui;

import tcss360.diybuilder.models.Project;
import tcss360.diybuilder.models.Task;
import tcss360.diybuilder.models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class ProjectPage extends JFrame {

    private JPanel projectPanel;
    private JMenuBar menuBar;
    private JMenu settingsSection;
    private Project project;
    private JPanel taskListPanel;
    private User myUser;


    public ProjectPage(Project theP, User theUser) {
        super(theP.getName());
        project = theP;
        myUser = theUser;
        display();
        createMenuBar();
        setVisible(true);
    }

    public void display() {
        //set layout
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(500, 500);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        //creat panel
        projectPanel = new JPanel(new BorderLayout(10, 10));
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.5);

        // description
        JPanel descriptionPanel = new JPanel(new BorderLayout());
        JLabel descriptionLabel = new JLabel("Project Description:");
        JTextArea descriptionTextArea = new JTextArea(project.getDescription());
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setEditable(false);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);
        descriptionPanel.add(descriptionLabel, BorderLayout.NORTH);
        descriptionPanel.add(descriptionScrollPane, BorderLayout.CENTER);

        // Task
        JPanel tasksPanel = new JPanel(new BorderLayout());
        JLabel tasksLabel = new JLabel("Tasks:");
        JPanel createTaskPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton createTaskButton = new JButton("+ Add Task");
        createTaskPanel.add(createTaskButton);
        tasksPanel.add(tasksLabel, BorderLayout.NORTH);
        tasksPanel.add(createTaskPanel, BorderLayout.CENTER);

        splitPane.setLeftComponent(descriptionPanel);
        splitPane.setRightComponent(tasksPanel);

        projectPanel.add(splitPane, BorderLayout.CENTER);

        taskListPanel = new JPanel();
        taskListPanel.setLayout(new BoxLayout(taskListPanel, BoxLayout.Y_AXIS));
        JScrollPane taskListScrollPane = new JScrollPane(taskListPanel);
        tasksPanel.add(taskListScrollPane, BorderLayout.SOUTH);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(projectPanel, BorderLayout.CENTER);

        // add task button
        createTaskButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JPanel taskDetailsPanel = new JPanel(new GridLayout(4, 2));

                taskDetailsPanel.add(new JLabel("Name:"));
                JTextField taskNameField = new JTextField();
                taskDetailsPanel.add(taskNameField);
                taskDetailsPanel.add(new JLabel("PN:"));
                JTextField pnField = new JTextField();
                taskDetailsPanel.add(pnField);
                taskDetailsPanel.add(new JLabel("Start Date:"));
                JTextField startDateField = new JTextField();
                taskDetailsPanel.add(startDateField);

                int result = JOptionPane.showConfirmDialog(ProjectPage.this, taskDetailsPanel, "Create a new task", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String taskName = taskNameField.getText();
                    String pn = pnField.getText();
                    String startDate = startDateField.getText();

                    Task task = new Task(taskName, pn, startDate);
                    Task.add(task);
                    
                }
            }
        });
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();
        settingsSection = new JMenu();

        // Add back icon image
        ImageIcon backIcon = new ImageIcon("src/main/resources/backicon.png");
        Image resizedBackIcon = backIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon resizedBackIconImage = new ImageIcon(resizedBackIcon);

        // Add task icon image
        ImageIcon taskIcon = new ImageIcon("src/main/resources/taskicon.png");
        Image resizedTaskIcon = taskIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedTaskIcon);

        menuBar.add(Box.createRigidArea(new Dimension(450, 0)));
        settingsSection.setIcon(resizedBackIconImage);
        settingsSection.setIcon(resizedIcon);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(settingsSection);

        JMenuItem noteMenuItem = new JMenuItem("Note");
        settingsSection.add(noteMenuItem);

        noteMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                NotesPage n = new NotesPage();
            }
        });

//        JMenuItem aboutMenuItem = new JMenuItem("About");
//        settingsSection.addSeparator();
//        settingsSection.add(aboutMenuItem);

//        aboutMenuItem.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                dispose();
////                About aboutPage = new About();
////                aboutPage.display();
//            }
//        });

        JMenuItem budgetMenuItem = new JMenuItem("Budget");
        settingsSection.addSeparator();
        settingsSection.add(budgetMenuItem);

        budgetMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                BudgetPage b = new BudgetPage(project, myUser);
                b.display();
            }
        });

        JMenuItem backMenuItem = new JMenuItem("Back");
        settingsSection.addSeparator();
        settingsSection.add(backMenuItem);

        backMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                UserHomePage u = new UserHomePage(myUser);
                u.display();
            }
        });

        setJMenuBar(menuBar);
    }
}