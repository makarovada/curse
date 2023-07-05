package com.example.demo2;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.function.DoubleToIntFunction;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PackageRegistrationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField first_name_field;

    @FXML
    private Button get_id_package_button;

    @FXML
    private TextField phone_field;

    @FXML
    private TextField second_name_field;

    @FXML
    private CheckBox urgency;

    @FXML
    private TextField weight_field;

    @FXML
    void initialize() {
         get_id_package_button.setOnAction(actionEvent -> {
             try{
             DataBaseHandler db = DataBaseHandler.getInstance();
             db.package_registartion(first_name_field.getText(), second_name_field.getText(), phone_field.getText(), urgency.isSelected(), Integer.parseInt(weight_field.getText()));


                    get_id_package_button.getScene().getWindow().hide();

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
