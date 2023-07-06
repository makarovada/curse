package com.example.demo2;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

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

public class AdminAccountController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField id_customer_field;

    @FXML
    private TableColumn<DeliveredPackagesAdmin, String> address_column1;

    @FXML
    private TableColumn<CustomerTable, String> address_column_customer;

    @FXML
    private Label address_field;

    @FXML
    private Button chamge_courier_button;

    @FXML
    private TableColumn<DeliveredPackagesAdmin, Integer> courier_id_column;

    @FXML
    private TableView<CourierTable> couriers;

    @FXML
    private Button create_admin_button;

    @FXML
    private Button create_dc_button;

    @FXML
    private TableView<CustomerTable> customers;

    @FXML
    private TableColumn<CustomerTable, Integer> dc_id_column_customer;

    @FXML
    private TableColumn<SentPackagesAdmin, Integer> dc_recipient_column;

    @FXML
    private TableColumn<CourierTable, String> fio_column_courier;

    @FXML
    private TableColumn<CustomerTable, String> fio_column_customer;

    @FXML
    private TableColumn<DeliveredPackagesAdmin, Integer> id_column1;

    @FXML
    private TableColumn<CourierTable, Integer> id_column_courier;

    @FXML
    private TableColumn<CustomerTable, Integer> id_column_customer;

    @FXML
    private TextField id_package_field1;

    @FXML
    private TextField id_package_field2;
    @FXML
    private TableColumn<SentPackagesAdmin, Integer> status_column1;

    @FXML
    private TableColumn<DeliveredPackagesAdmin, Integer> status_column2;

    @FXML
    private TableColumn<DeliveredPackagesAdmin, String> name_column1;

    @FXML
    private Label name_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private TableColumn<CourierTable, String> phone_column_courier;

    @FXML
    private TableColumn<CustomerTable, String> phone_column_customer;

    @FXML
    private Label phone_field;

    @FXML
    private Button registration_courier;

    @FXML
    private TableColumn<SentPackagesAdmin, Integer> sent_id_column;

    @FXML
    private TableColumn<SentPackagesAdmin, String> sent_name_column;

    @FXML
    private TableView<SentPackagesAdmin> sent_packages;

    @FXML
    private TableColumn<SentPackagesAdmin, String> sent_phone_column;

    @FXML
    private TableColumn<SentPackagesAdmin, String> sent_urgency_column;

    @FXML
    private TableColumn<SentPackagesAdmin, Integer> sent_weight_column;

    @FXML
    private Button status_package_button1;

    @FXML
    private Button status_package_button13;

    @FXML
    private Button status_package_button2;

    @FXML
    private TableColumn<DeliveredPackagesAdmin, String> urgency_column1;

    @FXML
    private TableView<DeliveredPackagesAdmin> wait_packages;

    @FXML
    private TableColumn<DeliveredPackagesAdmin, Integer> weight_column1;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        name_field.setText(Admin.getFirst_name() + " " + Admin.getSecond_name() + " " + Admin.getLast_name());
        phone_field.setText(Admin.getPhone());
        address_field.setText(Admin.getDc_name());
        DataBaseHandler db = DataBaseHandler.getInstance();

        ResultSet res = db.courier_table();

        ObservableList<CourierTable> w = FXCollections.observableArrayList();
        id_column_courier.setCellValueFactory(new PropertyValueFactory<CourierTable, Integer>("courier_id"));
        fio_column_courier.setCellValueFactory(new PropertyValueFactory<CourierTable, String>("fio"));
        phone_column_courier.setCellValueFactory(new PropertyValueFactory<CourierTable, String>("phone"));


        while (res.next()) {
            w.add(new CourierTable(res.getInt(1), res.getString(2),
                    res.getString(3)));
        }
        couriers.setItems(w);

        ResultSet res1 = db.customer_table();

        ObservableList<CustomerTable> w1 = FXCollections.observableArrayList();
        id_column_customer.setCellValueFactory(new PropertyValueFactory<CustomerTable, Integer>("customer_id"));
        fio_column_customer.setCellValueFactory(new PropertyValueFactory<CustomerTable, String>("fio"));
        phone_column_customer.setCellValueFactory(new PropertyValueFactory<CustomerTable, String>("phone"));
        address_column_customer.setCellValueFactory(new PropertyValueFactory<CustomerTable, String>("address"));
        dc_id_column_customer.setCellValueFactory(new PropertyValueFactory<CustomerTable, Integer>("dc_id"));


        while (res1.next()) {
            w1.add(new CustomerTable(res1.getInt(1), res1.getString(2),
                    res1.getString(3), res1.getString(4), res1.getInt(5)));
        }
        customers.setItems(w1);


        res = db.sent_packages_admin();

        ObservableList<SentPackagesAdmin> w3 = FXCollections.observableArrayList();
        sent_id_column.setCellValueFactory(new PropertyValueFactory<SentPackagesAdmin, Integer>("package_id"));
        sent_weight_column.setCellValueFactory(new PropertyValueFactory<SentPackagesAdmin, Integer>("weight"));
        sent_urgency_column.setCellValueFactory(new PropertyValueFactory<SentPackagesAdmin, String>("urgency"));
        sent_name_column.setCellValueFactory(new PropertyValueFactory<SentPackagesAdmin, String>("name"));
        sent_phone_column.setCellValueFactory(new PropertyValueFactory<SentPackagesAdmin, String>("phone"));
        dc_recipient_column.setCellValueFactory(new PropertyValueFactory<SentPackagesAdmin, Integer>("recipient_dc"));
        status_column1.setCellValueFactory(new PropertyValueFactory<SentPackagesAdmin, Integer>("status"));



        while(res.next()) {
            w3.add(new SentPackagesAdmin(res.getInt(1), res.getInt(2), res.getString(3),
                    res.getString(4), res.getString(5), res.getInt(6), res.getInt(7)));
        }
        sent_packages.setItems(w3);


        res = db.delivered_packages_admin();

        ObservableList<DeliveredPackagesAdmin> w4 = FXCollections.observableArrayList();
        id_column1.setCellValueFactory(new PropertyValueFactory<DeliveredPackagesAdmin, Integer>("package_id"));
        weight_column1.setCellValueFactory(new PropertyValueFactory<DeliveredPackagesAdmin, Integer>("weight"));
        urgency_column1.setCellValueFactory(new PropertyValueFactory<DeliveredPackagesAdmin, String>("urgency"));
        name_column1.setCellValueFactory(new PropertyValueFactory<DeliveredPackagesAdmin, String>("name"));
        address_column1.setCellValueFactory(new PropertyValueFactory<DeliveredPackagesAdmin, String>("phone"));
        courier_id_column.setCellValueFactory(new PropertyValueFactory<DeliveredPackagesAdmin, Integer>("courier_id"));
        status_column2.setCellValueFactory(new PropertyValueFactory<DeliveredPackagesAdmin, Integer>("status"));




        while(res.next()) {
            w4.add(new DeliveredPackagesAdmin(res.getInt(1), res.getInt(2),
                    res.getString(3), res.getString(4), res.getString(5), res.getInt(6), res.getInt(7)));
        }
        wait_packages.setItems(w4);



        status_package_button13.setOnAction(actionEvent -> {
            try {
                db.attach_customer(Integer.parseInt(id_customer_field.getText()));
                ResultSet res2 = db.customer_table();

                ObservableList<CustomerTable> w5 = FXCollections.observableArrayList();
                id_column_customer.setCellValueFactory(new PropertyValueFactory<CustomerTable, Integer>("customer_id"));
                fio_column_customer.setCellValueFactory(new PropertyValueFactory<CustomerTable, String>("fio"));
                phone_column_customer.setCellValueFactory(new PropertyValueFactory<CustomerTable, String>("phone"));
                address_column_customer.setCellValueFactory(new PropertyValueFactory<CustomerTable, String>("address"));
                dc_id_column_customer.setCellValueFactory(new PropertyValueFactory<CustomerTable, Integer>("dc_id"));


                while (res2.next()) {
                    w5.add(new CustomerTable(res2.getInt(1), res2.getString(2),
                            res2.getString(3), res2.getString(4), res2.getInt(5)));
                }
                customers.setItems(w5);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        status_package_button1.setOnAction(actionEvent -> {
            try {
                db.status_1(Integer.parseInt(id_package_field1.getText()));
                 ResultSet res2 = db.sent_packages_admin();

                ObservableList<SentPackagesAdmin> w6 = FXCollections.observableArrayList();
                sent_id_column.setCellValueFactory(new PropertyValueFactory<SentPackagesAdmin, Integer>("package_id"));
                sent_weight_column.setCellValueFactory(new PropertyValueFactory<SentPackagesAdmin, Integer>("weight"));
                sent_urgency_column.setCellValueFactory(new PropertyValueFactory<SentPackagesAdmin, String>("urgency"));
                sent_name_column.setCellValueFactory(new PropertyValueFactory<SentPackagesAdmin, String>("name"));
                sent_phone_column.setCellValueFactory(new PropertyValueFactory<SentPackagesAdmin, String>("phone"));
                dc_recipient_column.setCellValueFactory(new PropertyValueFactory<SentPackagesAdmin, Integer>("recipient_dc"));
                status_column1.setCellValueFactory(new PropertyValueFactory<SentPackagesAdmin, Integer>("status"));




                while(res2.next()) {
                    w6.add(new SentPackagesAdmin(res2.getInt(1), res2.getInt(2),
                            res2.getString(3), res2.getString(4), res2.getString(5), res2.getInt(6), res2.getInt(7)));
                }
                sent_packages.setItems(w6);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        status_package_button2.setOnAction(actionEvent -> {
            try {
                db.status_2(Integer.parseInt(id_package_field2.getText()));
                ResultSet res2 = db.delivered_packages_admin();

                ObservableList<DeliveredPackagesAdmin> w7 = FXCollections.observableArrayList();
                id_column1.setCellValueFactory(new PropertyValueFactory<DeliveredPackagesAdmin, Integer>("package_id"));
                weight_column1.setCellValueFactory(new PropertyValueFactory<DeliveredPackagesAdmin, Integer>("weight"));
                urgency_column1.setCellValueFactory(new PropertyValueFactory<DeliveredPackagesAdmin, String>("urgency"));
                name_column1.setCellValueFactory(new PropertyValueFactory<DeliveredPackagesAdmin, String>("name"));
                address_column1.setCellValueFactory(new PropertyValueFactory<DeliveredPackagesAdmin, String>("phone"));
                courier_id_column.setCellValueFactory(new PropertyValueFactory<DeliveredPackagesAdmin, Integer>("courier_id"));
                status_column2.setCellValueFactory(new PropertyValueFactory<DeliveredPackagesAdmin, Integer>("status"));




                while(res2.next()) {
                    w7.add(new DeliveredPackagesAdmin(res2.getInt(1), res2.getInt(2),
                            res2.getString(3), res2.getString(4), res2.getString(5), res2.getInt(6), res2.getInt(7)));
                }
                wait_packages.setItems(w7);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        chamge_courier_button.setOnAction(actionEvent -> {
            chamge_courier_button.getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("update_courier.fxml"));
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
        registration_courier.setOnAction(actionEvent -> {
            registration_courier.getScene().getWindow().hide();

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("registartion_courier.fxml"));
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
        create_admin_button.setOnAction(actionEvent -> {
            if(password_field.getText().equals("proga")){
                create_admin_button.getScene().getWindow().hide();

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("registartion_admin.fxml"));
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
                alert.setHeaderText("Неправильный пароль");
                alert.showAndWait();
            }
        });
        create_dc_button.setOnAction(actionEvent -> {
            if(password_field.getText().equals("proga")){
                create_dc_button.getScene().getWindow().hide();

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("dc_registration.fxml"));
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
                alert.setHeaderText("Неправильный пароль");
                alert.showAndWait();
            }
        });
    }

}

