package com.example.demo2;

public class User {
    private static int id = 0;
    private static int role;

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
}
