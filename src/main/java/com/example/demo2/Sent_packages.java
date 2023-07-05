package com.example.demo2;

public class Sent_packages {
    private int package_id;
    private int weight;
    private String urgency;
    private String name;
    private String status;

    public Sent_packages(int package_id, int weight, String urgency, String name, String status) {
        this.package_id = package_id;
        this.weight = weight;
        this.urgency = urgency;
        this.name = name;
        this.status = status;
    }
    public Sent_packages(){}

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

    public String getStatus() {
        return status;
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

    public void setStatus(String status) {
        this.status = status;
    }
}
