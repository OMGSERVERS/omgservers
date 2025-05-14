package com.omgservers.service.master.entity.impl.service.entityService.impl.method;

import com.omgservers.schema.master.entity.FindEntityRequest;
import com.omgservers.schema.master.entity.FindEntityResponse;
import io.smallrye.mutiny.Uni;

public interface FindEntityMethod {
    Uni<FindEntityResponse> execute(FindEntityRequest request);
}
