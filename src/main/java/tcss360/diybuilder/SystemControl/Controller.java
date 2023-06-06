package tcss360.diybuilder.SystemControl;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author Alex G
 * Controller Class in charge of reading and writing (also a repository for data)
 */

public class Controller{
    static protected String DATAFILE = "df.json";
    static public JSONObject data = new JSONObject();

    public Controller(){
        readData();
    }

    /**
     * reads in json file to the json object
     * @author Alex G
     */
    protected void readData()  {

        //figure out how to load in from an already created user json file
        JSONParser jsonParser = new JSONParser();
        //InputStream is = getFileFromResourceAsStream(DATAFILE);

        File f = new File(DATAFILE);
        FileReader fr = null;
        try {
            fr = new FileReader(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8
        try (BufferedReader reader = new BufferedReader(fr)) {

            Object obj = jsonParser.parse(reader);
            data = (JSONObject) obj;
            //is.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    /**
     * writes everything in the json object to the json file(entire object)
     * Note: this is not efficient but will suffice for now
     *  @author Alex G
     */
    protected static void writeData() {

        //Path p = Paths.get("df.json");
        //Path folder = p.getParent();

        FileWriter fw = null;
        try {
            fw = new FileWriter(DATAFILE);
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write(data.toJSONString());
            writer.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * adds in new user information to the original json data
     */
    static protected void updateData(JSONObject userData) throws IOException {

        data.replace("users", userData);
        writeData();
    }
}
