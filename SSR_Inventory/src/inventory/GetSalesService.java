package inventory;

import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;


/**
 * Created by dgist on 8/6/2016.
 */
public class GetSalesService extends Service<ObservableList<Sales>> {

    @Override
    protected Task createTask() {
        return new GetSalesTask();
    }
}
