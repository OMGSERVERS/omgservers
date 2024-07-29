package com.omgservers.service.server.service.testInterface;

import com.omgservers.service.server.service.initializer.InitializerService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class BootstrapServiceTestInterface {
    private static final long TIMEOUT = 1L;

    final InitializerService initializerService;

    public void bootstrap() {
        initializerService.initialize()
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
