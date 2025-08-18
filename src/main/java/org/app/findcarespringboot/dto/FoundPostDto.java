package org.app.findcarespringboot.dto;

public record FoundPostDto(
        int postID,
        String postDescription,
        String petType,
        String breed,
        String color,
        String gender,
        String photoUrl,
        String district,
        String city,
        String landmark,
        String finderName,
        String contactNumber,
        String postDate,
        String status,
        String mobileNumber
) {}