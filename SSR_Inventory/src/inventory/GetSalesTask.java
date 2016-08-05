package inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import static inventory.Controller.dbHelper;

/**
 * Created by SSR on 7/28/2016.
 */
public class GetSalesTask extends Task<ObservableList<Sales>> {
    @Override
    protected ObservableList<Sales> call() throws Exception {
        for (int i = 0; i < 250; i++) {
            updateProgress(i, 250);
            Thread.sleep(5);
        }

        return dbHelper.getSalesSheets();
    }
}
