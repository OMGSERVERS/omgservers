package com.omgservers.service.operation.alias;

import io.smallrye.mutiny.Uni;

public interface GetIdByUserOperation {
    Uni<Long> execute(String user);
}
