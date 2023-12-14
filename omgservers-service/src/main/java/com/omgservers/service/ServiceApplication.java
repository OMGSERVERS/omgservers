package com.omgservers.service;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.Startup;
import io.quarkus.runtime.annotations.QuarkusMain;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Startup
@QuarkusMain
@AllArgsConstructor
public class ServiceApplication {

    public static final int START_UP_DOCKER_CLIENT_PRIORITY = 1000;
    public static final int START_UP_SCHEMA_MIGRATION_PRIORITY = 1000;
    public static final int START_UP_BOOTSTRAP_SERVICE_PRIORITY = 2000;
    public static final int START_UP_RELAY_JOB_PRIORITY = 3000;

    public static void main(String... args) {
        Quarkus.run(args);
    }
}
