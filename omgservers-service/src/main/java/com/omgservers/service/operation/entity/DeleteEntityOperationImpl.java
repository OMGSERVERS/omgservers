package com.omgservers.service.operation.entity;

import com.omgservers.schema.master.entity.DeleteEntityRequest;
import com.omgservers.schema.master.entity.DeleteEntityResponse;
import com.omgservers.schema.master.entity.FindEntityRequest;
import com.omgservers.schema.master.entity.FindEntityResponse;
import com.omgservers.schema.model.entity.EntityModel;
import com.omgservers.service.factory.entity.EntityModelFactory;
import com.omgservers.service.master.entity.EntityMaster;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteEntityOperationImpl implements DeleteEntityOperation {

    final EntityMaster entityMaster;

    final EntityModelFactory entityModelFactory;

    @Override
    public Uni<Boolean> execute(Long entityId) {
        return findEntity(entityId)
                .map(EntityModel::getId)
                .flatMap(this::deleteEntity);
    }

    @Override
    public Uni<Boolean> executeFailSafe(final Long entityId) {
        return execute(entityId)
                .onFailure()
                .recoverWithItem(t -> {
                    log.warn("Failed to delete entity, entityId={}, {}:{}",
                            entityId,
                            t.getClass().getSimpleName(),
                            t.getMessage());
                    return Boolean.FALSE;
                });
    }

    Uni<EntityModel> findEntity(final Long entityId) {
        final var request = new FindEntityRequest(entityId);
        return entityMaster.getService().execute(request)
                .map(FindEntityResponse::getEntity);
    }

    Uni<Boolean> deleteEntity(final Long id) {
        final var request = new DeleteEntityRequest(id);
        return entityMaster.getService().execute(request)
                .map(DeleteEntityResponse::getDeleted);
    }
}
