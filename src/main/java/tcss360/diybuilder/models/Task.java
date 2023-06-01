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
        ArrayList<Item> copyList = new ArrayList<Item>();
        for (int i = 0; i < itemsList.size(); i++) {
            copyList.add(itemsList.get(i));
        }
        return copyList;
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

    //ALex G comment: can use this for one of our unit tests
    public static void main(String[] theArgs) {
        ArrayList<Item> myItemList = new ArrayList<>();
        myItemList.add(new Item("Rice", 38.50, 3));
        myItemList.add(new Item("IPhone12", 969.55, 2));
        myItemList.add(new Item("Sticker", 3.50, 3));
        Task task1 = new Task("Buy List", myItemList);
        System.out.println(task1.toString());

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

