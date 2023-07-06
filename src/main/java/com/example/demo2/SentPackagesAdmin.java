package com.example.demo2;

public class SentPackagesAdmin {
    private int package_id;
    private int weight;
    private String urgency;
    private String name;
    private String phone;
    private int recipient_dc;
    private int status;

    public int getRecipient_dc() {
        return recipient_dc;
    }

    public void setRecipient_dc(int recipient_dc) {
        this.recipient_dc = recipient_dc;
    }

    public SentPackagesAdmin(int package_id, int weight, String urgency, String name, String phone, int recipient_dc, int status) {
        this.package_id = package_id;
        this.weight = weight;
        this.urgency = urgency;
        this.name = name;
        this.phone = phone;
        this.recipient_dc = recipient_dc;
        this.status = status;
    }
    public SentPackagesAdmin(){}

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
    }
}
