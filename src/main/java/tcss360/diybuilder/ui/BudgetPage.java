package tcss360.diybuilder.ui;

/*
 *
 * @author Soe Lin
 */

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tcss360.diybuilder.*;
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
        tasksTable.setCellSelectionEnabled(false);
        JScrollPane tasksScrollPane = new JScrollPane(tasksTable);

        // Panel2 setup
        panel2.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20));
        panel2.setPreferredSize(new Dimension(800, 100));
        panel2.add(tasksScrollPane);
        categoryScrollPane.setFocusable(false);
        panel2.add(categoryScrollPane);

        // BackButton Action Listener
        backButton.addActionListener(e -> {
            dispose();
            //create project page
            JOptionPane.showMessageDialog(getParent(), "Mouse clicked");
        });

        // Add the panel to the frame
        this.setLayout(new BorderLayout());
        this.add(panel1, BorderLayout.NORTH);
        this.add(panel2, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

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
        myTaskList.add(task1);
        myTaskList.add(task2);
        myTaskList.add(task3);


        /*
        try {
            String jsonString = new String(Files.readAllBytes(Paths.get("src/Resources/Data.json")));
            JSONParser parser = new JSONParser();
            Object object = parser.parse(jsonString);
            JSONObject jsonObject = (JSONObject) object;

            String jsonString2 = new String(Files.readAllBytes(Paths.get("src/Resources/Items.json")));
            JSONParser parser2 = new JSONParser();
            Object object2 = parser2.parse(jsonString2);
            JSONObject itemsJson = (JSONObject) object2;

            List<String> projectString = new ArrayList<>();

            // Iterate over the keys in the JSONObject
            for (Object key : jsonObject.keySet()) {
                projectString.add((String) key);
            }

            //System.out.println(projectString);

            for (String projectName : projectString) {
                JSONArray tasksArray = (JSONArray) jsonObject.get(projectName);
                ArrayList<Task> tasksList = new ArrayList<>();
                //System.out.println(tasksArray);
                for (Object task : tasksArray) {
                    JSONObject taskObject = (JSONObject) task;
                    String taskName = (String) taskObject.get("name");
                    JSONArray itemsArray = (JSONArray) itemsJson.get(taskName);
                    if (itemsArray != null) {
                        ArrayList<Item> itemsList = new ArrayList<Item>();
                        for (Object item : itemsArray) {
                            JSONObject itemObject = (JSONObject) item;
                            String itemName = (String) itemObject.get("name");
                            double pricePerUnit = (double) itemObject.get("price_per_unit");
                            Long totalUnitL = (Long) itemObject.get("total_unit");
                            int totalUnit = Math.toIntExact(totalUnitL);
                            itemsList.add(new Item(itemName, pricePerUnit, totalUnit));
                        }
                        tasksList.add(new Task(taskName, itemsList));
                    }
                }
                Budget budget1 = new Budget(tasksList, 300);
                BudgetPage bg = new BudgetPage(projectName, budget1);
                bg.display();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        */

        Budget budget1 = new Budget(myTaskList, 9000);
        BudgetPage bg = new BudgetPage("ToBuy", budget1);
        bg.display();
    }

    private void createMenuBar() {
        menuBar = new JMenuBar();
        settingsSection = new JMenu();

        // add backicon image
        ImageIcon backIcon = new ImageIcon("backicon.png");
        Image resizedBackIcon = backIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon resizedBackIconImage = new ImageIcon(resizedBackIcon);




        // add taskicon image
        ImageIcon taskIcon = new ImageIcon("taskicon.png");
        Image resizedTaskIcon = taskIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedTaskIcon);

        menuBar.add(Box.createRigidArea(new Dimension(450, 0)));
        settingsSection.setIcon(resizedBackIconImage);
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

