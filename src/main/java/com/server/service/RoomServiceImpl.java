package com.server.service;

import com.server.model.Room;
import com.server.repo.RoomRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final PasswordEncoder passwordEncoder;

    public RoomServiceImpl(RoomRepository roomRepository,
                           PasswordEncoder passwordEncoder) {
        this.roomRepository = roomRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(String name, String password) {
        if (roomRepository.findByName(name).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        var room = new Room();
        room.setName(name);
        room.setPassword(passwordEncoder.encode(password));
        roomRepository.save(room);
    }

    @Override
    public void authenticate(String username, String password) {
        var room = roomRepository.findByName(username)
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(password, room.getPassword())) {
            throw new RuntimeException("Invalid room name or password");
        }
    }
}
