package org.app.findcarespringboot.service.impl;

import lombok.RequiredArgsConstructor;
import org.app.findcarespringboot.dto.FilterPostsDto;
import org.app.findcarespringboot.dto.FoundPostDto;
import org.app.findcarespringboot.entity.FoundPost;
import org.app.findcarespringboot.exception.InternalServerErrorException;
import org.app.findcarespringboot.repo.FoundPostRepo;
import org.app.findcarespringboot.service.FoundPostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FoundPostServiceImpl implements FoundPostService {
    private final FoundPostRepo foundPostRepo;

    @Override
    public FoundPost save(FoundPost foundPost) {
        try {
            return foundPostRepo.save(foundPost);
        } catch (Exception e) {
            throw new InternalServerErrorException("Error saving found post");
        }
    }

    @Override
    public FoundPost findPostById(int postID) {
        List<FoundPost> foundPostByPostID = foundPostRepo.getFoundPostByPostID(postID);
        if (!foundPostByPostID.isEmpty()) {
            return foundPostByPostID.get(0);
        }
        throw new NullPointerException("Post not found");
    }

//    Customer Method for Find the public id from exist uploaded url
    public String extractPublicIdFromCloudinary(String url) {
        String path = url.substring(url.indexOf("/upload/") + 8); // remove prefix
        if (path.contains("/v")) {
            path = path.substring(path.indexOf("/") + 1); // remove version
        }
        path = path.substring(0, path.lastIndexOf(".")); // remove extension
        System.out.println(path);
        return path; // just the public_id
    }

    @Override
    public boolean delete(String postID) {
        Optional<FoundPost> byId = foundPostRepo.findById(Integer.valueOf(postID));
        if (byId.isPresent()) {
            foundPostRepo.delete(byId.get());
            return true;
        }
        throw new NullPointerException("Post not found");
    }
    @Override
    public List<FoundPostDto> getAll() {
        return foundPostRepo.findAll()
                .stream()
                .map(foundPost -> new FoundPostDto(
                        foundPost.getPostID(),
                        foundPost.getUser() != null ? foundPost.getUser().getUsername() : null,
                        foundPost.getPostDescription(),
                        foundPost.getPetType(),
                        foundPost.getBreed(),
                        foundPost.getColor(),
                        foundPost.getGender(),
                        foundPost.getPhotoUrl(),
                        foundPost.getDistrict(),
                        foundPost.getCity(),
                        foundPost.getLandmark(),
                        foundPost.getFinderName(),
                        foundPost.getContactNumber(),
                        foundPost.getPostDate(),
                        foundPost.getStatus()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<FoundPostDto> filterPosts(FilterPostsDto dto) {
        List<FoundPost> foundPosts = foundPostRepo.filterPosts(
                dto.getPetType(), dto.getStatus(), dto.getDistrict(), dto.getCity()
        );
        List<FoundPostDto> filteredPosts = new ArrayList<>();
        for (FoundPost foundPost : foundPosts) {
            FoundPostDto foundPostDto = new FoundPostDto(
                    foundPost.getPostID(),
                    foundPost.getUser() != null ? foundPost.getUser().getUsername() : null,
                    foundPost.getPostDescription(),
                    foundPost.getPetType(),
                    foundPost.getBreed(),
                    foundPost.getColor(),
                    foundPost.getGender(),
                    foundPost.getPhotoUrl(),
                    foundPost.getDistrict(),
                    foundPost.getCity(),
                    foundPost.getLandmark(),
                    foundPost.getFinderName(),
                    foundPost.getContactNumber(),
                    foundPost.getPostDate(),
                    foundPost.getStatus()
            );
            filteredPosts.add(foundPostDto);
        }
        return filteredPosts;
    }
}
