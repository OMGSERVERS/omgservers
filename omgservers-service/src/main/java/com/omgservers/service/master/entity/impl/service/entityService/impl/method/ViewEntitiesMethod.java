package com.omgservers.service.master.entity.impl.service.entityService.impl.method;

import com.omgservers.schema.master.entity.ViewEntitiesRequest;
import com.omgservers.schema.master.entity.ViewEntitiesResponse;
import io.smallrye.mutiny.Uni;

public interface ViewEntitiesMethod {
    Uni<ViewEntitiesResponse> execute(ViewEntitiesRequest request);
}
