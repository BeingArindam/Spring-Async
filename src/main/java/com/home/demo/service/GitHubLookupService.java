package com.home.demo.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.home.demo.domain.User;

@Service
public class GitHubLookupService {
	private static final Logger logger = LoggerFactory.getLogger(GitHubLookupService.class);

	private final RestTemplate restTemplate;

	@Autowired
	public GitHubLookupService(RestTemplateBuilder restTemplateBuilder) {
		super();
		this.restTemplate = restTemplateBuilder.build();
	}

	@Async
	public CompletableFuture<User> findUser(String user) throws InterruptedException {
		logger.info("Looking up " + user);
		String url = String.format("https://api.github.com/users/%s", user);
		User results = restTemplate.getForObject(url, User.class);
		TimeUnit.SECONDS.sleep(5);
		return CompletableFuture.completedFuture(results);
	}
}
