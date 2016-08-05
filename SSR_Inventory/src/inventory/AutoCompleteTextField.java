package inventory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sun.management.snmp.jvmmib.JvmThreadInstanceTableMeta;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by dgist on 7/17/2016.
 *
 *  Use by :             AutoCompleteTextField field = new AutoCompleteTextField();
                         field.getEntries().addAll(Arrays.asList(s)); //some list

                         border.getChildren().addAll(field);
 *
 *
 */
public class AutoCompleteTextField extends TextField
{
    /** The existing autocomplete entries. */
    private final SortedSet<String> entries;
    /** The popup used to select an entry. */
    private ContextMenu entriesPopup;

    private Items chosenItem;
    private ObservableList<Items> items;

    /** Construct a new AutoCompleteTextField. */
    public AutoCompleteTextField() {
        super();
        entries = new TreeSet<>();
        entriesPopup = new ContextMenu();
        textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                if (getText().length() == 0) {
                    entriesPopup.hide();
                } else {
                    LinkedList<String> searchResult = new LinkedList<>();
                    //System.out.println(getText());

                    for(String entity : entries){
                        if(entity.contains(getText())){
                            searchResult.add(entity);
                            //System.out.println(entity);
                        }
                    }

                    searchResult.addAll(entries.subSet(getText(), getText() + Character.MAX_VALUE));
                    if (entries.size() > 0) {
                        populatePopup(searchResult);
                        if (!entriesPopup.isShowing()) {
                            entriesPopup.show(AutoCompleteTextField.this, Side.BOTTOM, 0, 0);
                        }
                    } else {
                        entriesPopup.hide();
                    }
                }
            }
        });

        focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean aBoolean2) {
                entriesPopup.hide();
            }
        });

    }

    /**
     * Get the existing set of autocomplete entries.
     * @return The existing autocomplete entries.
     */
    public SortedSet<String> getEntries () {
        return entries;
    }

    /**
     * Populate the entry set with the given search results.  Display is limited to 10 entries, for performance.
     * @param searchResult The set of matching strings.
     */

    private void populatePopup(List<String> searchResult) {
        List<CustomMenuItem> menuItems = new LinkedList<>();
        // If you'd like more entries, modify this line.
        int maxEntries = 20;
        int count = Math.min(searchResult.size(), maxEntries);
        for (int i = 0; i < count; i++) {
            final String result = searchResult.get(i);
            Label entryLabel = new Label(result);
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    setText(result);
                    System.out.println("You have selected: "+ result);
                    openProduct(result);
                    entriesPopup.hide();
                }
            });
            menuItems.add(item);
        }
        entriesPopup.getItems().clear();
        entriesPopup.getItems().addAll(menuItems);

    }

    public void setSearchContent(ObservableList<Items> content){
        ObservableList<String> searchContent = FXCollections.observableArrayList();

        for(Items entity : content) {
            searchContent.add(entity.getCustomLabel());
        }

        items = content;
        this.getEntries().addAll(searchContent);
    }

    public void openProduct(String Product){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("product_information.fxml"));
            //Scene scene = new Scene(root,450,450);

            setChosenItem(Product);
            Stage stage = new Stage();
            stage.setTitle(Product);
            stage.setScene(new Scene(root, 390, 505));
            stage.setResizable(false);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public Items getChosenItem(){
        return chosenItem;
    }

    public void setChosenItem(String Chosen){

        for(Items pick : items){
            if(pick.getCustomLabel().equals(Chosen)){
                chosenItem = pick;
            }
        }
    }

    public ObservableList<Items> getItems() {
        return items;
    }
}