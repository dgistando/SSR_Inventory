package inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.apache.poi.ss.formula.functions.T;
import sun.security.util.Password;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;


/**
 * This class controls the login page for Sling Shot racing Inventory
 * The controller is linked to the login.fxml and uses the database backend to verify
 * accounts. There are no inputs or outputs to this page.
 *
 * @author  David Gistand of SSR
 *
 */
public class Controller implements Initializable{

    @FXML
    Button Btnlogin;
    @FXML
    TextField username;
    @FXML
    PasswordField password;

    public static DBHelper dbHelper;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assert Btnlogin != null : "Button Btnlogin not found";
        assert username != null : "username texfield not found in FXML";
        assert password != null : "password passwordfield not found";
        dbHelper = new DBHelper();


        Btnlogin.setOnAction(e-> {
            try {

                if(username.getText().equals("") || password.getText().equals("")){
                    return;
                }

                //if(!LoginPass(username.getText(),password.getText())) {
                if(!LoginPass("SSR_Janet" ,"Password#2")) {
                        System.out.print("failed to login");
                        return;
                }else {


                    Parent root = FXMLLoader.load(getClass().getResource("view_inventory.fxml"));
                    //Scene scene = new Scene(root,450,450);

                    //TPinventory.getTabs().addAll(InventoryTable, InventoryTable1);

                    Stage stage = new Stage();
                    stage.setTitle("SSR_Inventory");
                    stage.setScene(new Scene(root, 750, 600));
                    stage.show();


                    //hide this current window
                    ((Node) (e.getSource())).getScene().getWindow().hide();
                }
            }catch (IOException err){
                err.printStackTrace();
            }
        });


        username.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)){
                    password.requestFocus();
                }
            }
        });

        password.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER) && !username.getText().equals("") || !password.getText().equals("")){
                    Btnlogin.fire();
                }
            }
        });

    }

    private boolean LoginPass(String u, String p){
        return dbHelper.credentialLogin(u,p);
    }


}
