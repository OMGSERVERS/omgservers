package com.omgservers.ctl.configuration;

import java.net.URI;

public class LocalConfiguration {
    public static final URI API_URI = URI.create("http://localhost:8080");
    public static final URI REGISTRY_URI = URI.create("localhost:5000");
    public static final URI DOCKER_URI = URI.create("unix:///var/run/docker.sock");

    public static final String SERVICE_NAME = "local";
    public static final String SUPPORT_USER_ALIAS = "support";
    public static final String SUPPORT_USER_PASSWORD = "support";
}
