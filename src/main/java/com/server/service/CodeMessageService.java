package com.server.service;


import com.server.model.CodeMessage;
import com.server.repo.CodeMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CodeMessageService {
    @Autowired
    private CodeMessageRepository codeMessageRepository;

    public CodeMessage saveOrUpdateCode(String roomId, String code, String language) {
     
        CodeMessage existingCodeMessage = codeMessageRepository.findTopByRoomIdOrderByLastUpdatedDesc(roomId).orElse(null);

        if (existingCodeMessage != null) {
            
            existingCodeMessage.setCode(code);
            existingCodeMessage.setLanguage(language);
            existingCodeMessage.setLastUpdated(LocalDateTime.now());
            return codeMessageRepository.save(existingCodeMessage);
        } else {
          
            CodeMessage newCodeMessage = new CodeMessage();
            newCodeMessage.setRoomId(roomId);
            newCodeMessage.setCode(code);
            newCodeMessage.setLanguage(language);
            newCodeMessage.setLastUpdated(LocalDateTime.now());
            return codeMessageRepository.save(newCodeMessage); 
        }
    }

    public CodeMessage getLatestCode(String roomId) {
        return codeMessageRepository.findTopByRoomIdOrderByLastUpdatedDesc(roomId).orElse(null);
    }

	public void updateLanguage(String roomId, String language) {
		CodeMessage existingCodeMessage = codeMessageRepository
                .findTopByRoomIdOrderByLastUpdatedDesc(roomId)
                .orElse(null);

        if (existingCodeMessage != null) {
            existingCodeMessage.setLanguage(language);
            existingCodeMessage.setLastUpdated(LocalDateTime.now());
            codeMessageRepository.save(existingCodeMessage);
        } else {
            CodeMessage newCodeMessage = new CodeMessage();
            newCodeMessage.setRoomId(roomId);
            newCodeMessage.setLanguage(language);
            newCodeMessage.setCode("");
            newCodeMessage.setLastUpdated(LocalDateTime.now());
            codeMessageRepository.save(newCodeMessage);
        }
		
	}


}