package com.omgservers.service.operation.alias;

import io.smallrye.mutiny.Uni;

public interface CreatePtrAliasOperation {
    Uni<Boolean> execute(Long entityId,
                         String aliasValue,
                         String idempotencyKey);
}
