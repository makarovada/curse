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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DcRegistrationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField address_field;

    @FXML
    private Button back;

    @FXML
    private TextField name_field;

    @FXML
    private Button registration_button;

    @FXML
    void initialize() {
        back.setOnAction(actionEvent -> {
            back.getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("admin_account_1.fxml"));
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
        registration_button.setOnAction(actionEvent -> {
            try {
                DataBaseHandler db = DataBaseHandler.getInstance();
                if(name_field.getText().equals("") || address_field.getText().equals("")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Регистрация");
                    alert.setHeaderText("Ошибка регистрации");
                    alert.setContentText("Введите данные во все поля");
                    alert.showAndWait();}
                else{
                    db.registration_dc(name_field.getText(), address_field.getText());

                }

                registration_button.getScene().getWindow().hide();

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("admin_account_1.fxml"));
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

