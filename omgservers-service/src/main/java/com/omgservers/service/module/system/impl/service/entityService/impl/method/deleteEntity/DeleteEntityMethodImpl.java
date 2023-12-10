package com.omgservers.service.module.system.impl.service.entityService.impl.method.deleteEntity;

import com.omgservers.model.dto.system.DeleteEntityRequest;
import com.omgservers.model.dto.system.DeleteEntityResponse;
import com.omgservers.service.module.system.impl.operation.deleteEntity.DeleteEntityOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteEntityMethodImpl implements DeleteEntityMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteEntityOperation deleteEntityOperation;

    @Override
    public Uni<DeleteEntityResponse> deleteEntity(final DeleteEntityRequest request) {
        log.debug("Delete entity, request={}", request);

        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteEntityOperation.deleteEntity(changeContext, sqlConnection, id)
                )
                .map(ChangeContext::getResult)
                .map(DeleteEntityResponse::new);
    }
}
