package org.app.findcarespringboot.controller;

import lombok.RequiredArgsConstructor;
import org.app.findcarespringboot.dto.FoundPostDto;
import org.app.findcarespringboot.dto.response.ApiResponseDto;
import org.app.findcarespringboot.entity.FoundPost;
import org.app.findcarespringboot.service.FoundPostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/found")
@CrossOrigin
@RequiredArgsConstructor
public class FoundController {
    private final FoundPostService foundPostService;

    @PostMapping
    public ResponseEntity<ApiResponseDto> savePost(@RequestBody FoundPostDto foundPostDto) {
        FoundPost savedPost = foundPostService.save(new FoundPost(
                foundPostDto.getPostID(),
                foundPostDto.getUser(),
                foundPostDto.getPostDescription(),
                foundPostDto.getPetType(),
                foundPostDto.getBreed(),
                foundPostDto.getColor(),
                foundPostDto.getGender(),
                foundPostDto.getPhotoUrl(),
                foundPostDto.getDistrict(),
                foundPostDto.getCity(),
                foundPostDto.getLandmark(),
                foundPostDto.getFinderName(),
                foundPostDto.getContactNumber(),
                foundPostDto.getPostDate(),
                foundPostDto.getStatus(),
                foundPostDto.getMobileNumber()
        ));
        if (savedPost != null) {
            return ResponseEntity.ok(
                    new ApiResponseDto(200, "Save Success", null)
            );
        }
        return ResponseEntity.ok(
                new ApiResponseDto(500, "Internal Server Error", null)
        );
    }
}
