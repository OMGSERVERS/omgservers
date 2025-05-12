package com.omgservers.service.operation.alias;

import io.smallrye.mutiny.Uni;

public interface DeletePtrAliasOperation {
    Uni<Boolean> execute(Long entityId, String aliasValue);
}
