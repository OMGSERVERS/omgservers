package com.omgservers.service.service.bootstrap.testInterface;

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

    public void bootstrapServerIndex() {
        bootstrapService.bootstrapServerIndex()
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public void bootstrapServiceRoot() {
        bootstrapService.bootstrapServiceRoot()
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public void bootstrapAdminUser() {
        bootstrapService.bootstrapAdminUser()
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public void bootstrapSupportUser() {
        bootstrapService.bootstrapSupportUser()
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public void bootstrapRegistryUser() {
        bootstrapService.bootstrapRegistryUser()
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public void bootstrapBuilderUser() {
        bootstrapService.bootstrapBuilderUser()
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public void bootstrapServiceUser() {
        bootstrapService.bootstrapServiceUser()
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public void bootstrapDefaultPool() {
        bootstrapService.bootstrapDefaultPool()
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
