package com.omgservers.connector;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.Startup;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.ws.rs.core.Application;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Startup
@QuarkusMain
@AllArgsConstructor
public class ConnectorApplication extends Application {

    public static void main(String... args) {
        Quarkus.run(args);
    }
}
