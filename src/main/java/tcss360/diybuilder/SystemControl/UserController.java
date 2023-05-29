package tcss360.diybuilder.SystemControl;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.*;
import tcss360.diybuilder.models.User;

/**
 * User Controller Class.
 * @author Alex Garcia
 */
public class UserController extends Controller{
    //static protected JSONObject userData = new JSONObject();
    //static protected List<User> userRepository = new ArrayList<>();

    //contructor
    public UserController() {
    }



    /**
     * @param key
     * @return User Object
     * @author Alex Garcia
     */
    public static User getUserObject(String key)
    {
        JSONObject userData = (JSONObject) data.get("users");
       //JSONObject users = (JSONObject) data.get("users");
       JSONObject user = (JSONObject) userData.get(key);
       User temp = new User((String)user.get("username") ,  (String)user.get("email"), (String)user.get("password"));
       return temp;
    }

    /**
     *
     * @param user
     * @throws IOException
     */
    public void createUser(User user) throws IOException {
        JSONObject userData = (JSONObject) data.get("users");
        // create new user Json object
        JSONObject newUser = new JSONObject();
        newUser.put("userName", user.getUserName());
        newUser.put("email", user.getEmail());
        newUser.put("password", user.getPassword());
        newUser.put("projects", new JSONArray());

        //add new user to the existing user data
        userData.put((String)user.getUserName(), newUser);

        //update the og data
        updateData(userData);
    }

    /**
     * @param username
     * @param email
     * @param password
     * @throws IOException
     * Note: not ideal, can be changed later
     */
    public void createUser(String username, String email, String password) throws IOException {
        User u = new User(username, email, password);
        createUser(u);
    }


    /**
     * check to see if user already exists
     * @param username
     * @return boolean
     */
    public boolean userExists(String username) {
        JSONObject userData = (JSONObject) data.get("users");

        if(userData.get(username) == null){
            return false;
        }else{
            return true;
        }
    }


    /**
     * checks user credentials(username and password)
     */
    public static boolean checkCredentials(String username, String password) {
        JSONObject userData = (JSONObject) data.get("users");

        if (userData.get(username) != null) {
            JSONObject userInfo = (JSONObject) userData.get(username);
            return password.equals((String)userInfo.get("password"));
        }

        return false;
    }


    /**
     * adds in new user information to the original json data
     */
    protected void updateData(JSONObject userData) throws IOException {

        data.replace("users", userData);
        writeData();
    }


}