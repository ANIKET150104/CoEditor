package com.server.controller;

import com.server.model.JWTResponse;
import com.server.model.LoginRequest;
import com.server.service.JWTService;
import com.server.service.RoomService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class RoomController {

    private final RoomService roomService;
    private final JWTService jwtService;

    public RoomController(RoomService roomService, JWTService jwtService) {
        this.roomService = roomService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public JWTResponse register(@RequestBody LoginRequest req) {
        roomService.register(req.username(), req.password());
        String token = jwtService.generateToken(req.username());
        return new JWTResponse(token);
    }

    @PostMapping("/login")
    public JWTResponse login(@RequestBody LoginRequest req) {
        roomService.authenticate(req.username(), req.password());
        String token = jwtService.generateToken(req.username());
        return new JWTResponse(token);
    }
}
