/*
 * Team Periwinkle
 */
package tcss360.diybuilder.models;
/**
 * User Object Class.
 * @author Alex Garcia
 */
import tcss360.diybuilder.SystemControl.ProjectController;

import java.io.*;
import java.util.ArrayList;

public class User implements Serializable {

    //data field
    private String userName;
    private String email;
    private String password;
    private ArrayList<Project> userProjects;

    private static final long serialVersionUID = 1L;

    public User(){}
    public User(String name, String email, String password){
        this.userName = name;
        this.email = email;
        this.password = password;
        userProjects = ProjectController.readProjects(this);
    }

    public User(String name, String email){
        this.userName = name;
        this.email = email;
        this.password = "";
    }

    //getters
    public String getEmail(){
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //setters
    public void setUserName(String name){
        this.userName = name;
    }
    public void setEmail(String email){this.email = email;}

    public ArrayList<Project> getUserProjects() {
        return userProjects;
    }

    /**
     * used to get a specific project Object
     * @param title
     * @return
     */
    public Project getProject(String title){
        for (Project project: userProjects) {
            if(project.getName().equals(title)){
                return project;
            }
        }
        return new Project();
    }


    /**
     *
     * @return list with project titles for a user
     */
    public ArrayList<String> getProjectTitles() {
        ArrayList<String> result = new ArrayList<>(userProjects.size());
        for (Project project : userProjects) {
            result.add(project.getName());
        }

        return result;
    }
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
