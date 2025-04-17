package com.omgservers.service.master.index.impl.service.indexService.impl.method;

import com.omgservers.schema.master.index.GetIndexRequest;
import com.omgservers.schema.master.index.GetIndexResponse;
import io.smallrye.mutiny.Uni;

public interface GetIndexMethod {
    Uni<GetIndexResponse> execute(GetIndexRequest request);
}
