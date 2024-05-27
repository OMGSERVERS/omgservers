package com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapSchema;

import io.smallrye.mutiny.Uni;

public interface BootstrapSchemaMethod {
    Uni<Void> bootstrapSchema();
}
