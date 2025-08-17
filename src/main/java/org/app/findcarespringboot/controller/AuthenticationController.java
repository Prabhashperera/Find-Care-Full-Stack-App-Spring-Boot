package org.app.findcarespringboot.controller;

import lombok.RequiredArgsConstructor;
import org.app.findcarespringboot.dto.UserDto;
import org.app.findcarespringboot.dto.response.ApiResponseDto;
import org.app.findcarespringboot.entity.User;
import org.app.findcarespringboot.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("signup")
    public ResponseEntity<ApiResponseDto> userRegister(@RequestBody UserDto user) {
        User savedUser = authenticationService.saveUser(new User(user.getUsername(), user.getPassword()));
        if (savedUser != null) {
            return ResponseEntity.ok(
                    new ApiResponseDto(200, "Register Success", null)
            );
        }
        return ResponseEntity.status(400).body(
                new ApiResponseDto(400, "Registration failed", null)
        );
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponseDto> userLogin(@RequestBody UserDto user) {
        String token = authenticationService.loginUser(new User(user.getUsername(), user.getPassword()));
        return ResponseEntity.ok(
                new ApiResponseDto(200, "Login Success", token) //Standard Api Response
        );
    }
}
