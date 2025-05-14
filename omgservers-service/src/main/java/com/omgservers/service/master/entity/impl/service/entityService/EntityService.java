package com.omgservers.service.master.entity.impl.service.entityService;

import com.omgservers.schema.master.entity.DeleteEntityRequest;
import com.omgservers.schema.master.entity.DeleteEntityResponse;
import com.omgservers.schema.master.entity.FindEntityRequest;
import com.omgservers.schema.master.entity.FindEntityResponse;
import com.omgservers.schema.master.entity.GetEntityRequest;
import com.omgservers.schema.master.entity.GetEntityResponse;
import com.omgservers.schema.master.entity.SyncEntityRequest;
import com.omgservers.schema.master.entity.SyncEntityResponse;
import com.omgservers.schema.master.entity.ViewEntitiesRequest;
import com.omgservers.schema.master.entity.ViewEntitiesResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface EntityService {

    Uni<GetEntityResponse> execute(@Valid GetEntityRequest request);

    Uni<FindEntityResponse> execute(@Valid FindEntityRequest request);

    Uni<ViewEntitiesResponse> execute(@Valid ViewEntitiesRequest request);

    Uni<SyncEntityResponse> execute(@Valid SyncEntityRequest request);

    Uni<SyncEntityResponse> executeWithIdempotency(@Valid SyncEntityRequest request);

    Uni<DeleteEntityResponse> execute(@Valid DeleteEntityRequest request);
}
