package tcss360.diybuilder.SystemControl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import tcss360.diybuilder.models.*;

import java.io.IOException;
import java.util.ArrayList;

public class ProjectController extends UserController {

    //Hoping to use this in order to reduce some repeating code
    private static JSONObject currentProject;
    private static JSONObject currentTask;
    private static JSONObject currentItem;

    //constructors
    public ProjectController() {
    }

    //METHODS

    /**
     * helper method to fill ProjectList
     */
    static public ArrayList<Project> readProjects(String username) {
        ArrayList<Project> projectsList = new ArrayList<>();



        //currently sloppy but will retrieve the "projects array for users"
        JSONArray projectData = readProjectdata(username);

        for (Object project : projectData) {
            JSONObject projTemp = (JSONObject) project; //not sure why I cant just do this directly but ok

            String name = projTemp.get("title").toString();
            double budget = Double.parseDouble(projTemp.get("budget").toString());
            String plan = projTemp.get("title").toString();
            String description = projTemp.get("title").toString();

            Project temp = new Project(name, budget, plan, description);
            projectsList.add(temp);
        }
        return projectsList;
    }

    /**
     *
     * @param userName
     * @param projectName
     * @return
     */
    static public ArrayList<Task> readtasks(String userName, String projectName) {
        ArrayList<Task> result = new ArrayList<>();

        JSONArray projectData = readProjectdata(userName);
        JSONArray projectTasks = new JSONArray();
        //find the project in jsonObject and the jsonArray for tasks
        for (Object project : projectData) {
            JSONObject temp = (JSONObject) project;
            String title = (String) temp.get("title");

            if(title.equals(projectName)){
                projectTasks = (JSONArray) temp.get("tasks");
            }
        }

        for(Object task : projectTasks){
            JSONObject taskJson = (JSONObject) task;
            String name = (String)taskJson.get("name");
            JSONArray items = (JSONArray) taskJson.get("items");
            ArrayList<Item> list = JSONArrayToItemArray(items);
            Task temp  = new Task(name,list );
            result.add(temp);
        }
        return result;
    }

    /**
     * Returns the Item array for a task
     * @param things
     * @return
     */
    protected static ArrayList<Item> JSONArrayToItemArray(JSONArray things) {

        ArrayList<Item> result = new ArrayList<>(things.size());
        for(Object item: things){
            JSONObject itemTemp = (JSONObject) item; //not sure why I cant just do this directly but ok

            String name = itemTemp.get("name").toString();
            double price = Double.parseDouble(itemTemp.get("price").toString());
            int unit = Integer.parseInt(itemTemp.get("unit").toString());

            Item temp = new Item(name, price, unit);
            result.add(temp);
        }
        return result;
    }


    /**
     *
     * @param userName
     * @return Json Array for all user projects
     */
    protected static JSONArray readProjectdata(String userName){
        JSONObject tempUserData = (JSONObject) data.get("users");
        JSONObject accountData = (JSONObject) tempUserData.get(userName);
        JSONArray projectData = (JSONArray) accountData.get("projects");



        return projectData;
    }

    /**
     * To be used when creating a new project(will handle saving the project information to th json file)
     * @param project
     */
    public void createProject(String username, Project project){
        //retrieve correct placement in JSON Object for new project
        JSONObject userData = (JSONObject) data.get("users");
        JSONObject user = (JSONObject) userData.get(username);
        JSONArray userProjects = (JSONArray) user.get("projects");

        //initialize empty JsonObject and JSONArray to fill then add
        JSONObject newProject = new JSONObject(); //commonly used
        JSONArray tempTasks = new JSONArray();

        //add project information to a Json Object
        newProject.put("title", project.getName());
        newProject.put("budget", project.getBudget());
        newProject.put("plan", project.getPlan());
        newProject.put("description", project.getPlan());
        newProject.put("tasks", tempTasks);

        //add everything back to the user data and update json file
        userProjects.add(newProject);
        user.replace("projects", userProjects);
        userData.replace(username, user);

        try {
            updateData(userData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * To be used when creating a new Task(will handle saving the project information to th json file)
     * @param currentUser current signed in user
     * @param currentProject current project that is being edited
     * @param taskName name for the new task to be added
     */
    public void createTask(User currentUser, Project currentProject, String taskName){
        //retrieve correct placement in JSON Object for new project
        //could be simplified user and project wasnt needed as a parameter(possible hold them statically)
        JSONObject usersData = (JSONObject) data.get("users");
        JSONObject user = (JSONObject) usersData.get(currentUser.getUserName());
        JSONArray userProjects = (JSONArray) user.get("projects");
        JSONObject project = null;

        //find the currentProject
        for(Object proj: userProjects ){
            JSONObject tempProj = (JSONObject) proj;
            String tempProjName = (String)tempProj.get("title");
            if(tempProjName.equals(currentProject.getName())){
                project = tempProj;
            }
        }

        //adding some information in case of a null exception might be beneficial
        JSONArray projectTasks = (JSONArray) project.get("projects");

        //initialize empty JsonObject and JSONArray to fill then add
        JSONObject newTask = new JSONObject();
        JSONArray tempItemArray = new JSONArray();

        //add task information to a Json Object
        newTask.put("name", taskName);
        newTask.put("items", tempItemArray);

        //add everything back to project data and then user data
        projectTasks.add(newTask);
        project.replace("tasks", projectTasks);

        for (int i = 0; i < userProjects.size() ; i++) {
            JSONObject proj = (JSONObject) userProjects.get(i);
            String tempProjName = (String) proj.get("title");
            if(tempProjName.equals(currentProject.getName())){
                userProjects.set(i, project);
            }
        }

        user.replace("projects",userProjects);
        usersData.replace(currentUser.getUserName(), user);

        try {
            updateData(usersData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ///* DANGER*/before implementing createItem implement change to reduce some of the redudancy(static JSON Objects)
    //If this isnt done this method will be 50+ lines long for no real reason
    public void createItem(){

    }



    /**
     * loads in the current project into static datafield
     * @param title title of the project user has selected
     */
    public void loadProject(String title){
        JSONArray userProjects = (JSONArray) currentUser.get("projects");

        for(Object proj: userProjects){
            JSONObject tempProj = (JSONObject)proj;
            String projTitle = (String)tempProj.get("title");

            if(projTitle.equals(title)){
                currentProject = tempProj;
            }
        }

        //for deubugging purposes
        if(currentProject.isEmpty()){
            System.out.println("Item was not loaded!!");
        }
    }

    /**
     * loads in the current project into static datafield
     */
    public void loadTask(String TaskName){
        JSONArray projectTasks = (JSONArray) currentProject.get("tasks");

        for(Object task: projectTasks){
            JSONObject tempTask = (JSONObject)task;
            String tempName = (String)tempTask.get("name");

            if(tempName.equals(TaskName)){
                currentTask = tempTask;
            }
        }

        //for deubugging purposes
        if(currentTask.isEmpty()){
            System.out.println("Task was not loaded!!");
        }
    }

    /**
     * loads in the current Item into static datafield
     * assumes task is already loaded in
     */
    public void loadItem(String itemName){
        JSONArray taskItems = (JSONArray) currentTask.get("items");

        for(Object item: taskItems){
            JSONObject tempItem = (JSONObject)item;
            String tempName = (String)tempItem.get("name");

            if(tempName.equals(itemName)){
                currentItem = tempItem;
            }
        }

        //for deubugging purposes
        if(currentItem.isEmpty()){
            System.out.println("Item was not loaded!!");
        }
    }



    /**
     * @author Soe
     * @param theTask
     * @return
     */
    public static double calcuateTaskCost(Task theTask) {
        double result = 0;
        ArrayList<Item> itemsList = theTask.getItemsList();
        for (int j = 0; j < itemsList.size(); j++) {
            result += itemsList.get(j).getTotalCost();
        }
        return result;
    }



}
