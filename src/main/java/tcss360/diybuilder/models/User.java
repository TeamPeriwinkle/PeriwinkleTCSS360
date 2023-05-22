package tcss360.diybuilder.models;
/**
 * User Object Class.
 * @author Alex Garcia
 */
import java.io.*;

public class User implements Serializable {

    //data field
    private String userName;
    private String email;
    private String password;

    private static final long serialVersionUID = 1L;

    //constructors
    public User(){
        this.userName = "";
        this.email = "";
    }
    public User(String name, String email){
        this.userName = name;
        this.email = email;
    }

    //getters
    public String getName(){
        return userName;
    }

    public String getEmail(){
        return email;
    }

    //setters
    public void setUserName(String name){
        this.userName = name;
    }
    public void setEmail(String email){this.email = email;}

    /**
     * Serialize a User Object
     * @throws IOException
     */
    public void serialize() throws IOException {
        String filepath = "src/main/resources/protocols/user.txt";
        FileOutputStream fileOutputStream = new FileOutputStream(filepath);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this);
    }


    /**
     * Deserialize a User Object and return
     * @throws IOException
     */
    public void deserialize() throws IOException, ClassNotFoundException {
        String filepath = "src/main/resources/protocols/user.txt";
        FileInputStream fileInputStream = new FileInputStream(filepath);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        User deserializedUser = (User) objectInputStream.readObject();
        this.email = deserializedUser.email;
        this.userName = deserializedUser.userName;
    }
}
