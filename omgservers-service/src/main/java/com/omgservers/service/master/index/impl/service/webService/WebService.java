package com.omgservers.service.master.index.impl.service.webService;

import com.omgservers.schema.master.index.GetIndexRequest;
import com.omgservers.schema.master.index.GetIndexResponse;
import com.omgservers.schema.master.index.SyncIndexRequest;
import com.omgservers.schema.master.index.SyncIndexResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<GetIndexResponse> execute(GetIndexRequest request);

    Uni<SyncIndexResponse> execute(SyncIndexRequest request);
}
