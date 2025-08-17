package com.server.service;

import com.server.repo.RoomRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final RoomRepository roomRepository;

    public UserDetailsServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        var room = roomRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("Room not found with room name: " + name));

        return org.springframework.security.core.userdetails.User.builder()
                .username(room.getName())
                .password(room.getPassword())
                .build();
    }
}
