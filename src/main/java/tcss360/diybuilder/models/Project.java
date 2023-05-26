package tcss360.diybuilder.models;

import java.awt.*;

public class Project {
    private String name;
    private double budget;
    private String plan;
    private String description;
    
    

    public Project(String name, double budget, String plan, String description) {
        this.name = name;
        this.budget = budget;
        this.plan = plan;
        this.description = description;
    }

    public Project() {
    }

    public String getTitle() {
        return name;
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

}
