package com.example.demo2;

public class CourierTable {
    private int courier_id;
    private String fio;
    private String phone;
    public CourierTable(int courier_id, String fio, String phone){
        this.courier_id = courier_id;
        this.fio = fio;
        this.phone = phone;
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

    public int getCourier_id() {
        return courier_id;
    }

    public void setCourier_id(int courier_id) {
        this.courier_id = courier_id;
    }
}
