/*
 * Team Periwinkle
 */
package tcss360.diybuilder.models;

import tcss360.diybuilder.SystemControl.ProjectController;

import java.awt.*;
import java.util.ArrayList;

/**
 * Project Object class.
 *
 * @author Mey Vo
 * @author Soe Lin
 */
public class Project {
    /** The name of the Project. */
    private String name;
    /** The estimated budget of the Project. */
    private double budget;
    /** The description of the Project. */
    private String description;
    /** Array list of Task Object. */
    private ArrayList<Task> taskList;


    /**
     * Constructor.
     *
     * @param name
     * @param budget
     * @param description
     */
    public Project(String name, double budget, String description, ArrayList<Task> theTaskList) {
        this.name = name;
        this.budget = budget;
        this.description = description;
        taskList = theTaskList;
    }

    public Project() {

    }

    /**
     * Return the budget of the Project.
     *
     * @return budget
     */
    public double getBudget() {
        return budget;
    }

    /**
     * Return the description of the Project.
     *
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the name of the Project.
     *
     * @param name
     */
    public void setTitle(String name) {
        this.name = name;
    }

    /**
     * Return the name of the Project.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Return the ArrayList of task of the Project.
     *
     * @return Task ArrayList
     */
    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    /**
     * Reading and initializing the task from data.
     *
     * @param username
     */
    public void initTasks(String username){
        taskList = ProjectController.readtasks(username, this.name);
    }
}
