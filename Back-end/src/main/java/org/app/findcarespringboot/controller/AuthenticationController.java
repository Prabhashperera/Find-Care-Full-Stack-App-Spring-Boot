package org.app.findcarespringboot.controller;

import lombok.RequiredArgsConstructor;
import org.app.findcarespringboot.dto.UserDto;
import org.app.findcarespringboot.dto.response.ApiResponseDto;
import org.app.findcarespringboot.entity.User;
import org.app.findcarespringboot.service.AuthenticationService;
import org.app.findcarespringboot.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtUtil jwtUtil;

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

    @GetMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7); // remove "Bearer "
            boolean valid = jwtUtil.validateToken(token); // your JWT utility method
            if (valid) {
                return ResponseEntity.ok(Map.of("message", "Token is valid"));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Token invalid or expired"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Token invalid"));
        }
    }
}




