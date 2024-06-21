package com.omgservers.service.module.system.impl.service.bootstrapService.impl.method.bootstrapDatabaseSchema;

import io.smallrye.mutiny.Uni;

public interface BootstrapDatabaseSchemaMethod {
    Uni<Void> bootstrapDatabaseSchema();
}
