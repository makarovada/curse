package com.example.demo2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException, ClassNotFoundException {
        DataBaseHandler.getInstance();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("authorization.fxml"));
        fxmlLoader.setRoot(new Pane());
        Parent root = fxmlLoader.load();
        stage.setTitle("Hello!");
        stage.setScene(new Scene(root));


        /*
        Parent root = FXMLLoader.load(getClass().getResource("authorization.fxml"));
        stage.setTitle("Delivery_center");
        stage.setScene(new Scene(root));

         */
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}