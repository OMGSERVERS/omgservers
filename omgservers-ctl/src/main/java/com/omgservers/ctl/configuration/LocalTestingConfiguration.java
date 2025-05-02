package com.omgservers.ctl.configuration;

import java.net.URI;

public class LocalTestingConfiguration {
    public static final URI LOCALTESTING_URI = URI.create("http://localhost:8080");
    public static final URI REGISTRY_URI = URI.create("http://localhost:5000");
    public static final URI DOCKER_DAEMON_URI = URI.create("unix:///var/run/docker.sock");

    public static final String SUPPORT_USER_DEFAULT_ALIAS = "support";
    public static final String SUPPORT_USER_DEFAULT_PASSWORD = "support";
}
