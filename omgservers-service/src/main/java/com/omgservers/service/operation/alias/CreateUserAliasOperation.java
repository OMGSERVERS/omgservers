package com.omgservers.service.operation.alias;

import io.smallrye.mutiny.Uni;

public interface CreateUserAliasOperation {
    Uni<Boolean> execute(Long userId, String aliasValue);
}
