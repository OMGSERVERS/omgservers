package com.omgservers.service.master.index.impl.service.indexService.impl.method;

import com.omgservers.schema.master.index.SyncIndexRequest;
import com.omgservers.schema.master.index.SyncIndexResponse;
import io.smallrye.mutiny.Uni;

public interface SyncIndexMethod {
    Uni<SyncIndexResponse> execute(SyncIndexRequest request);
}
