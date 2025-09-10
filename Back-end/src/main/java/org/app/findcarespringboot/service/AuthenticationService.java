package org.app.findcarespringboot.service;


import org.app.findcarespringboot.controller.AuthenticationController;
import org.app.findcarespringboot.dto.response.ApiResponseDto;
import org.app.findcarespringboot.entity.User;

import java.util.Map;

public interface AuthenticationService {
    User saveUser(User user);
    Map<String, String> loginUser(User user);
    User findById(String i);
    ApiResponseDto refreshToken(AuthenticationController.RefreshRequest request);
}
