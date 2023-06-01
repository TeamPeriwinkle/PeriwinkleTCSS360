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
import tcss360.diybuilder.SystemControl.BudgetController;
import tcss360.diybuilder.SystemControl.TaskController;
import tcss360.diybuilder.models.*;

public class ProjectPage2 extends JFrame {
    private JLabel projectName;
    private JPanel projectPanel;
    private DefaultPieDataset dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private JPanel buttonsPanel;
    private JPanel taskListPanel;
    private ArrayList<Task> tasks;
    private Project project;
    private User myUser;
    private Budget myBudget;
    private JMenuBar menuBar;
    private JMenu settingsSection;

    public ProjectPage2(Project theP, User theUser) {
        super("DIY Control");
        project = theP;
        myUser = theUser;
        tasks = project.getTaskList();
        myBudget = new Budget(project.getTaskList(), project.getBudget());
        projectPanel = new JPanel();
        buttonsPanel = new JPanel();
        taskListPanel = new JPanel(new GridBagLayout());
    }

    public void display() {
        createMenuBar();
        projectPanel.setLayout(new BoxLayout(projectPanel, BoxLayout.Y_AXIS));

        // Create Dataset
        dataset = new DefaultPieDataset();
        double remBudget = project.getBudget() - BudgetController.calculateOverallTotal(myBudget);
        dataset.setValue("Remaining Budget", remBudget);
        for (Task t : tasks) {
            dataset.setValue(t.getName(), TaskController.calcuateTaskCost(t));
        }

        // Create chart
        chart = ChartFactory.createPieChart("Budget Pie Chart", dataset, false, true, false);
        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(300, 300));

        // Create description label
        projectName = new JLabel("Project Name: " + project.getName());
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
                int result = JOptionPane.showConfirmDialog(getParent(), taskDetailsPanel, "Create a new task", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    String name = taskNameField.getText();

                    // Create new task
                    Task task = new Task(name, new ArrayList<Item>());
                    tasks.add(task);

                    JOptionPane.showMessageDialog(getParent(), "New task created: " + "newTask.getName()", "Create Task", JOptionPane.INFORMATION_MESSAGE);

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

    private void updateAddedPieChart() {
        // Create Dataset
        double remBudget = project.getBudget() - BudgetController.calculateOverallTotal(myBudget);
        dataset.setValue("Remaining Budget", remBudget);
        for (Task t : tasks) {
            dataset.setValue(t.getName(), TaskController.calcuateTaskCost(t));
        }

        chart.fireChartChanged();
    }

    private void updateDeletedPieChart(String name) {
        dataset.remove(name);

        chart.fireChartChanged();
    }

    private void updateTaskList() {
        taskListPanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        for (int i = 0; i < tasks.size(); i++) {
            String taskName = tasks.get(i).getName();

            // Mouseclick delete project
            TaskButton taskButton = new TaskButton(taskName, i);

            Image cursorDeleteImage = Toolkit.getDefaultToolkit().getImage("src/main/resources/delete-24.png");
            Image resizedDeleteImage = cursorDeleteImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);

            // Create a custom cursor using the image
            Cursor customDeleteCursor = Toolkit.getDefaultToolkit().createCustomCursor(
                    resizedDeleteImage, new java.awt.Point(0, 0), "CustomCursor");
            taskButton.getDeleteLabelLabel().setCursor(customDeleteCursor);
            taskButton.getDeleteLabelLabel().addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int confirm = JOptionPane.showConfirmDialog(getParent(), "Are you sure you want to delete this task?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        boolean deleted = deleteTask(taskName);
                        if (deleted) {
                            JOptionPane.showMessageDialog(getParent(), "Task deleted: " + taskName, "Delete Task", JOptionPane.INFORMATION_MESSAGE);
                            updateTaskList();
                            updateDeletedPieChart(taskName);
                        } else {
                            JOptionPane.showMessageDialog(getParent(), "Failed to delete the task", "Delete Task", JOptionPane.WARNING_MESSAGE);
                        }
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
            taskButton.addActionListener(null);
        }

        // Refresh the task list panel
        taskListPanel.revalidate();
        taskListPanel.repaint();
    }

    private boolean deleteTask(String taskName) {
        for (Task task : tasks) {
            if (task.getName().equals(taskName)) {
                tasks.remove(task);
                return true;
            }
        }
        return false;
    }

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


