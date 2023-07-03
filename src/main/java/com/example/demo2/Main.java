package com.example.demo2;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://std-mysql.ist.mospolytech.ru:3306/std_2295_curse",
                "std_2295_curse", "Vfrfhjdf1");
        Statement statement = connection.createStatement();
        String query = " SELECT * FROM members " ;
        ResultSet result = statement.executeQuery(query);
        while(result.next()){
            int member_id = result.getInt("member_id" );
            String login = result.getString("login" );
            String password = result.getString("password" );
            int role = result.getInt("role");
            System.out.print("Member: " );
            System.out.print(" id = " + member_id);
            System.out.print(" , login = \" " + login + " \" " );
            System.out.print(" , password = \" " + password + " \" . " );
            System.out.println(" , role = \" " + role + " \" . " );
        }
    }
}
