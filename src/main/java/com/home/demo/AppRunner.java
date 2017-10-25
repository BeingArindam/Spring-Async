package com.home.demo;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.home.demo.domain.User;
import com.home.demo.service.GitHubLookupService;

@Component
public class AppRunner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

	private final GitHubLookupService gitHubLookupService;

	@Autowired
	public AppRunner(GitHubLookupService gitHubLookupService) {
		this.gitHubLookupService = gitHubLookupService;
	}

	@Override
	public void run(String... args) throws Exception {
		long start = System.currentTimeMillis();

		CompletableFuture<User> page1 = gitHubLookupService.findUser("PivotalSoftware");
		CompletableFuture<User> page2 = gitHubLookupService.findUser("CloudFoundry");
		CompletableFuture<User> page3 = gitHubLookupService.findUser("BeingArindam");

		CompletableFuture.allOf(page1, page2, page3).join();

		logger.info("Elapsed time: " + (System.currentTimeMillis() - start));
		logger.info("--> " + page1.get());
		logger.info("--> " + page2.get());
		logger.info("--> " + page3.get());
	}

}
