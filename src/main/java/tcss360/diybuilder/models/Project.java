package tcss360.diybuilder.models;

import tcss360.diybuilder.SystemControl.ProjectController;

import java.awt.*;
import java.util.ArrayList;

public class Project {
    private String name;
    private double budget;
    private String description;
    private ArrayList<Task> taskList;
    

    public Project(String name, double budget, String description) {
        this.name = name;
        this.budget = budget;
        this.description = description;
    }

    public Project() {
    }

    public double getBudget() {
        return budget;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    public void initTasks(String username){
        taskList = ProjectController.readtasks(username, this.name);
    }
}
