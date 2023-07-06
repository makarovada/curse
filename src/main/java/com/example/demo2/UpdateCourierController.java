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

public class UpdateCourierController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button back;

    @FXML
    private Button change_button;

    @FXML
    private TextField dc_field;

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
    private TextField id_courier_field;

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
        change_button.setOnAction(actionEvent -> {
            DataBaseHandler db = null;
            try {
                db = DataBaseHandler.getInstance();

                int dc; int id;
                if(dc_field.getText().equals(""))
                    dc = 0;
                else dc = Integer.parseInt(dc_field.getText());
                if(id_courier_field.getText().equals(""))
                    id = 0;
                else id = Integer.parseInt(id_courier_field.getText());
                if(id==0){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Изменение");
                    alert.setHeaderText("Ошибка изменения");
                    alert.setContentText("Введите id");
                    alert.showAndWait();
                }
                while(!db.update_courier(first_name_field.getText(), second_name_field.getText(), last_name_field.getText(),
                        phone_field.getText(), dc, password_field.getText().hashCode(), id)){
                    if(dc_field.getText().equals(""))
                        dc = 0;
                    else dc = Integer.parseInt(dc_field.getText());
                    if(id_courier_field.getText().equals(""))
                        id = 0;
                    else id = Integer.parseInt(id_courier_field.getText());
                    if(id==0){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Изменение");
                        alert.setHeaderText("Ошибка изменения");
                        alert.setContentText("Введите id");
                        alert.showAndWait();
                    }
                }
                change_button.getScene().getWindow().hide();

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


            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

}

