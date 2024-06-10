package com.omgservers.tester.operation.getConfig;

import io.smallrye.config.ConfigMapping;

import java.net.URI;

@ConfigMapping(prefix = "omgservers.tester")
public interface TesterConfig {

    EnvironmentEnum environment();

    EnvironmentConfig development();

    EnvironmentConfig integration();

    EnvironmentConfig standalone();

    interface EnvironmentConfig {
        URI externalUri();

        URI internalUri();

        AdminConfig admin();

        SupportConfig support();
    }

    interface AdminConfig {
        long userId();

        String password();
    }

    interface SupportConfig {
        long userId();

        String password();
    }
}
