package tcss360.diybuilder.models;

import java.util.ArrayList;

public class Budget {

    private ArrayList<Task> tasksList;
    private double estimatedBudget;

    public Budget(ArrayList<Task> theTasksList, double theEstimatedBudget) {
        tasksList = new ArrayList<>();
        tasksList.addAll(theTasksList);
        estimatedBudget = theEstimatedBudget;
    }

    public ArrayList<Task> getTasksList() {
        return new ArrayList<>(tasksList);
    }

    public double getEstimatedBudget() {
        return estimatedBudget;
    }
}

