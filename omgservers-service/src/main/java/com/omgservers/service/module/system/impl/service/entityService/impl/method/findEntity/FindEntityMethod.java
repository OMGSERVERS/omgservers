package com.omgservers.service.module.system.impl.service.entityService.impl.method.findEntity;

import com.omgservers.model.dto.system.FindEntityRequest;
import com.omgservers.model.dto.system.FindEntityResponse;
import io.smallrye.mutiny.Uni;

public interface FindEntityMethod {
    Uni<FindEntityResponse> findEntity(FindEntityRequest request);
}
