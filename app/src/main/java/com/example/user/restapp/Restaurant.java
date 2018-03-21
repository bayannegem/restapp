package com.example.user.restapp;

import android.media.Image;

/**
 * Created by USER on 07/03/2018.
 */

public class Restaurant {

    private String name;
    private String description;
    private String phone;
    private String website;
    private String faceebook;
    private String whatsapp;
    private String location;
    private String Image;


    public Restaurant() {
    }

    public Restaurant(String name, String description, String phone, String website, String faceebook, String whatsapp, String location ,String Image) {
        this.name = name;
        this.description = description;
        this.phone = phone;
        this.website = website;
        this.faceebook = faceebook;
        this.whatsapp = whatsapp;
        this.location = location;
        this.Image = Image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getFaceebook() {
        return faceebook;
    }

    public void setFaceebook(String faceebook) {
        this.faceebook = faceebook;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String location) {
        this.Image = Image;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", phone='" + phone + '\'' +
                ", website='" + website + '\'' +
                ", faceebook='" + faceebook + '\'' +
                ", whatsapp='" + whatsapp + '\'' +
                ", location='" + location + '\'' +
                ", Image='" + Image + '\'' +
                '}';
    }
}
