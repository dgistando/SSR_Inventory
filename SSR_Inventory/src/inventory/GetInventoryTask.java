package inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;

import static inventory.Controller.dbHelper;

/**
 * Created by SSR on 7/19/2016.
 */
public class GetInventoryTask extends Task<ObservableList<Items>> {
    @Override
    protected ObservableList<Items> call() throws Exception {
        for (int i = 0; i < 500; i++) {
            updateProgress(i, 500);
            Thread.sleep(5);
        }

        return dbHelper.getAllItems();
    }
}

