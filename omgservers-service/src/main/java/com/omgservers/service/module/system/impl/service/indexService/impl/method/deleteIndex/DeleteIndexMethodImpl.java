package com.omgservers.service.module.system.impl.service.indexService.impl.method.deleteIndex;

import com.omgservers.model.dto.system.DeleteIndexRequest;
import com.omgservers.service.module.system.impl.operation.deleteIndex.DeleteIndexOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteIndexMethodImpl implements DeleteIndexMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteIndexOperation deleteIndexOperation;

    @Override
    public Uni<Void> deleteIndex(final DeleteIndexRequest request) {
        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteIndexOperation.deleteIndex(changeContext, sqlConnection, id))
                .map(ChangeContext::getResult)
                //TODO: make response with deleted flag
                .replaceWithVoid();
    }
}
