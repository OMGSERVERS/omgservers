package com.omgservers.application;

import com.omgservers.application.module.bootstrapModule.BootstrapModule;
import com.omgservers.application.operation.getConfigOperation.GetConfigOperation;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.Startup;
import io.quarkus.runtime.annotations.QuarkusMain;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@QuarkusMain
@AllArgsConstructor
public class Application {

    public static void main(String... args) {
        Quarkus.run(args);
    }

    final BootstrapModule bootstrapModule;

    @Startup
    @WithSpan
    void startup() {
        bootstrapModule.getBootstrapHelpService().bootstrap()
                .await().indefinitely();
    }
}
