package com.omgservers.service.module.system.impl.service.indexService.impl.method.getIndex;

import com.omgservers.model.dto.system.GetIndexRequest;
import com.omgservers.model.dto.system.GetIndexResponse;
import io.smallrye.mutiny.Uni;

public interface GetIndexMethod {
    Uni<GetIndexResponse> getIndex(GetIndexRequest request);
}
