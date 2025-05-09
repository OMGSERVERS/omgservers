package com.omgservers.service.server.initializer.testInterface;

import com.omgservers.service.server.initializer.InitializerService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class InitializerServiceTestInterface {
    private static final long TIMEOUT = 1L;

    final InitializerService initializerService;

    public void initialize() {
        initializerService.initialize()
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
