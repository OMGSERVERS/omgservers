package com.omgservers.service.server.initializer.impl.method;

import io.smallrye.mutiny.Uni;

public interface MigrateMasterSchemaMethod {
    Uni<Void> execute();
}
