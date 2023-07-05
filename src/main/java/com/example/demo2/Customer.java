package com.example.demo2;

public class Customer extends User{
    private static int cust_id;
    private static String first_name;
    private static String second_name;
    private static String last_name;
    private static String phone;
    private static String address;

    private static int dc_id=0;

    private Customer(){}
    //int cust_id, String first_name, String second_name, String last_name, String phone, String address, String login, int password, int dc_id

        /*
        this.first_name = first_name;
        this.second_name = second_name;
        this.last_name = last_name;
        this.phone = phone;
        this.address = address;
        this.login = login;
        this.password = password;
        this.dc_id = dc_id;
        this.cust_id=cust_id;

         */





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
    public static String getAddress() {
        return address;
    }
    public static void setAddress(String address1) {
        address = address1;
    }

    public static void setDc_id(int dc_id1) {
        dc_id = dc_id1;
    }
    public static int getDc_id() {
        return dc_id;
    }
    public static void setCust_id(int cust_id1) {
        cust_id = cust_id1;
    }
    public static int getCust_id() {
        return cust_id;
    }


}
