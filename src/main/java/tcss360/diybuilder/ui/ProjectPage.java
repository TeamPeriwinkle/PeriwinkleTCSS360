/*
 * Team Periwinkle
 */
package tcss360.diybuilder.ui;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import org.jfree.chart.ChartPanel;
import tcss360.diybuilder.SystemControl.ProjectController;
import tcss360.diybuilder.models.*;

/**
 * Project UI.
 *
 * @author Soe Lin
 * @author Mey Vo
 */
public class ProjectPage extends JFrame {
    /** Panel to hold project name and description. */
    private final JPanel projectPanel;
    /** Data to create pie chart. */
    private DefaultPieDataset dataset;
    /** Pie chart. */
    private JFreeChart chart;
    /** Buttons to hold project's functions. */
    private final JPanel buttonsPanel;
    /** Panel to hold task list. */
    private final JPanel taskListPanel;
    /** Arraylist of task to manipulate tasks. */
    //private ArrayList<Task> tasks;
    /** Project object. */
    private final Project project;
    /** User object. */
    private final User myUser;
    /** Budget object. */
    private final Budget myBudget;

    /**
     * Constructor.
     *
     * @param theP project object
     * @param theUser user object
     */
    public ProjectPage(Project theP, User theUser) {
        super("DIY Control");
        ProjectController.loadProject(theP.getName());
        project = theP;
        myUser = theUser;
        //tasks = project.getTaskList();
        myBudget = new Budget(project.getTaskList(), project.getBudget());
        projectPanel = new JPanel();
        buttonsPanel = new JPanel();
        taskListPanel = new JPanel(new GridBagLayout());
    }

    /**
     * Set up the JFrame and display.
     */
    public void display() {
        createMenuBar();
        projectPanel.setLayout(new BoxLayout(projectPanel, BoxLayout.Y_AXIS));

        // Create Dataset
        dataset = new DefaultPieDataset();
        double remBudget = project.getBudget() - ProjectController.calculateOverallTotal(myBudget);
        dataset.setValue("Remaining Budget", remBudget);
        for (Task t : project.getTaskList()) {
            dataset.setValue(t.getName(), ProjectController.calcuateTaskCost(t));
        }

        // Create chart
        chart = ChartFactory.createPieChart("Budget Pie Chart", dataset, false, true, false);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(200, 200));

        // Create description label
        JLabel projectName = new JLabel("Project Name: " + project.getName());
        JLabel projectDescription = new JLabel("Description: " + project.getDescription());
        projectPanel.add(projectName);
        projectPanel.add(Box.createVerticalStrut(10));
        projectPanel.add(projectDescription);

        // Set up Buttons Panel
        buttonsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel taskCreatePanel = new JPanel();
        taskCreatePanel.setLayout(new BoxLayout(taskCreatePanel, BoxLayout.X_AXIS));

        JLabel createTaskLabel = new JLabel("+");
        createTaskLabel.setToolTipText("Create new Task");
        createTaskLabel.setFont(new Font("Arial", Font.BOLD, 20));
        createTaskLabel.setForeground(Color.RED);
        taskCreatePanel.add(createTaskLabel);

        createTaskLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        createTaskLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JPanel taskDetailsPanel = new JPanel(new GridLayout(3, 2));

                taskDetailsPanel.add(new JLabel("Task Name:"));
                JTextField taskNameField = new JTextField();
                taskDetailsPanel.add(taskNameField);

                // Show the panel in a JOptionPane
                int result = JOptionPane.showConfirmDialog(getParent(), taskDetailsPanel, "Create a new task",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String name = taskNameField.getText();

                    // Create new task
                    Task task = new Task(name, new ArrayList<>());
                    //tasks.add(task);
                    project.addTask(task);

                    JOptionPane.showMessageDialog(getParent(), "New task created: " + name,
                            "Create Task", JOptionPane.INFORMATION_MESSAGE);

                    // Update the list of tasks
                    updateTaskList();
                    updateAddedPieChart();
                }
            }
        });

        // Add space between "+" and "Create new project" labels
        taskCreatePanel.add(Box.createHorizontalStrut(10)); // Change the number 10 to increase or decrease the space

        // Create "Create new project" label
        JLabel taskLabel = new JLabel("Create new Task");
        taskLabel.setHorizontalAlignment(SwingConstants.LEFT);
        taskCreatePanel.add(taskLabel);

        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonsPanel.add(taskCreatePanel, gbc);

        updateTaskList();
        gbc.gridx = 0;
        gbc.gridy = 1;
        buttonsPanel.add(taskListPanel, gbc);
        buttonsPanel.setPreferredSize(new Dimension(200, 200));

        // Set up layout
        setLayout(new BorderLayout());
        add(projectPanel, BorderLayout.NORTH);
        add(chartPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Update the pie chart based on the added data.
     */
    private void updateAddedPieChart() {
        // Create Dataset
        double remBudget = project.getBudget() - ProjectController.calculateOverallTotal(myBudget);
        dataset.setValue("Remaining Budget", remBudget);
        for (Task t : project.getTaskList()) {
            dataset.setValue(t.getName(), ProjectController.calcuateTaskCost(t));
        }

        chart.fireChartChanged();
    }

    /**
     * Update the pie chart based on the deleted data.
     *
     * @param name name of the task
     */
    private void updateDeletedPieChart(String name) {
        dataset.remove(name);

        chart.fireChartChanged();
    }

    /**
     * Update the task list based on the added or deleted task.
     */
    private void updateTaskList() {
        //tasks = project.getTaskList();
        taskListPanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 2, 2, 2);

        for (int i = 0; i < project.getTaskList().size(); i++) {
            String taskName = project.getTaskList().get(i).getName();

            // Mouseclick delete project
            TaskButton taskButton = new TaskButton(taskName, i);

            Image cursorDeleteImage = Toolkit.getDefaultToolkit().getImage("src/main/resources/delete-24.png");
            Image resizedDeleteImage = cursorDeleteImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);

            // Create a custom cursor using the image
            Cursor customDeleteCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                    resizedDeleteImage, new java.awt.Point(0, 0), "CustomCursor");
            taskButton.getDeleteLabel().setCursor(customDeleteCursor);
            taskButton.getDeleteLabel().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int confirm = JOptionPane.showConfirmDialog(getParent(),
                            "Are you sure you want to delete this task?",
                            "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        project.deleteTask(taskButton.getIndex());
                        updateTaskList();
                        updateDeletedPieChart(taskName);
                    }
                }
            });

            Image cursorImage = Toolkit.getDefaultToolkit().getImage("src/main/resources/cursor-60.png");
            Image resizedCursorImage = cursorImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);

            // Create a custom cursor using the image
            Cursor customCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                    resizedCursorImage, new java.awt.Point(0, 0), "CustomCursor");

            taskButton.setCursor(customCursor);

            // Add the TaskButton to the task list panel
            gbc.gridy++;
            taskListPanel.add(taskButton, gbc);
            taskButton.addActionListener();
        }

        // Refresh the task list panel
        taskListPanel.revalidate();
        taskListPanel.repaint();
    }

    /**
     * Creating menu bar for JFrame.
     */
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu settingsSection = new JMenu();

        // Add back icon image
        ImageIcon backIcon = new ImageIcon("src/main/resources/backicon.png");
        ImageIcon resizedBackIcon = new ImageIcon(backIcon.getImage().getScaledInstance(25, 25,
                Image.SCALE_SMOOTH));
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
        JMenuItem noteMenuItem = new JMenuItem("Note");
        budgetMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                BudgetPage b = new BudgetPage(project, myUser, 1, 0);
                b.display();
            }
        });
        settingsSection.add(budgetMenuItem);// Add the Budget menu item to settingsSection
        settingsSection.addSeparator();

        noteMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                // create note page
                NotesPage notepage = new NotesPage(myUser, project );
                notepage.display();
            }
        });
        settingsSection.add(noteMenuItem);

        setJMenuBar(menuBar);
    }

    /**
     * Custom TaskButton inner class.
     */
    private class TaskButton extends JButton {
        /** Integer to use and actionPerformed. */
        private final int index;
        /** Delete label. */
        private JLabel deleteLabel;

        /**
         * Constructor.
         *
         * @param name task name
         * @param theIndex index for taskbutton
         */
        public TaskButton(String name, int theIndex) {
            super(name);
            index = theIndex;
            setup();
        }

        public int getIndex() {
            return index;
        }

        /**
         * Return delete label.
         *
         * @return deleteLabel
         */
        public JLabel getDeleteLabel() {
            return deleteLabel;
        }

        /**
         * Set up for custom task button.
         */
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

        /**
         * Add action listener to the button.
         */
        public void addActionListener() {
            this.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                    TaskPage t = new TaskPage(project, myUser, index);
                    t.display();
                }
            });
        }
    }

}


