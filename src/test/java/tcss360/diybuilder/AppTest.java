package tcss360.diybuilder;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import tcss360.diybuilder.SystemControl.UserController;
import tcss360.diybuilder.models.Person;

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
        Person me = controller.getUserObject("alexg123");
        String expectedName = "alexg123";
        String expectedEmail ="alex123@gmail.com";
        String actualName = me.getName();
        String actualEmail = me.getEmail();

        assertTrue(expectedName.equals(actualName));
        assertTrue(expectedEmail.equals(actualEmail));

    }
}
