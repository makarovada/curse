package com.example.demo2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

}
/*
public class DateBaseHandler {
    Connection dbconnection;
    private static class DataBaseHandlerHolder {
        public static final DataBaseHandler HOLDER_INSTANCE = new DataBaseHandler();
    }
    private static class ConnectionHolder {
        public static final Connection HOLDER_CONNECTION;

        static {
            try {
                HOLDER_CONNECTION = getInstance().getDbconnection();
            } catch (ClassNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Connection getInstanceConnection() {
        return ConnectionHolder.HOLDER_CONNECTION;
    }
    private Statement statement;
    public DateBaseHandler() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://std-mysql.ist.mospolytech.ru:3306/std_2295_curse",
                "std_2295_curse", "Vfrfhjdf1");
        statement = connection.createStatement();
        //connection.close();
    }

}

 */
