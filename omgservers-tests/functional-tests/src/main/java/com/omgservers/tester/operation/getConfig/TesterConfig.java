package com.omgservers.tester.operation.getConfig;

import io.smallrye.config.ConfigMapping;

import java.net.URI;

@ConfigMapping(prefix = "omgservers.tester")
public interface TesterConfig {

    EnvironmentEnum environment();

    EnvironmentConfig localtesting();

    EnvironmentConfig development();

    EnvironmentConfig integration();

    EnvironmentConfig standalone();

    interface EnvironmentConfig {
        URI externalUri();

        URI internalUri();

        AdminConfig admin();

        SupportConfig support();

        RegistryConfig registry();
    }

    interface AdminConfig {
        String alias();

        String password();
    }

    interface SupportConfig {
        String alias();

        String password();
    }

    interface RegistryConfig {
        String url();
    }
}
