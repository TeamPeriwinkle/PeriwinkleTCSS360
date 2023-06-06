/*
 * Team Periwinkle
 */
package tcss360.diybuilder.models;

import java.util.ArrayList;
import tcss360.diybuilder.SystemControl.ProjectController;

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

        // Add item to the project controller
        String itemName = theItem.getName();
        Double itemPrice = theItem.getPrice();
        String itemUnits = theItem.getUnit();
        ProjectController.createItem(itemName, itemPrice,itemUnits);
    }

    public void removeItem(int index) {
        itemsList.remove(index);

        // Delete from project Controller
        Item itemToDelete = itemsList.get(index);
        String itemName = itemToDelete.getName();
        ProjectController.deleteItem(itemName);
    }

    /**
     * Edits an item in the itemsList based on the item name.
     * Updates the price and unit of the specified item.
     * @param itemName the item to be edited
     * @param newPrice the new price to be set
     * @param newUnit  the new unit amount to be set
     */
    public void editItem(String itemName, double newPrice, int newUnit) {
        for (Item item : itemsList) {
            if (item.getName().equals(itemName)) {
                item.setPrice(newPrice);
                item.setUnit(newUnit);
                break;
            }
        }

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

