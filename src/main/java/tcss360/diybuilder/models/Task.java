/*
 * Team Periwinkle
 */
package tcss360.diybuilder.models;

import java.util.ArrayList;

/**
 * Task Object class.
 *
 * @author Soe Lin
 * @author Mey Vo
 */
public class Task {

    /** THe name of the Task. */
    private String name;
    /** Array List of item. */
    private ArrayList<Item> itemsList;

    /**
     * Constructor.
     *
     * @param theName
     * @param theItemsList
     */
    public Task(String theName, ArrayList<Item> theItemsList) {
        name = theName;
        itemsList = new ArrayList<Item>();
        for (int i = 0; i < theItemsList.size(); i++) {
            itemsList.add(theItemsList.get(i));
        }
    }


    public Task(String name2, int pn, String startDate) {
    }


    public String getName() {
        return name;
    }

    public void setName(String theName) {
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
        return getPN();
    }

    public String getStartDate() {
        return getStartDate();
    }

    public void setPn(int pn) {
    }


    public void setStartDate(String startDate) {
    }


    public static Task get(int i) {
        return null;
    }


    public static int size() {
        return 0;
    }


}

