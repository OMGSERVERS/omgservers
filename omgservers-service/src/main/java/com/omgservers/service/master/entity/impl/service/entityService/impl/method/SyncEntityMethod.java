package com.omgservers.service.master.entity.impl.service.entityService.impl.method;

import com.omgservers.schema.master.entity.SyncEntityRequest;
import com.omgservers.schema.master.entity.SyncEntityResponse;
import io.smallrye.mutiny.Uni;

public interface SyncEntityMethod {
    Uni<SyncEntityResponse> execute(SyncEntityRequest request);
}
