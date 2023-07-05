package com.example.demo2;

public class Courier extends User{
    private static int cust_id;
    private static String first_name;
    private static String second_name;
    private static String last_name;
    private static String phone;
    private static int dc_id;

    private Courier(){}

    public static String getFirst_name() {
        return first_name;
    }
    public static void setFirst_name(String first_name1) {
        first_name = first_name1;
    }
    public static String getSecond_name() {
        return second_name;
    }

    public static void setSecond_name(String second_name1) {
        second_name = second_name1;
    }

    public static String getLast_name() {
        return last_name;
    }

    public static void setLast_name(String last_name1) {
        last_name = last_name1;
    }



    public static String getPhone() {
        return phone;
    }

    public static void setPhone(String phone1) {
        phone = phone1;
    }


    public static void setDc_id(int dc_id1) {
        dc_id = dc_id1;
    }
    public static int getDc_id() {
        return dc_id;
    }
    public static void setCour_id(int cust_id1) {
        cust_id = cust_id1;
    }
    public static int getCour_id() {
        return cust_id;
    }


}

