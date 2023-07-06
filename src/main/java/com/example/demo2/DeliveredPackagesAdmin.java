package com.example.demo2;

public class DeliveredPackagesAdmin {
    private int package_id;
    private int weight;
    private String urgency;
    private String name;
    private String phone;
    private int courier_id;
    private int status;


    public int getCourier_id() {
        return courier_id;
    }

    public void setCourier_id(int courier_id) {
        this.courier_id = courier_id;
    }

    public DeliveredPackagesAdmin(int package_id, int weight, String urgency, String name, String phone, int courier_id, int status) {
        this.package_id = package_id;
        this.weight = weight;
        this.urgency = urgency;
        this.name = name;
        this.phone = phone;
        this.courier_id = courier_id;
        this.status = status;
    }
    public DeliveredPackagesAdmin(){}

    public int getPackage_id() {
        return package_id;
    }

    public int getWeight() {
        return weight;
    }

    public String getUrgency() {
        return urgency;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPackage_id(int package_id) {
        this.package_id = package_id;
    }
    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }}
