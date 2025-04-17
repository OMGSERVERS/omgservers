package com.omgservers.service.server.initializer.impl.method;

import io.smallrye.mutiny.Uni;

public interface MigrateShardsSchemasMethod {
    Uni<Void> execute();
}
