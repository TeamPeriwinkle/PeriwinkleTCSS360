package tcss360.diybuilder.SystemControl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import tcss360.diybuilder.models.*;

import java.util.ArrayList;

public class ProjectController extends Controller {

    //constructors
    public ProjectController(User user) {
    }

    //METHODS

    /**
     * helper method to fill ProjectList
     */
    static public ArrayList<Project> readProjects(User user) {
        ArrayList<Project> projectsList = new ArrayList<>();

        //currently sloppy but will retrieve the "projects array for users"
        JSONArray projectData = readProjectdata(user.getUserName());

        for (Object project : projectData) {
            JSONObject projTemp = (JSONObject) project; //not sure why I cant just do this directly but ok

            String name = projTemp.get("title").toString();
            double budget = Double.parseDouble(projTemp.get("budget").toString());
            String plan = projTemp.get("title").toString();
            String description = projTemp.get("description").toString();

            ArrayList<Task> tasks = readtasks(user.getUserName(), name);
            Project temp = new Project(name, budget, description, tasks);
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
