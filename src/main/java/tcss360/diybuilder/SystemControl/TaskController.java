package tcss360.diybuilder.SystemControl;

import tcss360.diybuilder.models.Item;
import tcss360.diybuilder.models.Task;

import java.util.ArrayList;

public class TaskController {

    public static double calcuateTaskCost(Task theTask) {
        double result = 0;
        ArrayList<Item> itemsList = theTask.getItemsList();
        for (int j = 0; j < itemsList.size(); j++) {
            result += itemsList.get(j).getTotalCost();
        }
        return result;
    }

}

