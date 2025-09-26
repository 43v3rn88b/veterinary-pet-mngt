package com.codecraft.veterinary_pet_system.controller;

import com.codecraft.veterinary_pet_system.JwtUtil;
import com.codecraft.veterinary_pet_system.dto.LoginRequest;
import com.codecraft.veterinary_pet_system.dto.LoginResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            String token = jwtUtil.generateToken(auth.getName());

//            HttpHeaders headers = new HttpHeaders();
//            headers.setCacheControl("no-store"); // 🚀 prevent caching

            return ResponseEntity.ok()
                    .header(HttpHeaders.CACHE_CONTROL, "no-store, no-cache, must-revalidate")
                    .header("Pragma", "no-cache") // for HTTP/1.0
                    .header("Expires", "0")       // expire immediately
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new LoginResponse(token));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("❌ Invalid username or password");
        }
    }
}

