package tcss360.diybuilder.SystemControl;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.*;
import tcss360.diybuilder.models.*;

/**
 * User Controller Class.
 * @author Alex Garcia
 */
public class UserController extends Controller{

    //can load this in to remove some redudancy in code
    //private static UserController singleInstance = null;

    //datafield
    protected static JSONObject userData;
    protected static JSONObject currentUser;

    //contructor
    public UserController() {
    }



    /**
     * Goes into the volotile data(JSONOBJECTS) and returns a User Object
     * @param username the username user passes in when performing an action such as logging in
     * @return User Object
     * @author Alex Garcia
     */
    public static User getUserObject(String username)
    {
        //make sure things are properly loaded in
        if(userData.isEmpty()){
            loadUserData();
        }

       //JSONObject users = (JSONObject) data.get("users");
       JSONObject user = (JSONObject) userData.get(username);
       User temp = new User((String)user.get("username") ,  (String)user.get("email"), (String)user.get("password"));
       return temp;
    }


    /**
     * function to be used when creating a new account, will add user to permanant and volotile data
     * @param username username passed in by user
     * @param email email passed in by user
     * @param password password passed in by user
     * @throws IOException
     */
    public void createUser(String username, String email, String password) throws IOException {

        //make sure things are properly loaded in
        if(userData.isEmpty()){
            loadUserData();
        }

        // create new user Json object
        JSONObject newUser = new JSONObject();
        newUser.put("userName", username);
        newUser.put("email",email);
        newUser.put("password", password);
        newUser.put("projects", new JSONArray());//should be empty Json Array to start

        //add the new user to the existing user data
        userData.put(username, newUser);

        //update the overall data
        updateData(userData);
    }


    /**
     * check to see if a given username already exists
     * @param username name of user
     * @return boolean
     */
    public static boolean userExists(String username) {
        if(userData.isEmpty()){
            loadUserData();
        }

        if(userData.get(username) == null){
            return false;
        }else{
            return true;
        }
    }

    /**
     * loads in the current User account into static datafield(as a Json Object)
     */
    public static void loadUserAccount(String username){
        //make sure everything is loading in correctly
        if(userData.isEmpty()){
            loadUserData();
        }

        //place the signed-in users information into the static field
        currentUser = (JSONObject) userData.get(username);
    }

    /**
     * retrieves all user data, currenlty can be removed if time allows
     */
    public static void loadUserData(){
        userData = (JSONObject) data.get("users");
    }


    /**
     * checks user credentials(username and password)
     */
    public static boolean checkCredentials(String username, String password) {
        //make sure things are loaded in properly, shouldnt be a problem however
        if(userData.isEmpty()){
            loadUserData();
        }

        if (userData.get(username) != null) {
            JSONObject userInfo = (JSONObject) userData.get(username);
            return password.equals((String)userInfo.get("password"));
        }

        return false;
    }
}