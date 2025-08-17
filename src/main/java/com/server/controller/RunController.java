package com.server.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.server.service.Judge0Service;

@RestController
@RequestMapping("/run")
public class RunController {
	
	private final Judge0Service judge0;

	public RunController(Judge0Service judge0) {
		this.judge0 = judge0;
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<String> runForm(@RequestParam("code") String code,
			@RequestParam("languageId") Integer languageId,
			@RequestParam(value = "stdin", required = false) String stdin,
			@RequestParam(value = HttpHeaders.ACCEPT, required = false) String accept) {
		
		String resultJson = judge0.execute(code, languageId, stdin);
		
		if(accept != null && accept.contains(MediaType.TEXT_PLAIN_VALUE)) {
			
			return ResponseEntity.ok()
					.contentType(MediaType.TEXT_PLAIN)
					.body(resultJson);
			
		}
		
		
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(resultJson);
	}

}
