package com.example.demo2;

public class Package_courier {
    private int package_id;
    private int weight;
    private String urgency;
    private String name;
    private String phone;
    private String address;

    public Package_courier(int package_id, int weight, String urgency, String name, String phone, String address) {
        this.package_id = package_id;
        this.weight = weight;
        this.urgency = urgency;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }
    public Package_courier(){}

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
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}

