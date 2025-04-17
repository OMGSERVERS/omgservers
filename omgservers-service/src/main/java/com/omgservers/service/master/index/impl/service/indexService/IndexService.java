package com.omgservers.service.master.index.impl.service.indexService;

import com.omgservers.schema.master.index.GetIndexRequest;
import com.omgservers.schema.master.index.GetIndexResponse;
import com.omgservers.schema.master.index.SyncIndexRequest;
import com.omgservers.schema.master.index.SyncIndexResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface IndexService {

    Uni<GetIndexResponse> execute(@Valid GetIndexRequest request);

    Uni<SyncIndexResponse> execute(@Valid SyncIndexRequest request);

    Uni<SyncIndexResponse> executeWithIdempotency(@Valid SyncIndexRequest request);
}
