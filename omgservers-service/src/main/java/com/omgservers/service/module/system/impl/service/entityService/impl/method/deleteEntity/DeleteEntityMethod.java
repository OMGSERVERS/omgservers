package com.omgservers.service.module.system.impl.service.entityService.impl.method.deleteEntity;

import com.omgservers.model.dto.system.DeleteEntityRequest;
import com.omgservers.model.dto.system.DeleteEntityResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteEntityMethod {
    Uni<DeleteEntityResponse> deleteEntity(DeleteEntityRequest request);
}
