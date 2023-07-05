package com.example.demo2;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UpdateDataController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField address_field;

    @FXML
    private TextField first_name_field;

    @FXML
    private TextField last_name_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private TextField phone_field;

    @FXML
    private TextField second_name_field;

    @FXML
    private Button update_data_button;

    @FXML
    void initialize() {
        update_data_button.setOnAction(actionEvent -> {
            DataBaseHandler db = null;
            try {
                db = DataBaseHandler.getInstance();

                while(!db.update_data(first_name_field.getText(), second_name_field.getText(), last_name_field.getText(),
                    phone_field.getText(), address_field.getText(), password_field.getText().hashCode())){

                }
                update_data_button.getScene().getWindow().hide();

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("client_account.fxml"));
                fxmlLoader.setRoot(new Pane());
                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Parent root = fxmlLoader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();


            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
