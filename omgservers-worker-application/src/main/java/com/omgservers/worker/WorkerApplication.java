package com.omgservers.worker;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.Startup;
import io.quarkus.runtime.annotations.QuarkusMain;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Startup
@QuarkusMain
@AllArgsConstructor
public class WorkerApplication {
    public static final int START_UP_TOKEN_HOLDER_PRIORITY = 1000;
    public static final int START_UP_HANDLER_HOLDER_PRIORITY = 2000;
    public static final int START_UP_WORKER_JOB_PRIORITY = 3000;

    public static void main(String... args) {
        Quarkus.run(args);
    }
}
