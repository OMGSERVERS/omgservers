package com.omgservers.service.operation.alias;

import io.smallrye.mutiny.Uni;

public interface CreateUserAliasOperation {
    Uni<CreateUserAliasResult> execute(Long userId, String aliasValue);
}
