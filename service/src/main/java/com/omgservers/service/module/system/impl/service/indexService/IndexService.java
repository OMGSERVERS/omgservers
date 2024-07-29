package com.omgservers.service.module.system.impl.service.indexService;

import com.omgservers.schema.service.system.DeleteIndexRequest;
import com.omgservers.schema.service.system.DeleteIndexResponse;
import com.omgservers.schema.service.system.GetIndexRequest;
import com.omgservers.schema.service.system.GetIndexResponse;
import com.omgservers.schema.service.system.SyncIndexRequest;
import com.omgservers.schema.service.system.SyncIndexResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface IndexService {

    Uni<GetIndexResponse> getIndex(@Valid GetIndexRequest request);

    Uni<SyncIndexResponse> syncIndex(@Valid SyncIndexRequest request);

    Uni<SyncIndexResponse> syncIndexWithIdempotency(@Valid SyncIndexRequest request);

    Uni<DeleteIndexResponse> deleteIndex(@Valid DeleteIndexRequest request);
}
