package org.app.findcarespringboot.entity;

import jakarta.persistence.*;

@Entity
public class FoundPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postID;

    private String postDescription;
    private String petType;   // Dog, Cat, etc.
    private String breed;
    private String color;
    private String gender;

    private String photoUrl;  // Cloudinary image URL
    private String district;   // Colombo, Galle, Kandy
    private String city;       // Moratuwa, Dehiwala, Maharagama
    private String landmark;   // "Near bus stand", "Close to temple"

    private String finderName;
    private String contactNumber;
    private String email;

    private String postDate;
    private String mobileNumber;
    @ManyToOne
    private User user;
}
