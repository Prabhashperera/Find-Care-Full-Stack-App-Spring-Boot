package org.app.findcarespringboot.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.app.findcarespringboot.controller.AuthenticationController;
import org.app.findcarespringboot.dto.response.ApiResponseDto;
import org.app.findcarespringboot.entity.User;
import org.app.findcarespringboot.exception.DataAlreadyExistsException;
import org.app.findcarespringboot.repo.UserRepo;
import org.app.findcarespringboot.service.AuthenticationService;
import org.app.findcarespringboot.util.JwtUtil;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public User saveUser(User user) {
        boolean isExist = userRepo.existsUserByUsername(user.getUsername()); //check if there is Exist user
        if (!isExist) {
            User toSave = new User(); //new Object for Hashed Password and Plain Username
            toSave.setUsername(user.getUsername());
            toSave.setPassword(passwordEncoder.encode(user.getPassword())); //Password Converting to the hashed Version
            return userRepo.save(toSave);
        }
        throw new DataAlreadyExistsException("User with username " + user.getUsername() + " already exists");
    }

    public User findById(String id) {
        return userRepo.findById(String.valueOf(id)).orElse(null);
    }

    @Override
    public Map<String, String> loginUser(User user) {
        User foundUser = userRepo.findByUsername(user.getUsername());
        if (foundUser != null) {
            boolean matches = passwordEncoder.matches(user.getPassword(), foundUser.getPassword());
            if (matches) {
                String accessToken = jwtUtil.generateToken(user.getUsername(), 1000L * 60); // set this LABELLLLL FIX THIS
                log.info("Generated Access Token : {}", accessToken);
                String refreshToken = jwtUtil.generateToken(user.getUsername(), 1000L * 60 * 60 * 24 * 7); // 7 days
                return Map.of("accessToken", accessToken , "refreshToken" , refreshToken);//Generating JWT Token
            }
        }
        throw new BadCredentialsException("Invalid username or password");
    }

    @Override
    public ApiResponseDto refreshToken(AuthenticationController.RefreshRequest request) {

        if (!jwtUtil.validateToken(request.refreshToken())) {
            return new ApiResponseDto(401, "Invalid or expired refresh token", null);
        }

        String username = jwtUtil.extractUsername(request.refreshToken());
        String newAccessToken = jwtUtil.generateToken(username, 1000L * 60); // 1 min

        log.info("Generated new Access Token : {}", newAccessToken);
        return new ApiResponseDto(200, "Refresh token generated", newAccessToken);
    }

}
