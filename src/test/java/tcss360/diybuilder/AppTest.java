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
        //Serialize the User
        User user1 = new User("LoganDeezDukes123", "migratious@gmail.com");
        user1.serialize();

        //Deserialize the User
        User desiralizedUser = new User();
        desiralizedUser.deserialize();

        assertEquals("LoganDeezDukes123", desiralizedUser.getUserName());
        assertEquals("migratious@gmail.com", desiralizedUser.getEmail());
    }

    @Test
    public void loginUser(){
        Controller genController = new Controller();

        String username = "alexg123";
        String password = "123";

        UserController userController = new UserController();
        assertTrue(userController.checkCredentials(username, password));

        User testUser = userController.getUserObject(username);

        Project testProj = testUser.getUserProjects().get(0);
        testProj.initTasks(testUser.getUserName());

        Task testTask = testProj.getTaskList().get(0);

        System.out.println(testTask.getName());
    }

    /**
     * Example of how it is possible to get information using models
     */
    @Test
    public void loadItem(){
        Controller c = new Controller();
        String username = "alexg123";
        String password = "123";

        User alex = new User(username);

        String projectTitle = "bathroom";
        Project firstProj = alex.getProject(projectTitle);
        firstProj.initTasks(username);

        firstProj.getTaskList();
        String taskName = "replace tiles";

        Task firstTask = firstProj.getTask(taskName);

        String itemName = "white tile";

        Item firstItem  = firstTask.getItem(itemName);

        assertEquals(itemName, firstItem.getName() );


    }

}
