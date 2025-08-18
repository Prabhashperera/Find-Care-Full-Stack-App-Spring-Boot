package org.app.findcarespringboot.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.app.findcarespringboot.dto.FoundPostDto;
import org.app.findcarespringboot.entity.FoundPost;
import org.app.findcarespringboot.entity.User;
import org.app.findcarespringboot.service.AuthenticationService;
import org.app.findcarespringboot.service.FoundPostService;
import org.app.findcarespringboot.dto.response.ApiResponseDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("api/found")
@CrossOrigin
@RequiredArgsConstructor
public class FoundController {
    private final FoundPostService foundPostService;
    private final Cloudinary cloudinary;

    @PostMapping(path = "save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseDto> savePost(@RequestPart("dto") String dto, @RequestPart("file") MultipartFile file) throws IOException {
        String photoUrl = null;

        ObjectMapper mapper = new ObjectMapper();
        FoundPostDto foundPostDto = mapper.readValue(dto, FoundPostDto.class);

        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            photoUrl = (String) uploadResult.get("secure_url");
            System.out.println("Photo URL: " + photoUrl);
        } catch (IOException e) {
            System.err.println("Upload failed: " + e.getMessage());
            return ResponseEntity.ok(new ApiResponseDto(500, "Upload failed: " + e.getMessage(), null));
        }

        FoundPost foundPost = new FoundPost(
                foundPostDto.postDescription(),
                foundPostDto.petType(),
                foundPostDto.breed(),
                foundPostDto.color(),
                foundPostDto.gender(),
                photoUrl,
                foundPostDto.district(),
                foundPostDto.city(),
                foundPostDto.landmark(),
                foundPostDto.finderName(),
                foundPostDto.contactNumber(),
                foundPostDto.postDate(),
                foundPostDto.status(),
                foundPostDto.mobileNumber()
        );
        FoundPost savedPost = foundPostService.save(foundPost);

        if (savedPost != null) {
            return ResponseEntity.ok(new ApiResponseDto(200, "Save Success", photoUrl));
        }
        return ResponseEntity.ok(new ApiResponseDto(500, "Internal Server Error", null));
    }
}