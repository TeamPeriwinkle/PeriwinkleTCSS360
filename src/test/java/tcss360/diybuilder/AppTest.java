package tcss360.diybuilder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import tcss360.diybuilder.SystemControl.UserController;
import tcss360.diybuilder.models.User;

import java.io.IOException;

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
     */
    @Test
    public void readinusers(){
        UserController controller = new UserController();
        //System.out.println(controller.data.get("users"));
    }

    /**
     * read data from json file and generate a User object
     */
    @Test
    public void createUser(){
        UserController controller = new UserController();
        User me = controller.getUserObject("alexg123");
        String expectedName = "alexg123";
        String expectedEmail ="alex123@gmail.com";
        String actualName = me.getName();
        String actualEmail = me.getEmail();

        assertTrue(expectedName.equals(actualName));
        assertTrue(expectedEmail.equals(actualEmail));

    }

    /**
     * Export and import a user Object for Iteration 2(serialize and deserialize)
     */
    @Test
    public void iteration2Test() throws IOException, ClassNotFoundException {
        //Serialize the User
        User user1 = new User("LoganDeezDukes123", "migratious@gmail.com");
        user1.serialize();

        //Deserialize the User
        User desiralizedUser = new User();
        desiralizedUser.deserialize();

        assertEquals("LoganDeezDukes123", desiralizedUser.getName());
        assertEquals("migratious@gmail.com", desiralizedUser.getEmail());
    }

}
