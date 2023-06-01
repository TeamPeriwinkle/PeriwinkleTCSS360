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

    protected static JSONObject userData;
    protected static JSONObject currentUser;

    //thinking that the tag "users" isnt needed anymore
    //private static JSONObject currentUser;

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
        JSONObject userData = (JSONObject) data.get("users");
       //JSONObject users = (JSONObject) data.get("users");
       JSONObject user = (JSONObject) userData.get(username);
       User temp = new User((String)user.get("username") ,  (String)user.get("email"), (String)user.get("password"));
       return temp;
    }


//    /**
//     * writes in a new user to the json file
//     * @param user
//     * @throws IOException
//     */
//    public void createUser(User user) throws IOException {
//        JSONObject userData = (JSONObject) data.get("users");
//        // create new user Json object
//        JSONObject newUser = new JSONObject();
//        newUser.put("userName", user.getUserName());
//        newUser.put("email", user.getEmail());
//        newUser.put("password", user.getPassword());
//        newUser.put("projects", new JSONArray());
//
//        //add new user to the existing user data
//        userData.put((String)user.getUserName(), newUser);
//
//        //update the og data
//        updateData(userData);
//    }


    /**
     * function to be used when creating a new account, will add user to permanant and volotile data
     * @param username username passed in by user
     * @param email email passed in by user
     * @param password password passed in by user
     * @throws IOException
     */
    public void createUser(String username, String email, String password) throws IOException {

        //can become redudnant, think about holding this in a
        JSONObject userData = (JSONObject) data.get("users");

        // create new user Json object
        JSONObject newUser = new JSONObject();
        newUser.put("userName", username);
        newUser.put("email",email);
        newUser.put("password", password);
        newUser.put("projects", new JSONArray());//should be empty Json Array

        //add new user to the existing user data
        userData.put(username, newUser);

        //update the og data
        updateData(userData);
    }


    /**
     * check to see if user already exists
     * @param username
     * @return boolean
     */
    public static boolean userExists(String username) {
        JSONObject userData = (JSONObject) data.get("users");

        if(userData.get(username) == null){
            return false;
        }else{
            return true;
        }
    }

    /**
     * loads in the current User account into static datafield(as a Json Object)
     */
    public void loadUserAccount(){

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
}