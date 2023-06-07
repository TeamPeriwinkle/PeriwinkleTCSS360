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

    public Task(String name){
        this.name = name;
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

    public void addItem(String itemName, double price, int unit) {
        Item newItem= new Item(itemName, price, unit);

        //add to object datafield
        itemsList.add(newItem);

        //add to permanant data
        ProjectController.createItem(itemName, price,unit);
    }

    public void removeItem(String itemName) {
        // Delete from project Controller
        for (int i = 0; i < itemsList.size(); i++) {
            if(itemsList.get(i).getName().equals(itemName)){
                ProjectController.deleteItem(itemName);
                itemsList.remove(i);
            }
        }
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

        //item should be loaded in when editing an item
        ProjectController.editItem(itemName, newPrice, newUnit);

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


    public Item getItem(String itemName){
        for (Item item: itemsList) {
            if(item.getName().equals(itemName)){
                return item;
            }
        }

        //for error checkin this might not be ideal
        return new Item("", 0, 0);
    }

}

