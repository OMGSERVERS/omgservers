package com.omgservers.service.server.index.impl.method.getIndex;

import com.omgservers.service.server.index.dto.GetIndexRequest;
import com.omgservers.service.server.index.dto.GetIndexResponse;
import io.smallrye.mutiny.Uni;

public interface GetIndexMethod {
    Uni<GetIndexResponse> getIndex(GetIndexRequest request);
}
