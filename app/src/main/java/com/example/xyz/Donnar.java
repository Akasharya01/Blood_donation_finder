package com.example.xyz;

import com.google.gson.Gson;

public class Donnar {
    private String bloodgroup,email,healthissue,contact;
    private String age,name,district,datetime,token;
    private String p_name,p_age,p_contact,p_bloodgroup,p_reason,p_date,p_unit,p_token;

    // Constructors, getters, and setters


    public Donnar() {
    }

    public String getToken() {
        return token;
    }

    public String getP_token() {
        return p_token;
    }

    public void setP_token(String p_token) {
        this.p_token = p_token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHealthissue() {
        return healthissue;
    }

    public void setHealthissue(String healthissue) {
        this.healthissue = healthissue;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_age() {
        return p_age;
    }

    public void setP_age(String p_age) {
        this.p_age = p_age;
    }

    public String getP_contact() {
        return p_contact;
    }

    public void setP_contact(String p_contact) {
        this.p_contact = p_contact;
    }

    public String getP_bloodgroup() {
        return p_bloodgroup;
    }

    public void setP_bloodgroup(String p_bloodgroup) {
        this.p_bloodgroup = p_bloodgroup;
    }

    public String getP_reason() {
        return p_reason;
    }

    public void setP_reason(String p_reason) {
        this.p_reason = p_reason;
    }

    public String getP_date() {
        return p_date;
    }

    public void setP_date(String p_date) {
        this.p_date = p_date;
    }

    public String getP_unit() {
        return p_unit;
    }

    public void setP_unit(String p_unit) {
        this.p_unit = p_unit;
    }
}
