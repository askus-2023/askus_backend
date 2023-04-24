package com.askus.askus.testsupport;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.utility.DockerImageName;

import com.redis.testcontainers.RedisContainer;

public abstract class BaseTestContainerTest {
	private static RedisContainer redisContainer;

	@BeforeAll
	static void beforeAll() {
		DockerImageName redisImageName = DockerImageName.parse("redis:6-alpine");
		redisContainer = new RedisContainer(redisImageName)
			.withExposedPorts(6379)
			.withReuse(true);
		redisContainer.start();
	}

	@DynamicPropertySource
	private static void registeredRedisProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.redis.host", redisContainer::getHost);
		registry.add("spring.redis.port", () -> redisContainer.getMappedPort(6379));
	}
}
