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
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
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
            try {
                DataBaseHandler db = DataBaseHandler.getInstance();
                if(first_name_field.getText().equals("") || second_name_field.getText().equals("") ||
                        last_name_field.getText().equals("") || phone_field.getText().equals("") ||
                        login_field.getText().equals("") ||  password_field.getText().equals("") ||  address_field.getText().equals("")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Регистрация");
                    alert.setHeaderText("Ошибка регистрации");
                    alert.setContentText("Введите данные во все поля");
                    alert.showAndWait();}
                else{
                    db.registration(first_name_field.getText(), second_name_field.getText(), last_name_field.getText(), phone_field.getText(), login_field.getText(), password_field.getText(), address_field.getText());

                }

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
