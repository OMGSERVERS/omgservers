package com.omgservers.application.module.bootstrapModule.impl.service.bootstrapHelpService.impl.method.bootstrapDatabaseSchemaMethod;

import io.smallrye.mutiny.Uni;

public interface BootstrapDatabaseSchemaMethod {
    Uni<Void> bootstrapDatabaseSchema();
}
