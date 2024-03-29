package com.omgservers.service.configuration;

public class ServicePriorityConfiguration {

    public static final int START_UP_DOCKER_CLIENT_PRIORITY = 1000;
    public static final int START_UP_SCHEMA_MIGRATION_PRIORITY = 1000;
    public static final int START_UP_BOOTSTRAP_SERVICE_PRIORITY = 2000;
    public static final int START_UP_BOOTSTRAP_RELAY_JOB_PRIORITY = 3000;
}
