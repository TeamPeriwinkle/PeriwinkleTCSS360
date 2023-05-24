package tcss360.diybuilder.SystemControl;

import tcss360.diybuilder.models.Budget;
import tcss360.diybuilder.models.Task;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BudgetController {
    public static List<Object[]> readCSVFile(String filePath) {
        List<Object[]> data = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                Object[] row = { values[0], values[1], values[2], values[3] };
                data.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    public static double calculateOverallTotal(Budget theBudget) {
        double result = 0;
        ArrayList<Task> taskList = theBudget.getTasksList();
        for (int i = 0; i < taskList.size(); i++) {
            result += TaskController.calcuateTaskCost(taskList.get(i));
        }

        return result;
    }



}

