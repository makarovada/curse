package com.example.demo2;

public class User {
    protected static int id = 0;
    protected static int role;
    protected static String login;
    protected static int password;
    //private static int role_id;
    protected User(){}
    protected User(int id, int role){
        this.id=id;
        this.role=role;
    }



    public static void setId(int new_id) {
        id = new_id;
    }

    public static int getId() {
        return id;
    }
    public static void setRole(int new_role){
        role = new_role;
    }
    public static int getRole() {
        return role;
    }
    public static String getLogin() {
        return login;
    }
    public static void setLogin(String new_login) {
        login = new_login;
    }
    public static void setPassword(int new_password) {
        password = new_password;
    }
    public static int getPassword() {
        return password;
    }



}
