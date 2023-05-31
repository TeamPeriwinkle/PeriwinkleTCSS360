package tcss360.diybuilder.ui;
// /**
//  * @author Mey Vo
//  */
import tcss360.diybuilder.models.Project;
import tcss360.diybuilder.models.Task;
import tcss360.diybuilder.models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ProjectPage extends JFrame {

    private JMenuBar menuBar;
    private JMenu settingsSection;
    private Project project;
    private JPanel taskListPanel;
    private User myUser;

    public ProjectPage(Project theP, User theUser) {
        super(theP.getName());
        project = theP;
        myUser = theUser;
        createMenuBar();
        setVisible(true);
    }

    public void display() {
        // Set layout
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Project label
        JLabel projectName = new JLabel("Project " + project.getName(), null, SwingConstants.CENTER);
        projectName.setFont(new Font("Arial", Font.PLAIN, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Make this component span across two columns
        gbc.insets = new Insets(10, 10, 50, 10); // Change this to create desired space below the label
        this.add(projectName, gbc);

        // Description
        JLabel descriptionLabel = new JLabel("Description: " + project.getDescription());
        JTextArea descriptionTextArea = new JTextArea(project.getDescription());
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setEditable(false);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // Adjust as needed
        gbc.insets = new Insets(10, 10, 10, 10); // Adjust as needed
        this.add(descriptionLabel, gbc);

        // Panel of "+" and "Create new Task" labels
        JPanel taskCreatePanel = new JPanel();
        taskCreatePanel.setLayout(new BoxLayout(taskCreatePanel, BoxLayout.X_AXIS));
        gbc.insets = new Insets(10, 10, 10, 10); // Adjust as needed

        // Create "+"
        JLabel createTaskLabel = new JLabel("+");
        createTaskLabel.setToolTipText("Create new Task");
        createTaskLabel.setFont(new Font("", Font.BOLD, 20));
        createTaskLabel.setForeground(Color.RED);
        taskCreatePanel.add(createTaskLabel);

        // Add space between "+" and "Create new project" labels
        taskCreatePanel.add(Box.createHorizontalStrut(10)); // Change the number 10 to increase or decrease the space

        // Create "Create new project" label
        JLabel taskLabel = new JLabel("Create new Task");
        taskLabel.setHorizontalAlignment(SwingConstants.LEFT);
        taskCreatePanel.add(taskLabel);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        this.add(taskCreatePanel, gbc);

        //creat TaskLabel include Name, PN and Start Date
        createTaskLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JPanel taskDetailsPanel = new JPanel(new GridLayout(3, 2));

                taskDetailsPanel.add(new JLabel("Name:"));
                JTextField taskNameField = new JTextField();
                taskDetailsPanel.add(taskNameField);

                taskDetailsPanel.add(new JLabel("PN:"));
                JTextField taskPNField = new JTextField();
                taskDetailsPanel.add(taskPNField);

                taskDetailsPanel.add(new JLabel("Start date:"));
                JTextField taskDateField = new JTextField();
                taskDetailsPanel.add(taskDateField);

                // Show the panel in a JOptionPane
                int result = JOptionPane.showConfirmDialog(ProjectPage.this, taskDetailsPanel, "Create a new task", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String name = taskNameField.getText();
                    String pnString = taskPNField.getText();
                    int pn = Integer.parseInt(pnString); 
                    String startDate = taskDateField.getText();

                    // Create new task
                    Task newTask = new Task(name, pn, startDate);
                    newTask.setName(name); // Set the task name
                    newTask.setPn(pn); // Set the task priority number
                    newTask.setStartDate(startDate); // Set the task start date

                    // Add task to the project
                    project.addTask(newTask);

                    JOptionPane.showMessageDialog(ProjectPage.this, "New task created: " + newTask.getName(), "Create Task", JOptionPane.INFORMATION_MESSAGE);

                    // Update the list of tasks
                    updateTaskList();
                }
            }
        });

        taskListPanel = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(taskListPanel, gbc);
        updateTaskList();

        this.setLocationRelativeTo(null);
        setVisible(true);
    }

    //creat updateTaskList
    private void updateTaskList() {
        taskListPanel.removeAll(); 
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0; 
    
        // Get the list of tasks from the project
        ArrayList<Task> tasks = project.getTasks();
    
     
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            String taskName = task.getName();
    
            // Mouseclick delete project
            TaskButton taskButton = new TaskButton(taskName, i);
            taskButton.getDeleteLabelLabel().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int confirm = JOptionPane.showConfirmDialog(taskListPanel, "Are you sure you want to delete this task?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        boolean deleted = deleteTask(taskButton.getIndex());
                        if (deleted) {
                            JOptionPane.showMessageDialog(taskListPanel, "Task deleted: " + taskButton.getText(), "Delete Task", JOptionPane.INFORMATION_MESSAGE);
                            updateTaskList();
                        } else {
                            JOptionPane.showMessageDialog(taskListPanel, "Failed to delete the task", "Delete Task", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            });

               // Add the TaskButton to the task list panel
               gbc.gridy++; 
               taskListPanel.add(taskButton, gbc);
               taskButton.addActionListener(null);
            }   
    
            // Refresh the task list panel
            taskListPanel.revalidate();
            taskListPanel.repaint();
        }
    
    
    // deletet Task
    private boolean deleteTask(int index) {
        ArrayList<Task> tasks = project.getTasks();
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            return true;
        }
        return false;
    }

    // Create MenuBar
    private void createMenuBar() {
        menuBar = new JMenuBar();
        settingsSection = new JMenu();

        // Add back icon image
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

        // Add task icon image
        ImageIcon taskIcon = new ImageIcon("src/main/resources/taskicon.png");
        Image resizedTaskIcon = taskIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedTaskIcon);

        // Create back icon panel
        JPanel backIconPanel = new JPanel();
        backIconPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        backIconPanel.add(backIconButton);

        menuBar.add(backIconPanel);
        menuBar.add(Box.createHorizontalGlue());
        settingsSection.setIcon(resizedIcon);
        menuBar.add(settingsSection); // Add settingsSection to the menu bar

        // Create "Budget" menu item
        JMenuItem budgetMenuItem = new JMenuItem("Budget");
        budgetMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                BudgetPage b = new BudgetPage(project, myUser);
                b.display();
            }
        });
        settingsSection.add(budgetMenuItem); // Add the Budget menu item to settingsSection

        setJMenuBar(menuBar);
    }

    // creat TaskButton
    private class TaskButton extends JButton {
        private int index;
        private JLabel deleteLabel;
    
        public TaskButton(String name, int theIndex) {
            super(name);
            index = theIndex;
            setup();
        }

        public int getIndex() {
            return index;
        }

        public JLabel getDeleteLabelLabel() {
            return deleteLabel;
        }
    
        private void setup() {
            this.setHorizontalAlignment(SwingConstants.LEFT);
            this.setPreferredSize(new Dimension(220, 25));
            this.setFocusable(false);
        
            // Create the delete label and add it to the button
            deleteLabel = new JLabel("-");
            deleteLabel.setFont(new Font("", Font.BOLD, 20));
            deleteLabel.setForeground(Color.RED);
            deleteLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            this.setLayout(new BorderLayout());
            this.add(deleteLabel, BorderLayout.EAST);
        }
    }
}        