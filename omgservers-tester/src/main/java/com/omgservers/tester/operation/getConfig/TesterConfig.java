package com.omgservers.tester.operation.getConfig;

import io.smallrye.config.ConfigMapping;

import java.net.URI;

@ConfigMapping(prefix = "omgservers.tester")
public interface TesterConfig {

    URI gatewayUri();

    String adminUsername();

    String adminPassword();
}
