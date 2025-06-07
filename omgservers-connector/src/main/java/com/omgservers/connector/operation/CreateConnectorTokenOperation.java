package com.omgservers.connector.operation;

import io.smallrye.mutiny.Uni;

public interface CreateConnectorTokenOperation {
    Uni<String> execute();
}
