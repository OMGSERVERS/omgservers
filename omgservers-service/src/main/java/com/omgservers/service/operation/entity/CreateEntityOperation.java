package com.omgservers.service.operation.entity;

import com.omgservers.schema.model.entity.EntityQualifierEnum;
import io.smallrye.mutiny.Uni;

public interface CreateEntityOperation {
    Uni<CreateEntityResult> execute(EntityQualifierEnum qualifier, Long entityId, String idempotencyKey);
}
