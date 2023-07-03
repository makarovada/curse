package com.example.demo2;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.Stack;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RegistrationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField address_field;

    @FXML
    private Button enter;

    @FXML
    private TextField first_name_field;

    @FXML
    private TextField last_name_field;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private TextField phone_field;

    @FXML
    private TextField second_name_field;

    @FXML
    void initialize() {
        enter.setOnAction(actionEvent -> {
            String request = "INSERT INTO people (first_name, second_name, last_name, phone) VALUES (?,?,?,?)";
            try {
                DataBaseHandler db = DataBaseHandler.getInstance();
                PreparedStatement pst = db.getConnection().prepareStatement(request);
                pst.setString(1, first_name_field.getText());
                pst.setString(2, second_name_field.getText());
                pst.setString(3, last_name_field.getText());
                pst.setString(4, phone_field.getText());
                pst.executeUpdate();

                Statement statement = db.getConnection().createStatement();
                int value = 0;
                request = "SELECT human_id FROM people ORDER BY human_id DESC LIMIT 0, 1";
                ResultSet result = statement.executeQuery(request);
                while(result.next()){
                    value = result.getInt(1);
                }

                request = "INSERT INTO members (login, password, human_id) VALUES (?,?,?)";
                pst = db.getConnection().prepareStatement(request);
                pst.setString(1, login_field.getText());
                pst.setString(2, String.valueOf(password_field.getText().hashCode()));
                pst.setInt(3, value);
                pst.executeUpdate();

                statement = db.getConnection().createStatement();
                value = 0;
                request = "SELECT member_id FROM members ORDER BY member_id DESC LIMIT 0, 1";
                result = statement.executeQuery(request);
                while(result.next()){
                    value = result.getInt(1);
                }

                request = "INSERT INTO customers (address, member_id) VALUES (?,?)";
                pst = db.getConnection().prepareStatement(request);
                pst.setString(1, address_field.getText());
                pst.setInt(2, value);
                pst.executeUpdate();


                enter.getScene().getWindow().hide();

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("authorization.fxml"));
                fxmlLoader.setRoot(new Pane());
                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }Parent root = fxmlLoader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();


            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        });

    }

}
