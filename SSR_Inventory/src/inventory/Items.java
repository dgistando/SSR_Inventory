package inventory;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by SSR on 7/12/2016.
 */
public class Items {
    SimpleStringProperty custom_label;

    public Items(){

    }

    private SimpleStringProperty getCustom_label(){
        return custom_label;
    }

    private void setCustom_label(SimpleStringProperty input){
        custom_label = input;
    }
}
