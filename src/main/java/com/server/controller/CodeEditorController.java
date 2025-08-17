package com.server.controller;

import com.server.model.CodeMessage;
import com.server.service.CodeMessageService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CodeEditorController {
    

	private CodeMessageService codeMessageService;
	private final SimpMessagingTemplate messagingTemplate;
	
	public CodeEditorController(CodeMessageService codeMessageService, SimpMessagingTemplate messagingTemplate) {
		this.codeMessageService = codeMessageService;
		this.messagingTemplate = messagingTemplate;
	}
	
    @MessageMapping("/getLatestCode/{roomId}")
    @SendTo("/topic/{roomId}")
    public CodeMessage getLatestCode(@DestinationVariable String roomId) {
        return codeMessageService.getLatestCode(roomId);
    }

    @MessageMapping("/editor/{roomId}")
    public void handleCodeUpdate(@DestinationVariable String roomId, CodeMessage msg) {
        String type = msg.getType() == null ? "CODE_UPDATE" : msg.getType();

        switch (type) {
            case "CODE_UPDATE":
                // persist & broadcast
                codeMessageService.saveOrUpdateCode(roomId, msg.getCode(), msg.getLanguage());
                messagingTemplate.convertAndSend("/topic/" + roomId, msg);
                break;

            case "LANG_CHANGE":
                // update only language
                codeMessageService.updateLanguage(roomId, msg.getLanguage());
                messagingTemplate.convertAndSend("/topic/" + roomId, msg);
                break;

            case "REQUEST_FULL":
                CodeMessage latest = codeMessageService.getLatestCode(roomId);
                if (latest == null) latest = new CodeMessage();
                latest.setType("FULL_SYNC");
                messagingTemplate.convertAndSend("/topic/" + roomId, latest);
                break;

            default:
                // ignore unknown types
        }
    }
}
