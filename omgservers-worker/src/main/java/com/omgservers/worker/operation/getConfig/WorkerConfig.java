package com.omgservers.worker.operation.getConfig;

import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "omgservers")
public interface WorkerConfig {

    Long userId();

    String password();

    Long runtimeId();
}
