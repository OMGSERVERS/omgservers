package com.omgservers.worker.operation.getConfig;

import com.omgservers.model.runtime.RuntimeQualifierEnum;
import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "omgservers")
public interface WorkerConfig {

    Long userId();

    String password();

    Long runtimeId();

    RuntimeQualifierEnum runtimeQualifier();
}
