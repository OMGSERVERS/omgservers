package com.omgservers.service.module.alias.impl.service.aliasService.impl.method;

import com.omgservers.schema.module.alias.ViewAliasesRequest;
import com.omgservers.schema.module.alias.ViewAliasesResponse;
import io.smallrye.mutiny.Uni;

public interface ViewAliasesMethod {
    Uni<ViewAliasesResponse> execute(ViewAliasesRequest request);
}
