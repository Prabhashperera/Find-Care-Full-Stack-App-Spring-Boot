package org.app.findcarespringboot.controller;

import lombok.RequiredArgsConstructor;
import org.app.findcarespringboot.dto.response.ApiResponseDto;
import org.app.findcarespringboot.entity.User;
import org.app.findcarespringboot.service.AuthenticationService;
import org.app.findcarespringboot.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtUtil jwtUtil;

    @PostMapping("login")
    public ResponseEntity<ApiResponseDto> userLogin(@RequestBody User user) {
        boolean isExist = authenticationService.loginUser(user);
        if (isExist) {
            String token = jwtUtil.generateToken(user.getUsername());
            return ResponseEntity.ok(
                    new ApiResponseDto("200", "Login Success", token) //Standard Api Response
            );
        }
        return ResponseEntity.status(401).body(
                new ApiResponseDto("401", "Invalid credentials", null)
        );
    }

    @PostMapping("signup")
    public ResponseEntity<ApiResponseDto> userRegister(@RequestBody User user) {
        User savedUser = authenticationService.saveUser(user);
        if (savedUser != null) {
            return ResponseEntity.ok(
                    new ApiResponseDto("200", "Register Success", savedUser)
            );
        }
        return ResponseEntity.status(400).body(
                new ApiResponseDto("400", "Registration failed", null)
        );
    }
}
