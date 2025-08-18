package org.app.findcarespringboot.service;

import org.app.findcarespringboot.entity.FoundPost;

public interface FoundPostService {
    FoundPost save(FoundPost foundPost);
    FoundPost findPostById(int postID);
    String extractPublicIdFromCloudinary(String url);
}
