package com.example.demo2;

import java.io.IOException;
import java.net.URL;

import java.sql.SQLException;
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

public class AuthorizationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button enter;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button signUp;

    @FXML
    void initialize() {
        signUp.setOnAction(actionEvent -> {
            signUp.getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("registration.fxml"));
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
        });
        enter.setOnAction(actionEvent -> {
            try {
                DataBaseHandler db = DataBaseHandler.getInstance();
                db.authorization(login_field.getText(), Integer.toString(password_field.getText().hashCode()));
                if(User.getId()!=0 && User.getRole()==2){
                    db.customer();
                    enter.getScene().getWindow().hide();


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
                }
                else if(User.getId()!=0 && User.getRole()==1){
                    db.courier();
                    enter.getScene().getWindow().hide();


                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("courier_account.fxml"));
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
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Вход ");
                    alert.setHeaderText("Ошибка входа");
                    alert.setContentText("Введены неверные логин или пароль");
                    alert.showAndWait();
                }

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }


        });


    }

}
