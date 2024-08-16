package com.omgservers.service;

import com.omgservers.service.configuration.ServiceOpenApiConfiguration;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.Startup;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.ws.rs.core.Application;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;

@Slf4j
@Startup
@QuarkusMain
@AllArgsConstructor
@SecuritySchemes({
        @SecurityScheme(securitySchemeName = ServiceOpenApiConfiguration.DEVELOPER_SECURITY_SCHEMA,
                type = SecuritySchemeType.HTTP, scheme = "Bearer"),
        @SecurityScheme(securitySchemeName = ServiceOpenApiConfiguration.PLAYER_SECURITY_SCHEMA,
                type = SecuritySchemeType.HTTP, scheme = "Bearer"),
        @SecurityScheme(securitySchemeName = ServiceOpenApiConfiguration.RUNTIME_SECURITY_SCHEMA,
                type = SecuritySchemeType.HTTP, scheme = "Bearer")
})
public class ServiceApplication extends Application {

    public static void main(String... args) {
        Quarkus.run(args);
    }
}
