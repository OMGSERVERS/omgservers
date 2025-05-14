package com.omgservers.service.master.entity.impl.service.entityService.impl.method;

import com.omgservers.schema.master.entity.GetEntityRequest;
import com.omgservers.schema.master.entity.GetEntityResponse;
import io.smallrye.mutiny.Uni;

public interface GetEntityMethod {
    Uni<GetEntityResponse> execute(GetEntityRequest request);
}
