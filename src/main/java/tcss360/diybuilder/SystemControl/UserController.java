package tcss360.diybuilder.SystemControl;
import java.io.*;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tcss360.diybuilder.models.User;

public class UserController {

    //initially will be loaded with users held in a json file
    //will be shared for any instance of controller
    static protected JSONObject data = new JSONObject();
    //HashMap<String, Object> userRepo= new HashMap();

    public UserController() {
        //figure out how to load in from an already created user json file
        JSONParser jsonParser = new JSONParser();





        try (FileReader reader = new FileReader("src/main/resources/df.json")) {
            //Read JSON file

            Object obj = jsonParser.parse(reader);
            data = (JSONObject) obj;

            //System.out.println(users);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void createNewUser(String name, String email){

    }
    //returns a user object from the collected data
    public User getUserObject(String key)
    {
       JSONObject users = (JSONObject) data.get("users");
       JSONObject user = (JSONObject) users.get(key);
       User temp = new User((String)user.get("username") ,  (String)user.get("email"));
       return temp;
    }
        //check to see if username is already exists
    //currently reads directly
    public boolean checkUsername(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/data.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //checks password
    public boolean checkCredentials(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/data.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}