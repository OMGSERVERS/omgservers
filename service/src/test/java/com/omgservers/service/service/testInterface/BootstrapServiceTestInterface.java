package com.omgservers.service.service.testInterface;

import com.omgservers.service.service.bootstrap.BootstrapService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class BootstrapServiceTestInterface {
    private static final long TIMEOUT = 1L;

    final BootstrapService bootstrapService;

    public void bootstrap() {
        bootstrapService.bootstrap()
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
