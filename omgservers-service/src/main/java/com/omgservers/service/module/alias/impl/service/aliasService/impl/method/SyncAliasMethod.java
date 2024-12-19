package com.omgservers.service.module.alias.impl.service.aliasService.impl.method;

import com.omgservers.schema.module.alias.SyncAliasRequest;
import com.omgservers.schema.module.alias.SyncAliasResponse;
import io.smallrye.mutiny.Uni;

public interface SyncAliasMethod {
    Uni<SyncAliasResponse> execute(SyncAliasRequest request);
}
