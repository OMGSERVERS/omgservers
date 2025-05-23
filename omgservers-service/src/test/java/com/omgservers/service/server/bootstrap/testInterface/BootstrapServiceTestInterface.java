package com.omgservers.service.server.bootstrap.testInterface;

import com.omgservers.service.server.bootstrap.BootstrapService;
import com.omgservers.service.server.bootstrap.dto.BootstrapDefaultPoolRequest;
import com.omgservers.service.server.bootstrap.dto.BootstrapDefaultPoolResponse;
import com.omgservers.service.server.bootstrap.dto.BootstrapDefaultUserRequest;
import com.omgservers.service.server.bootstrap.dto.BootstrapDefaultUserResponse;
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

    public BootstrapDefaultUserResponse execute(final BootstrapDefaultUserRequest request) {
        return bootstrapService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public BootstrapDefaultPoolResponse bootstrapDefaultPool(final BootstrapDefaultPoolRequest request) {
        return bootstrapService.execute(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
