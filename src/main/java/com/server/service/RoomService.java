package com.server.service;

import org.springframework.stereotype.Service;

@Service
public interface RoomService {
    void register(String name, String password);
    void authenticate(String name, String password);
}
