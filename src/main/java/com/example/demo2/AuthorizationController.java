package com.example.demo2;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
        enter.setOnAction(actionEvent -> {
            String query = "SELECT * FROM members" ;
            try {
                //DateBaseHandler db = new DateBaseHandler();
                ResultSet result = DateBaseHandler.statement.executeQuery(query);
                while(result.next()){
                    int member_id = result.getInt("member_id" );
                    String login = result.getString("login" );
                    String password = result.getString("password" );
                    int role = result.getInt("role");
                    System.out.print("Member: " );
                    System.out.print(" id = " + member_id);
                    System.out.print(" , login = \" " + login + " \" " );
                    System.out.print(" , password = \" " + password + " \" . " );
                    System.out.println(" , role = \" " + role + " \" . " );
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
