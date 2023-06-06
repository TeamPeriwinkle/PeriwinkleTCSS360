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
        itemsList = theItemsList;
    }


    public Task(String taskName, String pn, String startDate) {
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
        return null;
    }

    public String getStartDate() {
        return null;
    }

    public static void add(Task task) {
    }

    public Item getItem(String itemName){
        for (Item item: itemsList) {
            if(item.getName().equals(itemName)){
                return item;
            }
        }

        //change this later
        return new Item("", 0, 0);
    }

}

