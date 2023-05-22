package tcss360.diybuilder.models;

public class Person {

    private String userName;
    private String email;

    //constructors
    public Person(String name, String email){
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
}
