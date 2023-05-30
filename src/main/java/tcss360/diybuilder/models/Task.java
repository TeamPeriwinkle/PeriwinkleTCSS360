package tcss360.diybuilder.models;

import java.util.ArrayList;

public class Task {

    private String name;
    private ArrayList<Item> itemsList;

    public Task(String theName, ArrayList<Item> theItemsList) {
        name = theName;
        itemsList = new ArrayList<Item>();
        for (int i = 0; i < theItemsList.size(); i++) {
            itemsList.add(theItemsList.get(i));
        }
    }


    public Task(String taskName, String pn, String startDate) {
    }


    public String getName() {
        return name;
    }

    public void setname(String theName) {
        name = theName;
    }

    public ArrayList<Item> getItemsList() {
        return itemsList;
    }

    public void addItem(Item theItem) {
        itemsList.add(theItem);
    }

    public void removeItem(int index) {
        itemsList.remove(index);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Task name: " + name);
        sb.append("\n");
        for (int i = 0; i < itemsList.size(); i++) {
            sb.append(itemsList.get(i).toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public String getPN() {
        return null;
    }

    public String getStartDate() {
        return null;
    }

    public static void add(Task task) {
    }


}

