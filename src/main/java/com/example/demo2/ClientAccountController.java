package com.example.demo2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

public class ClientAccountController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button button_change_data;

    @FXML
    private Button button_send_package;

    @FXML
    private Pane data;

    @FXML
    private Tab send_packages;

    @FXML
    private ListView<?> wait_packages;

    @FXML
    void initialize() {

    }

}
