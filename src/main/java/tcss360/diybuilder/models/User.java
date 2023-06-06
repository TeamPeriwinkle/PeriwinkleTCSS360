/*
 * Team Periwinkle
 */
package tcss360.diybuilder.models;
/**
 * User Object Class.
 * @author Alex Garcia
 */
import tcss360.diybuilder.SystemControl.ProjectController;
import tcss360.diybuilder.SystemControl.UserController;

import java.io.*;
import java.util.ArrayList;

public class User implements Serializable {

    //data field
    private String userName;
    private String email;
    private String password;
    private ArrayList<Project> userProjects;

    private static final long serialVersionUID = 1L;

    public User(String name, String email, String password){
        UserController.loadUserAccount(name);
        this.userName = name;
        this.email = email;
        this.password = password;
        this.userProjects = ProjectController.readProjects(this.userName);
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


    //setters
    public void setUserName(String name){
        this.userName = name;
    }

    public void setEmail(String email){this.email = email;}

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Project> getUserProjects() {
        return userProjects;
    }


    //bunch of helper methods

    //methods to add
    //addProject()
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
     * check to see if there is a user with the same username already
     * @return
     */
    public boolean alreadyExists(){
        return UserController.userExists(this.userName);
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
