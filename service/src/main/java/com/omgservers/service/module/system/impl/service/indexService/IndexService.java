package com.omgservers.service.module.system.impl.service.indexService;

import com.omgservers.model.dto.system.DeleteIndexRequest;
import com.omgservers.model.dto.system.DeleteIndexResponse;
import com.omgservers.model.dto.system.FindIndexRequest;
import com.omgservers.model.dto.system.FindIndexResponse;
import com.omgservers.model.dto.system.GetIndexRequest;
import com.omgservers.model.dto.system.GetIndexResponse;
import com.omgservers.model.dto.system.SyncIndexRequest;
import com.omgservers.model.dto.system.SyncIndexResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface IndexService {

    Uni<GetIndexResponse> getIndex(@Valid GetIndexRequest request);

    Uni<FindIndexResponse> findIndex(@Valid FindIndexRequest request);

    Uni<SyncIndexResponse> syncIndex(@Valid SyncIndexRequest request);

    Uni<DeleteIndexResponse> deleteIndex(@Valid DeleteIndexRequest request);
}
