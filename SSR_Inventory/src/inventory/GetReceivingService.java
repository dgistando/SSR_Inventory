package inventory;

import inventory.GetSalesTask;
import inventory.Sales;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Created by SSR on 8/8/2016.
 */
public class GetReceivingService  extends Service<ObservableList<Sales>> {

    @Override
    protected Task createTask() {
        return new GetReceivingTask();
    }
}
