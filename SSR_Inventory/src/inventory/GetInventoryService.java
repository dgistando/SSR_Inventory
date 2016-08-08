package inventory;

import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import static inventory.Inventory_Controller.programInfo;

/**
 * Created by dgist on 8/6/2016.
 */
public class GetInventoryService extends Service<ObservableList<Items>> {

    @Override
    protected Task<ObservableList<Items>> createTask() {
        programInfo.setText("Getting Inventory...");
        return new GetInventoryTask();
    }
}
