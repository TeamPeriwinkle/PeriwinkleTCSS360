package tcss360.diybuilder.SystemControl;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tcss360.diybuilder.models.User;

/**
 * User Controller Class.
 * @author Alex Garcia
 */

public class UserController {
    //initially will be loaded with users held in a json file
    //will be shared for any instance of controller
    static String DATAFILE = "df.json";
    static protected JSONObject data = new JSONObject();

    //contructor
    public UserController() throws URISyntaxException {

        //figure out how to load in from an already created user json file
        JSONParser jsonParser = new JSONParser();
        InputStream is = getFileFromResourceAsStream(DATAFILE);

        try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            while ((line = reader.readLine()) != null) {

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        //getting the stuff as a stream for the jar file


        File file = getFileFromResource(DATAFILE);

        try (FileReader reader = new FileReader(file)) {
            //Read JSON file

            //I reall think this just seperates keys and values nothing more
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


    /**
     *
     * wont work with the jar file lmao
     * @return
     */
    //HELPER METHODS
    private File getFileFromResource(String fileName) throws URISyntaxException {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {

            // failed if files have whitespaces or special characters
            //return new File(resource.getFile());

            return new File(resource.toURI());
        }

    }


    private InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }

}