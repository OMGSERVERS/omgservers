package com.omgservers.service.operation.authz;

import io.smallrye.mutiny.Uni;

public interface AuthorizeClientRequestOperation {
    Uni<ClientAuthorization> execute(Long clientId,
                                     Long userId);
}
