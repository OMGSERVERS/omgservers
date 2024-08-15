package com.omgservers.service.service.bootstrap.impl.method.bootstrapDatabaseSchema;

import io.smallrye.mutiny.Uni;

public interface BootstrapDatabaseSchemaMethod {
    Uni<Void> bootstrapDatabaseSchema();
}
