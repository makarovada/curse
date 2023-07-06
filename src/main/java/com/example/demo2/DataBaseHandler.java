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
        String query = "SELECT login FROM members WHERE login=?";
        PreparedStatement pt = getConnection().prepareStatement(query);
        pt.setString(1, login);
        ResultSet res = pt.executeQuery();
        boolean flag = true;
        while(res.next()){
            flag = false;
        }
        if(flag){
            query = "SELECT human_id FROM people WHERE phone=? AND first_name=? AND second_name=? AND last_name=?";
            PreparedStatement pt1 = getConnection().prepareStatement(query);
            pt1.setString(1, phone);
            pt1.setString(2, first_name);
            pt1.setString(3, second_name);
            pt1.setString(4, last_name);


            res = pt1.executeQuery();
            int human_id = 0;
            while(res.next()){
                human_id=res.getInt(1);
            }
            if(human_id==0){
                query = "INSERT INTO people (first_name, second_name, last_name, phone) VALUES (?,?,?,?)";

                pt1 = getConnection().prepareStatement(query);
                pt1.setString(1, first_name);
                pt1.setString(2, second_name);
                pt1.setString(3, last_name);
                pt1.setString(4, phone);
                pt1.executeUpdate();
                Statement statement = getConnection().createStatement();
                query = "SELECT human_id FROM people ORDER BY human_id DESC LIMIT 0, 1";
                ResultSet result = statement.executeQuery(query);
                while(result.next()){
                    human_id = result.getInt(1);
                }
            }
            query = "INSERT INTO members (login, password, human_id) VALUES (?,?,?)";
            pt1 = getConnection().prepareStatement(query);
            pt1.setString(1, login);
            pt1.setString(2, String.valueOf(password.hashCode()));
            pt1.setInt(3, human_id);
            pt1.executeUpdate();

            Statement statement = getConnection().createStatement();
            int member_id = 0;
            query = "SELECT member_id FROM members ORDER BY member_id DESC LIMIT 0, 1";
            res = statement.executeQuery(query);
            while(res.next()){
                member_id = res.getInt(1);
            }
            query = "INSERT INTO customers (address, member_id) VALUES (?,?)";
            pt1 = getConnection().prepareStatement(query);
            pt1.setString(1, address);
            pt1.setInt(2, member_id);
            pt1.executeUpdate();

        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Регистрация");
            alert.setHeaderText("Введите другой логин");
            alert.setContentText("Введенный логин уже занят.");
            alert.showAndWait();
        }

    }
        /*String request = "INSERT INTO people (first_name, second_name, last_name, phone) VALUES (?,?,?,?)";

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
        pst.executeUpdate();*/

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
    public void courier() throws SQLException, ClassNotFoundException {
        String request = "SELECT first_name, second_name, last_name, " +
                "phone, login, password, dc_id, courier_id  FROM people JOIN members USING(human_id) JOIN couriers USING(member_id) " +
                "WHERE member_id="+User.getId();
        PreparedStatement pst = getInstance().getConnection().prepareStatement(request);
        ResultSet res = pst.executeQuery();
        while(res.next()){
            Courier.setFirst_name(res.getString(1));
            Courier.setSecond_name(res.getString(2));
            Courier.setLast_name(res.getString(3));
            Courier.setPhone(res.getString(4));
            Courier.setLogin(res.getString(5));
            Courier.setPassword(res.getInt(6));
            Courier.setDc_id(res.getInt(7));
            Courier.setCour_id(res.getInt(8));
        }
    }
    public void admin() throws SQLException, ClassNotFoundException {
        String request = "SELECT first_name, second_name, last_name, " +
                "phone, login, password, dc_id, admin_id, dc_name  FROM people JOIN members USING(human_id) JOIN admins USING(member_id) JOIN delivery_centrers USING(dc_id) " +
                "WHERE member_id="+User.getId();
        PreparedStatement pst = getInstance().getConnection().prepareStatement(request);
        ResultSet res = pst.executeQuery();
        while(res.next()){
            Admin.setFirst_name(res.getString(1));
            Admin.setSecond_name(res.getString(2));
            Admin.setLast_name(res.getString(3));
            Admin.setPhone(res.getString(4));
            Admin.setLogin(res.getString(5));
            Admin.setPassword(res.getInt(6));
            Admin.setDc_id(res.getInt(7));
            Admin.setAdmin_id(res.getInt(8));
            Admin.setDc_name(res.getString(9));
        }
    }
    public ResultSet client_account_w() throws SQLException, ClassNotFoundException {
        String query = "SELECT package_id, weight, IF(urgency=1, 'Срочная', 'Обычная') AS urg, CONCAT(first_name, ' ', second_name) AS name, phone FROM people JOIN " +
                "members USING(human_id) " +
                "JOIN couriers USING(member_id) " +
                "JOIN packages USING(courier_id) " +
                "JOIN recipient_cust USING(package_id) " +
                "WHERE recipient_id=? AND status=0 ORDER BY packages.package_id";
        PreparedStatement pst = getInstance().getConnection().prepareStatement(query);
        pst.setInt(1, Customer.getCust_id());
        return pst.executeQuery();
    }
    public ResultSet courier_account_table() throws SQLException {
        String query = "SELECT package_id, weight, IF(urgency=1, 'Срочная', 'Обычная') AS urg, CONCAT(first_name, ' ', second_name) AS name, phone, address FROM " +
                "people JOIN members USING(human_id) JOIN customers USING(member_id) JOIN recipient_cust ON customer_id=recipient_id JOIN packages USING(package_id) " +
                "WHERE status!=3 AND courier_id=?";
        PreparedStatement pst = getConnection().prepareStatement(query);
        pst.setInt(1, Courier.getCour_id());
        return pst.executeQuery();
    }
    public ResultSet client_account_w1() throws SQLException, ClassNotFoundException {
        String query = "SELECT packages.package_id AS id, weight, IF(urgency=1, 'Срочная', 'Обычная') AS urg, CONCAT(first_name, ' ', second_name) AS name, IF(status=1, 'да', 'нет') AS status1 FROM people JOIN " +
                "members USING(human_id) JOIN customers USING(member_id) JOIN recipient_cust ON customer_id=recipient_id JOIN packages USING(package_id) JOIN sender_cust ON packages.package_id=sender_cust.package_id " +
                "WHERE sender_id=? ORDER BY packages.package_id";
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
    public void delivered_package(int id) throws SQLException {
        String query = "SELECT status FROM packages WHERE package_id="+id+" AND courier_id="+Courier.getCour_id();
        PreparedStatement pst1 = getConnection().prepareStatement(query);
        ResultSet res = pst1.executeQuery();
        boolean flag = false;
        while(res.next()){
            if(res.getInt(1)==2) flag=true;
        }
        if(flag){
            query = "UPDATE packages SET status=3 WHERE package_id=?";
            pst1 = getConnection().prepareStatement(query);
            pst1.setInt(1, id);
            pst1.executeUpdate();

        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Доставка посылки");
            alert.setHeaderText("Введен неверный id посылки");
            alert.setContentText("Введите кооректный id");
            alert.showAndWait();
        }
    }
    public ResultSet courier_table() throws SQLException, ClassNotFoundException {
        String query = "SELECT courier_id, CONCAT(first_name, ' ', second_name, ' ', last_name) AS fio, phone " +
                "FROM people JOIN members USING(human_id) JOIN couriers USING(member_id) WHERE dc_id=? ORDER BY courier_id";
        PreparedStatement pst = getInstance().getConnection().prepareStatement(query);
        pst.setInt(1, Admin.getDc_id());
        return pst.executeQuery();
    }
    public ResultSet customer_table() throws SQLException, ClassNotFoundException {
        String query = "SELECT customer_id, CONCAT(first_name, ' ', second_name, ' ', last_name) AS fio, phone, address, dc_id " +
                "FROM people JOIN members USING(human_id) JOIN customers USING(member_id) WHERE dc_id=? OR dc_id=4";
        PreparedStatement pst = getConnection().prepareStatement(query);
        pst.setInt(1, Admin.getDc_id());
        return pst.executeQuery();
    }
    public ResultSet sent_packages_admin() throws SQLException {
        String query = "SELECT packages.package_id, weight, IF(urgency=0, 'Обычная', 'Срочная') AS urg, " +
                "CONCAT(first_name, ' ', second_name) AS name, phone, sender_dc_id, status " +
                "FROM people JOIN members USING(human_id) JOIN customers USING(member_id) JOIN sender_cust ON customer_id=sender_id " +
                "JOIN packages USING(package_id) JOIN sender_dc ON packages.package_id=sender_dc.package_id " +
                "WHERE sender_dc_id=4 OR sender_dc_id=?";
        PreparedStatement pst = getConnection().prepareStatement(query);
        pst.setInt(1, Admin.getDc_id());
        return pst.executeQuery();
    }
    public ResultSet delivered_packages_admin() throws SQLException {
        String query = "SELECT packages.package_id, weight, IF(urgency=0, 'Обычная', 'Срочная') AS urg, " +
                "CONCAT(first_name, ' ', second_name) AS name, phone, packages.courier_id, status " +
                "FROM people JOIN members USING(human_id) JOIN customers USING(member_id) JOIN recipient_cust ON customer_id=recipient_id " +
                "JOIN packages USING(package_id) JOIN recipient_dc ON packages.package_id=recipient_dc.package_id " +
                "WHERE recipient_dc_id=?";
        PreparedStatement pst = getConnection().prepareStatement(query);
        pst.setInt(1, Admin.getDc_id());
        return pst.executeQuery();
    }

    public void status_1(int id) throws SQLException {
        String query = "SELECT status FROM packages WHERE package_id="+id;
        PreparedStatement pst = getConnection().prepareStatement(query);
        ResultSet res = pst.executeQuery();
        boolean flag = true;
        while(res.next()){
            if(res.getInt(1)!=0) flag = false;
        }
        if(flag){
            query = "UPDATE packages SET status=1 WHERE package_id="+id;
            pst = getConnection().prepareStatement(query);
            pst.executeUpdate();
            query = "UPDATE sender_dc SET sender_dc_id="+Admin.getDc_id()+" WHERE package_id="+id;
            pst = getConnection().prepareStatement(query);
            pst.executeUpdate();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Посылка");
            alert.setHeaderText("Ошибка отправления посылки");
            alert.setContentText("Посылка уже отправлена или не зарегистрирована.");
            alert.showAndWait();
        }
    }
    public void attach_customer(int id) throws SQLException {
        String query = "SELECT dc_id FROM customers WHERE customer_id="+id;
        PreparedStatement pst = getConnection().prepareStatement(query);
        ResultSet res = pst.executeQuery();
        boolean flag = true;
        while(res.next()){
            if(res.getInt(1)!=4) flag = false;
        }
        if(flag){
            query = "UPDATE customers SET dc_id="+Admin.getDc_id()+" WHERE customer_id="+id;
            pst = getConnection().prepareStatement(query);
            pst.executeUpdate();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Пользователь");
            alert.setHeaderText("Ошибка определения ближайшего цд.");
            alert.setContentText("Пользователь уже прикреплен к центру доставки.");
            alert.showAndWait();
        }
    }
    public void status_2(int id) throws SQLException {
        String query = "SELECT status FROM packages WHERE package_id="+id;
        PreparedStatement pst = getConnection().prepareStatement(query);
        ResultSet res = pst.executeQuery();
        boolean flag = true;
        while(res.next()){
            if(res.getInt(1)!=1) flag = false;
        }
        if(flag){
            query = "UPDATE packages SET status=2 WHERE package_id="+id;
            pst = getConnection().prepareStatement(query);
            pst.executeUpdate();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Посылка");
            alert.setHeaderText("Ошибка получения посылки");
            alert.setContentText("Посылка не была отправлена.");
            alert.showAndWait();
        }
    }
    public void registration_courier(String first_name, String second_name, String last_name, String phone, String login, String password, int dc_id) throws SQLException, ClassNotFoundException {
        String query = "SELECT login FROM members WHERE login=?";
        PreparedStatement pt = getConnection().prepareStatement(query);
        pt.setString(1, login);
        ResultSet res = pt.executeQuery();
        boolean flag = true;
        while(res.next()){
            flag = false;
        }
        if(flag){
            query = "SELECT human_id FROM people WHERE phone=? AND first_name=? AND second_name=? AND last_name=?";
            PreparedStatement pt1 = getConnection().prepareStatement(query);
            pt1.setString(1, phone);
            pt1.setString(2, first_name);
            pt1.setString(3, second_name);
            pt1.setString(4, last_name);


            res = pt1.executeQuery();
            int human_id = 0;
            while(res.next()){
                human_id=res.getInt(1);
            }
            if(human_id==0){
                query = "INSERT INTO people (first_name, second_name, last_name, phone) VALUES (?,?,?,?)";

                pt1 = getConnection().prepareStatement(query);
                pt1.setString(1, first_name);
                pt1.setString(2, second_name);
                pt1.setString(3, last_name);
                pt1.setString(4, phone);
                pt1.executeUpdate();
                Statement statement = getConnection().createStatement();
                query = "SELECT human_id FROM people ORDER BY human_id DESC LIMIT 0, 1";
                ResultSet result = statement.executeQuery(query);
                while(result.next()){
                    human_id = result.getInt(1);
                }
            }
            query = "INSERT INTO members (login, password, human_id, role) VALUES (?,?,?,?)";
            pt1 = getConnection().prepareStatement(query);
            pt1.setString(1, login);
            pt1.setString(2, String.valueOf(password.hashCode()));
            pt1.setInt(3, human_id);
            pt1.setInt(4, 1);

            pt1.executeUpdate();

            Statement statement = getConnection().createStatement();
            int member_id = 0;
            query = "SELECT member_id FROM members ORDER BY member_id DESC LIMIT 0, 1";
            res = statement.executeQuery(query);
            while(res.next()){
                member_id = res.getInt(1);
            }
            query = "INSERT INTO couriers (dc_id, member_id) VALUES (?,?)";
            pt1 = getConnection().prepareStatement(query);
            pt1.setInt(1, dc_id);
            pt1.setInt(2, member_id);
            pt1.executeUpdate();

        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Регистрация");
            alert.setHeaderText("Введите другой логин");
            alert.setContentText("Введенный логин уже занят.");
            alert.showAndWait();
        }

    }
    public void registration_admin(String first_name, String second_name, String last_name, String phone, String login, String password, int dc_id) throws SQLException {
        String query = "SELECT login FROM members WHERE login=?";
        PreparedStatement pt = getConnection().prepareStatement(query);
        pt.setString(1, login);
        ResultSet res = pt.executeQuery();
        boolean flag = true;
        while(res.next()){
            flag = false;
        }
        if(flag){
            query = "SELECT human_id FROM people WHERE phone=? AND first_name=? AND second_name=? AND last_name=?";
            PreparedStatement pt1 = getConnection().prepareStatement(query);
            pt1.setString(1, phone);
            pt1.setString(2, first_name);
            pt1.setString(3, second_name);
            pt1.setString(4, last_name);


            res = pt1.executeQuery();
            int human_id = 0;
            while(res.next()){
                human_id=res.getInt(1);
            }
            if(human_id==0){
                query = "INSERT INTO people (first_name, second_name, last_name, phone) VALUES (?,?,?,?)";

                pt1 = getConnection().prepareStatement(query);
                pt1.setString(1, first_name);
                pt1.setString(2, second_name);
                pt1.setString(3, last_name);
                pt1.setString(4, phone);
                pt1.executeUpdate();
                Statement statement = getConnection().createStatement();
                query = "SELECT human_id FROM people ORDER BY human_id DESC LIMIT 0, 1";
                ResultSet result = statement.executeQuery(query);
                while(result.next()){
                    human_id = result.getInt(1);
                }
            }
            query = "INSERT INTO members (login, password, human_id, role) VALUES (?,?,?,?)";
            pt1 = getConnection().prepareStatement(query);
            pt1.setString(1, login);
            pt1.setString(2, String.valueOf(password.hashCode()));
            pt1.setInt(3, human_id);
            pt1.setInt(4, 0);
            pt1.executeUpdate();

            Statement statement = getConnection().createStatement();
            int member_id = 0;
            query = "SELECT member_id FROM members ORDER BY member_id DESC LIMIT 0, 1";
            res = statement.executeQuery(query);
            while(res.next()){
                member_id = res.getInt(1);
            }
            query = "INSERT INTO admins (dc_id, member_id) VALUES (?,?)";
            pt1 = getConnection().prepareStatement(query);
            pt1.setInt(1, dc_id);
            pt1.setInt(2, member_id);
            pt1.executeUpdate();

        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Регистрация");
            alert.setHeaderText("Введите другой логин");
            alert.setContentText("Введенный логин уже занят.");
            alert.showAndWait();
        }


    }
    public void registration_dc(String name, String address) throws SQLException {
        String query = "SELECT dc_name FROM delivery_centrers WHERE dc_name=?";
        PreparedStatement pt = getConnection().prepareStatement(query);
        pt.setString(1, name);
        ResultSet res = pt.executeQuery();
        boolean flag = true;
        while(res.next()){
            flag = false;
        }
        if(flag){
            query = "INSERT INTO delivery_centrers (dc_name, address) VALUES (?,?)";

            pt = getConnection().prepareStatement(query);
            pt.setString(1, name);
            pt.setString(2, address);
            pt.executeUpdate();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Регистрация");
            alert.setHeaderText("Введите другое название");
            alert.setContentText("Введенное название уже занято.");
            alert.showAndWait();
        }
    }
    public boolean update_courier(String new_first_name, String new_second_name, String new_last_name, String new_phone, int new_dc_id, int new_password, int id){
        String request1 = "SELECT IF(COUNT(package_id)!=0, 1, 0) AS a FROM packages " +
                "WHERE courier_id="+id;
        try {
            PreparedStatement pst1 = getConnection().prepareStatement(request1);
            ResultSet res1 = pst1.executeQuery();
            boolean flag = false; //нельзя менять место работы
            while (res1.next()) {
                if (res1.getInt(1) == 0) flag = true; //можно менять место работы
            }
            String first_name = ""; String second_name = ""; String last_name = ""; String phone = "";
            int dc_id=0;
            int password = 0;
            request1 = "SELECT first_name, second_name, last_name, phone, dc_id, password " +
                    "FROM people JOIN members USING(human_id) JOIN couriers USING(member_id) WHERE courier_id="+id;
            pst1 = getConnection().prepareStatement(request1);
            res1 = pst1.executeQuery();
            while(res1.next()){
                first_name = res1.getString(1);
                second_name = res1.getString(2);
                last_name = res1.getString(3);
                phone = res1.getString(4);
                dc_id = res1.getInt(5);
                password = res1.getInt(6);
            }



            if (!flag && new_dc_id!=dc_id && new_dc_id!=0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ошибка центра доставки");
                alert.setHeaderText("Изменить центр доставки нельзя");
                alert.setContentText("Ожидаются посылки");
                alert.showAndWait();
                return false;
            }
            else {
                if (!first_name.equals(new_first_name) && !new_first_name.equals("")) {
                    first_name = new_first_name;
                    Courier.setFirst_name(first_name);
                }
                if (!second_name.equals(new_second_name) && !new_second_name.equals("")) {
                    second_name = new_second_name;
                    Courier.setSecond_name(second_name);
                }
                if (!last_name.equals(new_last_name) && !new_last_name.equals("")) {
                    last_name = new_last_name;
                    Courier.setLast_name(last_name);
                }
                if (!phone.equals(new_phone) && !new_phone.equals("")) {
                    phone = new_phone;
                    Courier.setPhone(phone);
                }
                if (new_dc_id!=dc_id && new_dc_id!=0) {
                    dc_id = new_dc_id;
                    Courier.setDc_id(dc_id);
                }
                if (password != new_password && new_password != 0) {
                    password = new_password;
                    Courier.setPassword(password);
                }


                request1 = "UPDATE people JOIN members USING(human_id) JOIN couriers USING(member_id)" +
                        "SET first_name = ?, second_name = ?, last_name = ?, phone = ?" +
                        "WHERE courier_id=?";
                pst1 = getConnection().prepareStatement(request1);
                pst1.setString(1, first_name);
                pst1.setString(2, second_name);
                pst1.setString(3, last_name);
                pst1.setString(4, phone);
                pst1.setInt(5, id);
                pst1.executeUpdate();

                request1 = "UPDATE members JOIN couriers USING(member_id) " +
                        "SET password=? WHERE courier_id=?";
                pst1 = getConnection().prepareStatement(request1);
                pst1.setString(1, String.valueOf(password));
                pst1.setInt(2, id);
                pst1.executeUpdate();

                request1 = "UPDATE couriers " +
                        "SET dc_id=? WHERE courier_id=?";
                pst1 = getConnection().prepareStatement(request1);
                pst1.setInt(1, dc_id);
                pst1.setInt(2, id);
                pst1.executeUpdate();
                return true;
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

