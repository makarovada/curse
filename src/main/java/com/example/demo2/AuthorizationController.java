package com.example.demo2;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main/resources/com/example/demo2/registration.fxml"));
            fxmlLoader.setRoot(new Pane());
            Parent root = fxmlLoader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });
        enter.setOnAction(actionEvent -> {
            String query = "SELECT member_id, role FROM members WHERE login = ? AND password= ?";
            try {
                ResultSet res = null;
                DataBaseHandler db = DataBaseHandler.getInstance();
                PreparedStatement pst = db.getConnection().prepareStatement(query);
                        //db.getConnection().createStatement().executeQuery(query);
                pst.setString(1, login_field.getText());
                pst.setString(2, Integer.toString(password_field.getText().hashCode()));
                res = pst.executeQuery();
                while(res.next()){
                    User.setId(res.getInt("member_id"));
                    User.setRole(res.getInt("role"));
                    System.out.println(User.getId());
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            if(User.getId()!=0 && User.getRole()==2){
                enter.getScene().getWindow().hide();


                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/main/resources/com/example/demo2/client_account.fxml"));
                fxmlLoader.setRoot(new Pane());
                Parent root = fxmlLoader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();
            }


        });


    }

}
