package com.example.iot.service.controller;

import com.example.iot.service.service.auth.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PingController {

    private final TokenService tokenService;

    @GetMapping("/api/ping")
    public String ping() {
        return tokenService.getToken();
    }
}