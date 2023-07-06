package com.example.demo2;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class CourierAccountController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Package_courier, String> address_column;

    @FXML
    private Label dc_field;

    @FXML
    private TableColumn<Package_courier, Integer> id_column;

    @FXML
    private TextField id_package_field;

    @FXML
    private TableColumn<Package_courier, String> name_column;

    @FXML
    private Label name_field;

    @FXML
    private TableColumn<Package_courier, String> phone_column;

    @FXML
    private Label phone_field;
    @FXML
    private Button status_package_button;

    @FXML
    private TableColumn<Package_courier, String> urgency_column;

    @FXML
    private TableView<Package_courier> wait_packages;

    @FXML
    private TableColumn<Package_courier, Integer> weight_column;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        name_field.setText(Courier.getFirst_name()+" "+Courier.getSecond_name()+" "+Courier.getLast_name());
        phone_field.setText(Courier.getPhone());
        String s = String.valueOf(Courier.getDc_id());
        dc_field.setText(s);

        DataBaseHandler db = DataBaseHandler.getInstance();

        ResultSet res = db.courier_account_table();

        ObservableList<Package_courier> w = FXCollections.observableArrayList();
        id_column.setCellValueFactory(new PropertyValueFactory<Package_courier, Integer>("package_id"));
        weight_column.setCellValueFactory(new PropertyValueFactory<Package_courier, Integer>("weight"));
        urgency_column.setCellValueFactory(new PropertyValueFactory<Package_courier, String>("urgency"));
        name_column.setCellValueFactory(new PropertyValueFactory<Package_courier, String>("name"));
        phone_column.setCellValueFactory(new PropertyValueFactory<Package_courier, String>("phone"));
        address_column.setCellValueFactory(new PropertyValueFactory<Package_courier, String>("address"));


        while(res.next()) {
            w.add(new Package_courier(res.getInt(1), res.getInt(2),
                    res.getString(3), res.getString(4), res.getString(5), res.getString(6)));
        }
        wait_packages.setItems(w);

        status_package_button.setOnAction(actionEvent -> {
            try {
                DataBaseHandler bd = DataBaseHandler.getInstance();
                bd.delivered_package(Integer.parseInt(id_package_field.getText()));

                ResultSet res1 = db.courier_account_table();

                ObservableList<Package_courier> w1 = FXCollections.observableArrayList();
                id_column.setCellValueFactory(new PropertyValueFactory<Package_courier, Integer>("package_id"));
                weight_column.setCellValueFactory(new PropertyValueFactory<Package_courier, Integer>("weight"));
                urgency_column.setCellValueFactory(new PropertyValueFactory<Package_courier, String>("urgency"));
                name_column.setCellValueFactory(new PropertyValueFactory<Package_courier, String>("name"));
                phone_column.setCellValueFactory(new PropertyValueFactory<Package_courier, String>("phone"));
                address_column.setCellValueFactory(new PropertyValueFactory<Package_courier, String>("address"));


                while(res1.next()) {
                    w1.add(new Package_courier(res1.getInt(1), res1.getInt(2),
                            res1.getString(3), res1.getString(4), res1.getString(5), res1.getString(6)));
                }
                wait_packages.setItems(w1);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

}

