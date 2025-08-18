package org.app.findcarespringboot.service.impl;

import lombok.RequiredArgsConstructor;
import org.app.findcarespringboot.entity.FoundPost;
import org.app.findcarespringboot.exception.DataNotFoundException;
import org.app.findcarespringboot.exception.InternalServerErrorException;
import org.app.findcarespringboot.repo.FoundPostRepo;
import org.app.findcarespringboot.repo.UserRepo;
import org.app.findcarespringboot.service.FoundPostService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
