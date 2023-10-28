package com.omgservers.module.system.impl.service.indexService.impl.method.getIndex;

import com.omgservers.model.dto.internal.GetIndexRequest;
import com.omgservers.model.dto.internal.GetIndexResponse;
import io.smallrye.mutiny.Uni;

public interface GetIndexMethod {
    Uni<GetIndexResponse> getIndex(GetIndexRequest request);
}
