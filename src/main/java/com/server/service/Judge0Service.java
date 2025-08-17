package com.server.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Judge0Service {

	@Value("${judge0.api.key}")
	private String apiKey;

	@Value("${judge0.api.host}")
	private String apiHost;

	@Value("${judge0.api.url}")
	private String apiUrl;

	private final RestTemplate rest = new RestTemplate();

	public String execute(String sourceCode, Integer languageId, String stdin) {
		String url = apiUrl + "/submissions?base64_encoded=false&wait=true";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("x-rapidapi-key", apiKey);
		headers.set("x-rapidapi-host", apiHost);

		Map<String, Object> body = new HashMap<>();
		body.put("source_code", sourceCode);
		body.put("language_id", languageId);
		if (stdin != null && !stdin.isBlank())
			body.put("stdin", stdin);

		HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

		ResponseEntity<String> response = rest.postForEntity(url, entity, String.class);

		return response.getBody();
	}
}