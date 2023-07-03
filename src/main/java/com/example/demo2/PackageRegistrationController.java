package com.example.demo2;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.function.DoubleToIntFunction;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class PackageRegistrationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField first_name_field;

    @FXML
    private Button get_id_package_button;

    @FXML
    private TextField phone_field;

    @FXML
    private TextField second_name_field;

    @FXML
    private CheckBox urgency;

    @FXML
    private TextField weight_field;

    @FXML
    void initialize() {
         get_id_package_button.setOnAction(actionEvent -> {
            String request;
            try {
                DataBaseHandler db = DataBaseHandler.getInstance();
                int id = 0;
                int id_dc = 0;
                int id_cust = 0;
                 //res = null;
                request = "SELECT customer_id, dc_id, customer_id FROM people JOIN members USING(human_id) " +
                        "JOIN customers ON members.member_id=customers.member_id AND role=2 " +
                        "WHERE first_name=? AND second_name=? AND phone=? AND dc_id IS NOT NULL";
                PreparedStatement pst = db.getConnection().prepareStatement(request);
                pst.setString(1, first_name_field.getText());
                pst.setString(2, second_name_field.getText());
                pst.setString(3, phone_field.getText());
                ResultSet res = pst.executeQuery();
                while (res.next()){
                    id = res.getInt("customer_id");
                    id_dc = res.getInt("dc_id");
                    id_cust = res.getInt("customer_id");
                    System.out.println(id + ", " + id_dc + ", " + id_cust);
                }
                if(id==0){
                    System.out.println("Получатель не зарегистрирован в системе или у него не подтверждена учетная запись. Отправление посылки невозможно.");
                }
                else{
                    request = "SELECT couriers.courier_id FROM couriers " +
                            "LEFT JOIN packages USING(courier_id) WHERE dc_id=? GROUP BY couriers.courier_id " +
                            "HAVING COUNT(*)!=0 AND SUM(packages.urgency)=" +
                            "(SELECT MIN(A) FROM " +
                            "(SELECT SUM(urgency) AS A FROM packages AS p " +
                            "JOIN couriers AS c USING(courier_id) " +
                            "JOIN delivery_centrers AS dc USING(dc_id) " +
                            "WHERE dc.dc_id=? AND p.status=0 GROUP BY c.courier_id) AS B)" +
                            " OR COUNT(package_id)=0 LIMIT 1";
                    pst = db.getConnection().prepareStatement(request);
                    pst.setInt(1, id_dc);
                    pst.setInt(2, id_dc);
                    ResultSet result = pst.executeQuery();
                    int id_courier = 0;
                    while(result.next()){
                        id_courier = result.getInt(1);
                    }
                    System.out.println(id_courier);

                    request = "INSERT INTO packages (weight, urgency, courier_id) VALUES (?,?,?)";
                    pst = db.getConnection().prepareStatement(request);
                    pst.setInt(1, Integer.parseInt(weight_field.getText()));
                    int ur = 0;
                    if(urgency.isSelected())
                        ur = 1;
                    pst.setInt(2, ur);
                    pst.setInt(3, id_courier);
                    pst.executeUpdate();

                    Statement statement = db.getConnection().createStatement();
                    int id_package = 0;
                    request = "SELECT package_id FROM packages ORDER BY package_id DESC LIMIT 0, 1";
                    result = statement.executeQuery(request);
                    while(result.next()){
                        id_package = result.getInt(1);
                    }
                    System.out.println(id_package);



                    request = "INSERT INTO sender_cust (sender_id, package_id) VALUES (?,?)";
                    pst = db.getConnection().prepareStatement(request);
                    System.out.println(User.getId() + ", " + id_package);
                    pst.setInt(1, id_cust);
                    pst.setInt(2, id_package);
                    pst.executeUpdate();

                    request = "INSERT INTO recipient_cust (recipient_id, package_id) VALUES (?,?)";
                    pst = db.getConnection().prepareStatement(request);
                    pst.setInt(1, id);
                    pst.setInt(2, id_package);
                    pst.executeUpdate();

                    request = "INSERT INTO sender_dc (package_id) VALUES (?)";
                    pst = db.getConnection().prepareStatement(request);
                    pst.setInt(1, id_package);
                    pst.executeUpdate();

                    request = "INSERT INTO recipient_dc (recipient_dc_id, package_id) VALUES (?,?)";
                    pst = db.getConnection().prepareStatement(request);
                    pst.setInt(1, id_dc);
                    pst.setInt(2, id_package);
                    pst.executeUpdate();
                    System.out.println(id_package);

                }
                    /*

                    request = "INSERT INTO members (login, password, human_id) VALUES (?,?,?)";
                    pst = db.getConnection().prepareStatement(request);
                    pst.setString(1, login_field.getText());
                    pst.setString(2, String.valueOf(password_field.getText().hashCode()));
                    pst.setInt(3, value);
                    pst.executeUpdate();
                }

                //request = "SELECT IF(dc_id IS NULL, 0, 1) AS a FROM customers WHERE customer_id";



                request = "SELECT customer_id FROM people JOIN members USING(human_id) " +
                        "JOIN customers ON members.member_id=customers.member_id AND role=2 " +
                        "WHERE first_name=? AND second_name=? AND phone=?" +
                        "LIMIT 0, 1";
                pst = db.getConnection().prepareStatement(request);
                pst.setString(1, first_name_field.getText());
                pst.setString(2, second_name_field.getText());
                pst.setString(3, phone_field.getText());
                res = pst.executeQuery();
                while (res.next()){
                    id = res.getInt(1);
                }



                request = "INSERT INTO members (login, password, human_id) VALUES (?,?,?)";
                pst = db.getConnection().prepareStatement(request);
                pst.setString(1, login_field.getText());
                pst.setString(2, String.valueOf(password_field.getText().hashCode()));
                pst.setInt(3, value);
                pst.executeUpdate();

                statement = db.getConnection().createStatement();
                value = 0;
                request = "SELECT member_id FROM members ORDER BY member_id DESC LIMIT 0, 1";
                result = statement.executeQuery(request);
                while(result.next()){
                    value = result.getInt(1);
                }

                request = "INSERT INTO customers (address, member_id) VALUES (?,?)";
                pst = db.getConnection().prepareStatement(request);
                pst.setString(1, address_field.getText());
                pst.setInt(2, value);
                pst.executeUpdate();

 */


            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        });

    }

}
