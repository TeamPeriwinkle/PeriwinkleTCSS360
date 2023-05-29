package tcss360.diybuilder.ui;

/*
 *
 * @author Soe Lin
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import tcss360.diybuilder.SystemControl.BudgetController;
import tcss360.diybuilder.SystemControl.TaskController;
import tcss360.diybuilder.models.Budget;
import tcss360.diybuilder.models.Item;
import tcss360.diybuilder.models.Task;

public class BudgetPage extends JFrame {

    private static final String[] ITEMS_COLUMNS = { "Items", "Cost per Unit", "Total Unit", "Total Cost" };
    private static final String[] TASKS_COLUMNS = { "Tasks", "Amount" };
    private static final String[] CATEGORY_COLUMNS = { "Category", "Amount" };
    private final int rows;
    private JPanel panel1;
    private JPanel panel2;
    private JButton backButton;
    private JPanel buttonPanel;
    private Budget myBudget;
    private JMenuBar menuBar;
    private JMenu settingsSection;

    public BudgetPage(String theProjectName, Budget theBudget) //also need parameter for project to go back to project page
    {
        super(theProjectName + "'s Budget");
        myBudget = theBudget;
        if (myBudget.getTasksList().size() == 0) {
            rows = 1;
        } else {
            rows = myBudget.getTasksList().size();
        }
        createMenuBar();
    }

    public void display()
    {
        setup();

        // Button setting
        backButton.setPreferredSize(new Dimension(80, 30)); // Set preferred size
        backButton.setFont(backButton.getFont().deriveFont(Font.BOLD, 12)); // Set font size
        backButton.setMargin(new Insets(5, 10, 5, 10)); // Set padding
        buttonPanel.add(backButton);

        // Category amount
        double overallTotal = BudgetController.calculateOverallTotal(myBudget);
        double estimatedBudget = myBudget.getEstimatedBudget();
        double remaingBudget = estimatedBudget - overallTotal;

        // Category Table
        Object[][] categoryData = {
                {"Overall Total", String.format("$%.2f", overallTotal)},
                {"Estimated Budget", String.format("$%.2f", estimatedBudget)},
                {"Remaining Budget", String.format("$%.2f", remaingBudget)}
        };
        JTable categoryTable = new JTable(categoryData, CATEGORY_COLUMNS) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells read-only
            }
        };
        categoryTable.setFocusable(false);
        categoryTable.setCellSelectionEnabled(false);
        JScrollPane categoryScrollPane = new JScrollPane(categoryTable);

        // Panel1 setup
        panel1.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel1.setPreferredSize(new Dimension(800, 400));

        // Task data and Items Table setup
        List<Object[]> tasksData = new ArrayList<>();
        ArrayList<Task> tasksList = myBudget.getTasksList();

        if (tasksList.size() != 0 ) {

            for (Task task : tasksList) {
                // Tasks Data
                String[] taskString = {task.getName(), String.format("$%.2f", TaskController.calcuateTaskCost(task))};
                Object[] taskRow = {taskString[0], taskString[1]};
                tasksData.add(taskRow);

                // Items Data
                List<Object[]> itemsData = new ArrayList<>();
                ArrayList<Item> itemsList = task.getItemsList();
                for (Item item : itemsList) {
                    String[] itemString = {item.getName(), String.format("$%.2f", item.getPrice()),
                            String.valueOf(item.getUnit()), String.format("$%.2f",item.getTotalCost())};
                    Object[] itemRow = {itemString[0], itemString[1], itemString[2], itemString[3]};
                    itemsData.add(itemRow);
                }

                // Items Table
                JTable itemsTable = new JTable(itemsData.toArray(new Object[0][0]), ITEMS_COLUMNS) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false; // Make all cells read-only
                    }
                };
                itemsTable.setCellSelectionEnabled(false);
                itemsTable.setFocusable(false);
                JScrollPane itemsScrollPane = new JScrollPane(itemsTable);

                // Creating Items Table Label
                JLabel taskLabel = new JLabel(task.getName());
                taskLabel.setFont(taskLabel.getFont().deriveFont(Font.BOLD, 14));
                taskLabel.setHorizontalAlignment(JLabel.CENTER);

                // Adding to Panel1
                panel1.add(taskLabel);
                panel1.add(itemsScrollPane);
            }
        } else {
            JLabel taskLabel = new JLabel("Empty Tasks");
            taskLabel.setFont(taskLabel.getFont().deriveFont(Font.BOLD, 14));
            taskLabel.setHorizontalAlignment(JLabel.CENTER);

            panel1.add(taskLabel);
        }

        // Task Table
        JTable tasksTable = new JTable(tasksData.toArray(new Object[0][0]), TASKS_COLUMNS) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells read-only
            }
        };
        tasksTable.setFocusable(false);
        tasksTable.setCellSelectionEnabled(false);
        JScrollPane tasksScrollPane = new JScrollPane(tasksTable);

        // Panel2 setup
        panel2.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panel2.setPreferredSize(new Dimension(800, 100));
        panel2.add(tasksScrollPane);
        panel2.add(categoryScrollPane);

        // BackButton Action Listener
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                //create project page
                JOptionPane.showMessageDialog(getParent(), "Mouse clicked");
            }


        });

        // Add the panel to the frame
        this.setLayout(new BorderLayout());
        this.add(panel1, BorderLayout.NORTH);
        this.add(panel2, BorderLayout.CENTER);
        //this.add(buttonPanel, BorderLayout.SOUTH);

        // Set frame properties
        this.setSize(new Dimension(900, 700));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // Warning Message for Remaining Budget
        if (remaingBudget < 0)
        {
            JOptionPane.showMessageDialog(getParent(),
                    "You have negative remaining Budget!\n" +
                            "Consider to edit the estimated budget or Reduce the items.",
                    "DIYControl", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void setup()
    {
        panel1 = new JPanel(new GridLayout(rows, 1));
        panel2 = new JPanel(new GridLayout(2, 1));
        backButton = new JButton("Back");
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    }

    public static void main(String[] args)
    {

        ArrayList<Item> myItemList = new ArrayList<>();
        myItemList.add(new Item("Rice", 38.50, 3));
        myItemList.add(new Item("IPhone12", 969.55, 2));
        myItemList.add(new Item("Sticker", 3.50, 3));
        Task task1 = new Task("Buy List", myItemList);
        Task task2 = new Task("Buy List2", myItemList);
        Task task3 = new Task("Buy List3", myItemList);
        ArrayList<Task> myTaskList = new ArrayList<>();
        //myTaskList.add(task1);
        //myTaskList.add(task2);
        //myTaskList.add(task3);

        Budget budget1 = new Budget(myTaskList, 9000);
        BudgetPage bg = new BudgetPage("ToBuy", budget1);
        bg.display();
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();
        settingsSection = new JMenu();

        // add taskicon image
        ImageIcon backIcon = new ImageIcon("src/main/resources/backicon.png");
        ImageIcon resizedBackIcon = new ImageIcon(backIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH));
        JButton backIconButton = new JButton(resizedBackIcon);
        backIconButton.setFocusable(false);
        backIconButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                //create project page
                JOptionPane.showMessageDialog(getParent(), "Mouse clicked");
            }


        });

        CustomMenuIcon menuIcon = new CustomMenuIcon();

        menuBar.add(Box.createRigidArea(new Dimension(450, 0)));
        //settingsSection.setIcon(resizedIcon);
        settingsSection.setIcon(menuIcon);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(backIconButton);
        menuBar.add(settingsSection);


        // Create "Sign Out" menu item
        JMenuItem noteSettingMenu = new JMenuItem("Note");
        settingsSection.add(noteSettingMenu);

        noteSettingMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }


        });

        //settingsSection.addSeparator();
        setJMenuBar(menuBar);


    }

    /*
     * Three horizontal stripes menu icon class.
     */
    private static class CustomMenuIcon implements Icon {
        private static final int ICON_WIDTH = 20;
        private static final int ICON_HEIGHT = 3;
        private static final Color ICON_COLOR = Color.BLACK;

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(ICON_COLOR);
            g2d.fillRect(x, y, ICON_WIDTH, ICON_HEIGHT);
            g2d.fillRect(x, y + 7, ICON_WIDTH, ICON_HEIGHT);
            g2d.fillRect(x, y + 14, ICON_WIDTH, ICON_HEIGHT);
            g2d.dispose();
        }

        @Override
        public int getIconWidth() {
            return ICON_WIDTH;
        }

        @Override
        public int getIconHeight() {
            return ICON_HEIGHT * 3 + 8;
        }
    }

}

