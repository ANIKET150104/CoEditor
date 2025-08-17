package com.server.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.time.LocalDateTime;

@Entity
@Table(name = "codeMessages")
public class CodeMessage {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String roomId;
    
    @Lob
    private String code;
    
    @Transient
    private String sender;
    
    @Transient
    private String type;
    
    private String language;
    
    private LocalDateTime lastUpdated;

    public CodeMessage(Long id, String roomId, String sender, String code, String type, String language, LocalDateTime lastUpdated) {
        this.id = id;
        this.roomId = roomId;
        this.sender = sender;
        this.code = code;
        this.type = type;
        this.language = language;
        this.lastUpdated = lastUpdated;
    }

    public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public CodeMessage() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setUsername(String sender) {
        this.sender = sender;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
