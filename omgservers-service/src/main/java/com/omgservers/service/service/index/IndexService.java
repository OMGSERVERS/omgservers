package com.omgservers.service.service.index;

import com.omgservers.service.service.index.dto.DeleteIndexRequest;
import com.omgservers.service.service.index.dto.DeleteIndexResponse;
import com.omgservers.service.service.index.dto.GetIndexRequest;
import com.omgservers.service.service.index.dto.GetIndexResponse;
import com.omgservers.service.service.index.dto.SyncIndexRequest;
import com.omgservers.service.service.index.dto.SyncIndexResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface IndexService {

    Uni<GetIndexResponse> getIndex(@Valid GetIndexRequest request);

    Uni<SyncIndexResponse> syncIndex(@Valid SyncIndexRequest request);

    Uni<SyncIndexResponse> syncIndexWithIdempotency(@Valid SyncIndexRequest request);

    Uni<DeleteIndexResponse> deleteIndex(@Valid DeleteIndexRequest request);
}
