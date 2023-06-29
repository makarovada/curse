package com.example.demo2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DateBaseHandler {
    static Statement statement;
    public DateBaseHandler() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://std-mysql.ist.mospolytech.ru:3306/std_2295_curse",
                "std_2295_curse", "Vfrfhjdf1");
        statement = connection.createStatement();
        //connection.close();
    }
}
