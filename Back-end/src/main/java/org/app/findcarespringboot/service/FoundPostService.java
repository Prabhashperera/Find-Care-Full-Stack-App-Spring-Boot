package org.app.findcarespringboot.service;

import org.app.findcarespringboot.dto.FilterPostsDto;
import org.app.findcarespringboot.dto.FoundPostDto;
import org.app.findcarespringboot.entity.FoundPost;

import java.util.List;

public interface FoundPostService {
    FoundPost save(FoundPost foundPost);
    FoundPost findPostById(int postID);
    String extractPublicIdFromCloudinary(String url);
    boolean delete(String postID);
    List<FoundPostDto> getAll();
    List<FoundPostDto> filterPosts(FilterPostsDto filterDto);
    List<FoundPostDto> loadPostsByUser(String userName);
    boolean changeStatus(int status, String s);
}
