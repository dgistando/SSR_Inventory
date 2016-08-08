package inventory;

import inventory.NewInventory;
import inventory.Sales;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import static inventory.Controller.dbHelper;

/**
 * Created by SSR on 8/8/2016.
 */
public class GetReceivingTask extends Task<ObservableList<NewInventory>> {
    @Override
    protected ObservableList<NewInventory> call() throws Exception {
        for (int i = 0; i < 230; i++) {
            updateProgress(i, 230);
            Thread.sleep(5);
        }


        return dbHelper.getReceivingSheets();
    }
}

