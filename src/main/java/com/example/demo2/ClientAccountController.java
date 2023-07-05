package com.example.demo2;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ClientAccountController {
    @FXML
    private Label address_field;

    @FXML
    private Button button_change_data;

    @FXML
    private Button button_send_package;

    @FXML
    private TableColumn<Expected_packages, Integer> id_column;

    @FXML
    private TableColumn<Sent_packages, Integer> id_column_1;

    @FXML
    private TableColumn<Expected_packages, String> name_column;

    @FXML
    private Label name_field;

    @FXML
    private TableColumn<Sent_packages, String> name_recipient_column;

    @FXML
    private TableColumn<Expected_packages, String> phone_column;

    @FXML
    private Label phone_field;

    @FXML
    private Tab send_packages;

    @FXML
    private TableView<Sent_packages> sent_packages;

    @FXML
    private TableColumn<Sent_packages, String> status_column;

    @FXML
    private TableColumn<Expected_packages, String> urgency_column;

    @FXML
    private TableColumn<Sent_packages, String> urgency_column_1;

    @FXML
    private TableView<Expected_packages> wait_packages;

    @FXML
    private TableColumn<Expected_packages, Integer> weight_column;

    @FXML
    private TableColumn<Sent_packages, Integer> weight_column_1;

    @FXML
    void initialize() {
        button_send_package.setOnAction(actionEvent -> {
            button_send_package.getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("package_registration_1.fxml"));
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
        button_change_data.setOnAction(actionEvent -> {
            button_change_data.getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("update_data.fxml"));
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
        try {
            name_field.setText(Customer.getFirst_name()+" "+Customer.getSecond_name()+" "+Customer.getLast_name());
            phone_field.setText(Customer.getPhone());
            address_field.setText(Customer.getAddress());

            DataBaseHandler db = DataBaseHandler.getInstance();

            ResultSet res = db.client_account_w();

            ObservableList<Expected_packages> w = FXCollections.observableArrayList();
            id_column.setCellValueFactory(new PropertyValueFactory<Expected_packages, Integer>("package_id"));
            weight_column.setCellValueFactory(new PropertyValueFactory<Expected_packages, Integer>("weight"));
            urgency_column.setCellValueFactory(new PropertyValueFactory<Expected_packages, String>("urgency"));
            name_column.setCellValueFactory(new PropertyValueFactory<Expected_packages, String>("name"));
            phone_column.setCellValueFactory(new PropertyValueFactory<Expected_packages, String>("phone"));


            while(res.next()) {
                w.add(new Expected_packages(res.getInt(1), res.getInt(2),
                                res.getString(3), res.getString(4), res.getString(5)));
            }
            wait_packages.setItems(w);



            res = db.client_account_w1();

            ObservableList<Sent_packages> w1 = FXCollections.observableArrayList();
            id_column_1.setCellValueFactory(new PropertyValueFactory<Sent_packages, Integer>("package_id"));
            weight_column_1.setCellValueFactory(new PropertyValueFactory<Sent_packages, Integer>("weight"));
            urgency_column_1.setCellValueFactory(new PropertyValueFactory<Sent_packages, String>("urgency"));
            name_recipient_column.setCellValueFactory(new PropertyValueFactory<Sent_packages, String>("name"));
            status_column.setCellValueFactory(new PropertyValueFactory<Sent_packages, String>("status"));


            while(res.next()) {
                w1.add(new Sent_packages(res.getInt(1), res.getInt(2),
                        res.getString(3), res.getString(4), res.getString(5)));
            }
            sent_packages.setItems(w1);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }

}
