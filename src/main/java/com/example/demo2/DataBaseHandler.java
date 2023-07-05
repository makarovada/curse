package com.example.demo2;

import javafx.scene.control.Alert;

import java.sql.*;

public class DataBaseHandler {
    private static DataBaseHandler instance = null;
    private Connection connection;

    private DataBaseHandler() throws SQLException, ClassNotFoundException {
        // Connect to database
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://std-mysql.ist.mospolytech.ru:3306/std_2295_curse",
                "std_2295_curse", "Vfrfhjdf1");
    }

    public static DataBaseHandler getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) {
            instance = new DataBaseHandler();
        }
        return instance;
    }

    public Connection getConnection(){
        return connection;
    }
    public void authorization(String login, String password) throws SQLException, ClassNotFoundException {
        String query = "SELECT member_id, role FROM members WHERE login = ? AND password= ?";
        ResultSet res = null;
        PreparedStatement pst = getInstance().getConnection().prepareStatement(query);
        pst.setString(1, login);
        pst.setString(2, password);
        res = pst.executeQuery();
        while(res.next()) {
            User.setId(res.getInt("member_id"));
            User.setRole(res.getInt("role"));
        }
    }
    public void registration(String first_name, String second_name, String last_name, String phone, String login, String password, String address) throws SQLException, ClassNotFoundException {
        String request = "INSERT INTO people (first_name, second_name, last_name, phone) VALUES (?,?,?,?)";

        DataBaseHandler db = DataBaseHandler.getInstance();
        PreparedStatement pst = db.getConnection().prepareStatement(request);
        pst.setString(1, first_name);
        pst.setString(2, second_name);
        pst.setString(3, last_name);
        pst.setString(4, phone);
        pst.executeUpdate();

        Statement statement = db.getConnection().createStatement();
        int value = 0;
        request = "SELECT human_id FROM people ORDER BY human_id DESC LIMIT 0, 1";
        ResultSet result = statement.executeQuery(request);
        while(result.next()){
            value = result.getInt(1);
        }

        request = "INSERT INTO members (login, password, human_id) VALUES (?,?,?)";
        pst = db.getConnection().prepareStatement(request);
        pst.setString(1, login);
        pst.setString(2, String.valueOf(password.hashCode()));
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
        pst.setString(1, address);
        pst.setInt(2, value);
        pst.executeUpdate();
    }
    public void customer() throws SQLException, ClassNotFoundException {
        String request = "SELECT first_name, second_name, last_name, " +
                "phone, address, login, password, dc_id, customer_id  FROM people JOIN members USING(human_id) JOIN customers USING(member_id) " +
                "WHERE member_id="+Customer.getId();
        PreparedStatement pst = getInstance().getConnection().prepareStatement(request);
        ResultSet res = pst.executeQuery();
        while(res.next()){
            Customer.setFirst_name(res.getString(1));
            Customer.setSecond_name(res.getString(2));
            Customer.setLast_name(res.getString(3));
            Customer.setPhone(res.getString(4));
            Customer.setAddress(res.getString(5));
            Customer.setLogin(res.getString(6));
            Customer.setPassword(res.getInt(7));
            Customer.setDc_id(res.getInt(8));
            Customer.setCust_id(res.getInt(9));
        }
    }
    public ResultSet client_account_w() throws SQLException, ClassNotFoundException {
        String query = "SELECT package_id, weight, IF(urgency=1, 'Срочная', 'Обычная') AS urg, CONCAT(first_name, ' ', second_name) AS name, phone FROM people JOIN " +
                "members USING(human_id) " +
                "JOIN couriers USING(member_id) " +
                "JOIN packages USING(courier_id) " +
                "JOIN recipient_cust USING(package_id) " +
                "WHERE recipient_id=? AND status=0;";
        PreparedStatement pst = getInstance().getConnection().prepareStatement(query);
        pst.setInt(1, Customer.getCust_id());
        return pst.executeQuery();
    }
    public ResultSet client_account_w1() throws SQLException, ClassNotFoundException {
        String query = "SELECT packages.package_id AS id, weight, IF(urgency=1, 'Срочная', 'Обычная') AS urg, CONCAT(first_name, ' ', second_name) AS name, IF(status=1, 'да', 'нет') AS status1 FROM people JOIN " +
                "members USING(human_id) JOIN customers USING(member_id) JOIN recipient_cust ON customer_id=recipient_id JOIN packages USING(package_id) JOIN sender_cust ON packages.package_id=sender_cust.package_id " +
                "WHERE sender_id=?";
        PreparedStatement pst = getInstance().getConnection().prepareStatement(query);
        pst.setInt(1, Customer.getCust_id());
        return pst.executeQuery();
    }
    public void package_registartion(String first_name, String second_name, String phone, boolean selected, int weight) throws SQLException, ClassNotFoundException {
        String request;
        int id_dc = 0;
        int id_cust = 0;
        //res = null;
        request = "SELECT customer_id, dc_id FROM people JOIN members USING(human_id) " +
                "JOIN customers ON members.member_id=customers.member_id AND role=2 " +
                "WHERE first_name=? AND second_name=? AND phone=? AND dc_id IS NOT NULL";
        PreparedStatement pst = getConnection().prepareStatement(request);
        pst.setString(1, first_name);
        pst.setString(2, second_name);
        pst.setString(3, phone);
        ResultSet res = pst.executeQuery();
        while (res.next()){
            id_cust = res.getInt("customer_id");
            id_dc = res.getInt("dc_id");
        }
        if(id_cust==0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Регистрация посылки");
            alert.setHeaderText("регистрация посылки невозможна");
            alert.setContentText("Введенный пользователь не имеет подтвержденной учетной записи.");
            alert.showAndWait();                }
        else {
            request = "SELECT couriers.courier_id FROM couriers " +
                    "LEFT JOIN packages USING(courier_id) WHERE dc_id=? GROUP BY couriers.courier_id " +
                    "HAVING COUNT(*)!=0 AND SUM(packages.urgency)=" +
                    "(SELECT MIN(A) FROM " +
                    "(SELECT SUM(urgency) AS A FROM packages AS p " +
                    "JOIN couriers AS c USING(courier_id) " +
                    "JOIN delivery_centrers AS dc USING(dc_id) " +
                    "WHERE dc.dc_id=? AND p.status=0 GROUP BY c.courier_id) AS B)" +
                    " OR COUNT(package_id)=0 LIMIT 1";
            pst = getConnection().prepareStatement(request);
            pst.setInt(1, id_dc);
            pst.setInt(2, id_dc);
            ResultSet result = pst.executeQuery();
            int id_courier = 0;
            while (result.next()) {
                id_courier = result.getInt(1);
            }

            request = "INSERT INTO packages (weight, urgency, courier_id) VALUES (?,?,?)";
            pst = getConnection().prepareStatement(request);
            pst.setInt(1, weight);
            int ur = 1;
            if (selected)
                ur = 2;
            pst.setInt(2, ur);
            pst.setInt(3, id_courier);
            pst.executeUpdate();

            Statement statement = getConnection().createStatement();
            int id_package = 0;
            request = "SELECT package_id FROM packages ORDER BY package_id DESC LIMIT 0, 1";
            result = statement.executeQuery(request);
            while (result.next()) {
                id_package = result.getInt(1);
            }


            request = "INSERT INTO sender_cust (sender_id, package_id) VALUES (?,?)";
            pst = getConnection().prepareStatement(request);
            pst.setInt(1, Customer.getCust_id());
            pst.setInt(2, id_package);
            pst.executeUpdate();

            request = "INSERT INTO recipient_cust (recipient_id, package_id) VALUES (?,?)";
            pst = getConnection().prepareStatement(request);
            pst.setInt(1, id_cust);
            pst.setInt(2, id_package);
            pst.executeUpdate();

            request = "INSERT INTO sender_dc (package_id) VALUES (?)";
            pst = getConnection().prepareStatement(request);
            pst.setInt(1, id_package);
            pst.executeUpdate();

            request = "INSERT INTO recipient_dc (recipient_dc_id, package_id) VALUES (?,?)";
            pst = getConnection().prepareStatement(request);
            pst.setInt(1, id_dc);
            pst.setInt(2, id_package);
            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("id посылки");
            alert.setHeaderText("Сгенерированный id посылки: " + id_package);
            alert.setContentText("Также вы сможете узнать его через личный кабинет в разделе 'Отправленные посылки'");
            alert.showAndWait();


        }


    }
    public boolean update_data(String new_first_name, String new_second_name, String new_last_name, String new_phone, String new_address, int new_password){
        String request1 = "SELECT IF(COUNT(packages.package_id)!=0, 1, 0) AS a FROM recipient_cust JOIN packages USING(package_id) " +
                "WHERE status=0 AND recipient_id="+Customer.getCust_id();
        //request = "INSERT INTO people (first_name, second_name, last_name, phone) VALUES (?,?,?,?)";
        try {
            PreparedStatement pst1 = getConnection().prepareStatement(request1);
            ResultSet res1 = pst1.executeQuery();
            boolean flag = false; //нельзя менять адрес
            String first_name, second_name, last_name, phone, address;
            int password;
            first_name = Customer.getFirst_name();
            second_name = Customer.getSecond_name();
            last_name = Customer.getLast_name();
            phone = Customer.getPhone();
            address = Customer.getAddress();
            password = Customer.getPassword();
            while (res1.next()) {
                if (res1.getInt(1) == 0) flag = true; //можно менять адрес
            }


            if (!flag && !new_address.equals(address) && !new_address.equals("")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ошибка адреса");
                alert.setHeaderText("Изменить адрес нельзя");
                alert.setContentText("Ожидаются посылки");
                alert.showAndWait();
                return false;
            }
            else {
                if (!first_name.equals(new_first_name) && !new_first_name.equals("")) {
                    first_name = new_first_name;
                    Customer.setFirst_name(first_name);
                }
                if (!second_name.equals(new_second_name) && !new_second_name.equals("")) {
                    second_name = new_second_name;
                    Customer.setSecond_name(second_name);
                }
                if (!last_name.equals(new_last_name) && !new_last_name.equals("")) {
                    last_name = new_last_name;
                    Customer.setLast_name(last_name);
                }
                if (!phone.equals(new_phone) && !new_phone.equals("")) {
                    phone = new_phone;
                    Customer.setPhone(phone);
                }
                if ((!address.equals(new_address) && !new_address.equals("")) && flag) {
                    address = new_address;
                    Customer.setAddress(address);
                    Customer.setDc_id(4);
                }
                if (password != new_password && new_password != 0) {
                    password = new_password;
                    Customer.setPassword(password);
                }


                request1 = "UPDATE people JOIN members USING(human_id) " +
                        "SET first_name = ?, second_name = ?, last_name = ?, phone = ?" +
                        "WHERE member_id=?";
                pst1 = getConnection().prepareStatement(request1);
                pst1.setString(1, first_name);
                pst1.setString(2, second_name);
                pst1.setString(3, last_name);
                pst1.setString(4, phone);
                pst1.setInt(5, Customer.getId());
                pst1.executeUpdate();

                request1 = "UPDATE members " +
                        "SET password=? WHERE member_id=?";
                pst1 = getConnection().prepareStatement(request1);
                pst1.setString(1, String.valueOf(password));
                pst1.setInt(2, Customer.getId());
                pst1.executeUpdate();

                request1 = "UPDATE customers " +
                        "SET address=? , dc_id=? WHERE customer_id=?";
                pst1 = getConnection().prepareStatement(request1);
                pst1.setString(1, address);
                pst1.setInt(2, Customer.getDc_id());
                pst1.setInt(3, Customer.getCust_id());
                pst1.executeUpdate();
                return true;
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

