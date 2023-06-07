package tcss360.diybuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static tcss360.diybuilder.SystemControl.UserController.*;

import org.junit.Test;
import tcss360.diybuilder.SystemControl.Controller;
import tcss360.diybuilder.SystemControl.*;
import tcss360.diybuilder.models.*;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    /**
     * read data from json file
     *  @author Alex Garcia
     */
    @Test
    public void readinusers() throws URISyntaxException {
        UserController controller = new UserController();
        //System.out.println(controller.data.get("users"));
    }

    /**
     * read data from json file and generate a User object
     * @Author Alex Garcia
     */
    @Test
    public void createUser() throws URISyntaxException {
        UserController controller = new UserController();
        User me = controller.getUserObject("alexg123");
        String expectedName = "alexg123";
        String expectedEmail ="alex123@gmail.com";
        String actualName = me.getUserName();
        String actualEmail = me.getEmail();

        assertTrue(expectedName.equals(actualName));
        assertTrue(expectedEmail.equals(actualEmail));

    }

    /**
     * Export and import a user Object for Iteration 2(serialize and deserialize)
     * @author Alex Garcia
     */
    @Test
    public void iteration2Test() throws IOException, ClassNotFoundException {

    }

    @Test
    public void loginUser(){
        Controller genController = new Controller();

        String username = "a6";
        String password = "a6";

        UserController userController = new UserController();
        UserController.loadUserAccount(username);
        assertTrue(userController.checkCredentials(username, password));

        User testUser = userController.getUserObject(username);

    }


    /**
     * test to see if new info is added to permanent data
     * WORKS!!!:) somehow got it to run without error on the first atttempt
     */
    @Test
    public void createProjectWithItems(){
        Controller c = new Controller();
        String username = "alexg123";
        String password = "123";
        loadUserData();

        UserController.loadUserAccount(username);

        String projectName = "gaming pc";
        String description = "new pc build for gaming and school";
        Double budget = 1200.0;

        Project newProj = new Project(projectName, budget,description);

        ProjectController.createProject(username, newProj);

        ProjectController.loadProject(projectName);

        ProjectController.createTask("Gather Parts");
        ProjectController.loadTask("Gather Parts");

        ProjectController.createItem("CPU", 120.0, 1);

        ProjectController.loadItem("CPU");
    }

    /**
     * try to delete the items, and tasks added in the previous test
     * WORKS!!!:) somehow got it to run without error on the first atttempt
     */
    @Test
    public void deleteItem(){
        Controller c = new Controller();//can have this be done for all tests but for now it will be a part of every test
        UserController.loadUserData();

        String username = "alexg123";

        UserController.loadUserAccount(username);


        //items and projects to be deleted for testing
        String projectName = "gaming pc";
        String taskName = "Gather Parts";
        String itemName = "CPU";

        ProjectController.loadProject(projectName);

        ProjectController.loadTask(taskName);

        //should delete the item
        ProjectController.deleteItem(itemName);
    }

    @Test
    public void deleteTask(){
        Controller c = new Controller();//can have this be done for all tests but for now it will be a part of every test
        UserController.loadUserData();

        String username = "alexg123";

        UserController.loadUserAccount(username);

        //items and projects to be deleted for testing
        String projectName = "gaming pc";
        String taskName = "Gather Parts";

        ProjectController.loadProject(projectName);

        //should delete the item
        ProjectController.deleteTask(taskName);
    }

    @Test
    public void deleteProject(){
        Controller c = new Controller();//can have this be done for all tests but for now it will be a part of every test
        UserController.loadUserData();

        String username = "alexg123";

        UserController.loadUserAccount(username);

        //items and projects to be deleted for testing
        String projectName = "gaming pc";

        //should delete the item
        ProjectController.deleteProject(projectName);
    }
}
