package com.omgservers.service.server.service.index.impl.method.getIndex;

import com.omgservers.schema.service.system.GetIndexRequest;
import com.omgservers.schema.service.system.GetIndexResponse;
import io.smallrye.mutiny.Uni;

public interface GetIndexMethod {
    Uni<GetIndexResponse> getIndex(GetIndexRequest request);
}
