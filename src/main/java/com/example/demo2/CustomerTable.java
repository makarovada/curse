package com.example.demo2;

public class CustomerTable {
    private int customer_id;
    private String fio;
    private String phone;
    private String address;
    private int dc_id;
    public CustomerTable(int customer_id, String fio, String phone, String address, int dc_id){
        this.customer_id = customer_id;
        this.fio = fio;
        this.phone = phone;
        this.address = address;
        this.dc_id = dc_id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }
    public int getDc_id() {
        return dc_id;
    }

    public void setDc_id(int dc_id) {
        this.dc_id = dc_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
