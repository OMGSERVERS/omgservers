package com.omgservers.base.module.internal.impl.service.indexService.impl.method.getIndex;

import com.omgservers.dto.internalModule.GetIndexRequest;
import com.omgservers.dto.internalModule.GetIndexHelpResponse;
import io.smallrye.mutiny.Uni;

public interface GetIndexMethod {
    Uni<GetIndexHelpResponse> getIndex(GetIndexRequest request);
}
