package com.example.myapplication.model;

import java.io.Serializable;

public class Item implements Serializable {
    private long id;
    private String title;
    private String description;
    private String category;
    private String type; // "LOST" or "FOUND"
    private String status; // "ACTIVE" or "RESOLVED"
    private String date;
    private String location;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private String imageUri;
    private String postedBy;

    public Item() {
    }

    public Item(long id, String title, String description, String category, String type,
                String status, String date, String location, String contactName,
                String contactPhone, String contactEmail, String imageUri, String postedBy) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.type = type;
        this.status = status;
        this.date = date;
        this.location = location;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.imageUri = imageUri;
        this.postedBy = postedBy;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }

    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }

    public String getImageUri() { return imageUri; }
    public void setImageUri(String imageUri) { this.imageUri = imageUri; }

    public String getPostedBy() { return postedBy; }
    public void setPostedBy(String postedBy) { this.postedBy = postedBy; }
}