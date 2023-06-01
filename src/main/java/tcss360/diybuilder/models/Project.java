package tcss360.diybuilder.models;

import tcss360.diybuilder.SystemControl.ProjectController;

import java.awt.*;
import java.util.ArrayList;

public class Project {
    private String name;
    private double budget;
    private String plan;
    private String description;
    private ArrayList<Task> taskList;
    

    public Project(String name, double budget, String plan, String description) {
        this.name = name;
        this.budget = budget;
        this.plan = plan;
        this.description = description;
    }

    public Project() {
    }

    public double getBudget() {
        return budget;
    }

    public String getPlan() {
        return plan;
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

    /**
     * used to get a specific project Object
     * @param
     * @return
     */
    public Task getTask(String taskName){
        for (Task task: taskList) {
            if(task.getName().equals(taskName)){
                return task;
            }
        }

        //change this later
        return new Task("","","");
    }
}
