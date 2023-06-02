package tcss360.diybuilder.SystemControl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tcss360.diybuilder.models.Item;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * @author Alex G
 * Controller Class in charge of reading and writing (also a repository for data)
 */

public class Controller <T>{
    static protected String DATAFILE = "df.json";
    static protected String DATAFILE2 = "rsrc:df.json";
    static protected JSONObject data = new JSONObject();

    public Controller(){
        readData();
    }

    /**
     * reads in json file to the json object
     * @author Alex G
     */
    public void readData()  {

        //figure out how to load in from an already created user json file
        JSONParser jsonParser = new JSONParser();
        InputStream is = getFileFromResourceAsStream(DATAFILE);

        try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            Object obj = jsonParser.parse(reader);
            data = (JSONObject) obj;
            is.close();
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
    public static void writeData() throws IOException {


        FileWriter fw = new FileWriter( DATAFILE2);
        BufferedWriter writer = new BufferedWriter(fw);
        writer.write(data.toJSONString());
        writer.close();
        fw.close();
    }


    /**
     * @author Alex Garcia
     * @param fileName
     * @return
     */
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
