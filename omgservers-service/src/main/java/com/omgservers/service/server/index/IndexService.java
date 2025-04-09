package com.omgservers.service.server.index;

import com.omgservers.service.server.index.dto.DeleteIndexRequest;
import com.omgservers.service.server.index.dto.DeleteIndexResponse;
import com.omgservers.service.server.index.dto.GetIndexRequest;
import com.omgservers.service.server.index.dto.GetIndexResponse;
import com.omgservers.service.server.index.dto.SyncIndexRequest;
import com.omgservers.service.server.index.dto.SyncIndexResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface IndexService {

    Uni<GetIndexResponse> getIndex(@Valid GetIndexRequest request);

    Uni<SyncIndexResponse> syncIndex(@Valid SyncIndexRequest request);

    Uni<SyncIndexResponse> syncIndexWithIdempotency(@Valid SyncIndexRequest request);

    Uni<DeleteIndexResponse> deleteIndex(@Valid DeleteIndexRequest request);
}
