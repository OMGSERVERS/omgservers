package com.omgservers.module.internal.impl.service.indexService.impl.method.getIndex;

import com.omgservers.dto.internal.GetIndexRequest;
import com.omgservers.dto.internal.GetIndexHelpResponse;
import io.smallrye.mutiny.Uni;

public interface GetIndexMethod {
    Uni<GetIndexHelpResponse> getIndex(GetIndexRequest request);
}
