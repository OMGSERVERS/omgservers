package com.omgservers.service.operation.entity;

import com.omgservers.schema.master.entity.SyncEntityRequest;
import com.omgservers.schema.master.entity.SyncEntityResponse;
import com.omgservers.schema.model.entity.EntityQualifierEnum;
import com.omgservers.service.factory.entity.EntityModelFactory;
import com.omgservers.service.master.entity.EntityMaster;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateEntityOperationImpl implements CreateEntityOperation {

    final EntityMaster entityMaster;

    final EntityModelFactory entityModelFactory;

    @Override
    public Uni<CreateEntityResult> execute(final EntityQualifierEnum qualifier,
                                           final Long entityId,
                                           final String idempotencyKey) {
        final var entity = entityModelFactory.create(idempotencyKey,
                qualifier,
                entityId);
        final var request = new SyncEntityRequest(entity);
        return entityMaster.getService().executeWithIdempotency(request)
                .map(SyncEntityResponse::getCreated)
                .map(created -> new CreateEntityResult(entity, created));
    }
}
