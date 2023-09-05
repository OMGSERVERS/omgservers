package com.omgservers.module.internal.impl.service.indexService.impl.method.deleteIndex;

import com.omgservers.ChangeContext;
import com.omgservers.dto.internal.DeleteIndexRequest;
import com.omgservers.module.internal.impl.operation.deleteIndex.DeleteIndexOperation;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
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
        DeleteIndexRequest.validate(request);

        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteIndexOperation.deleteIndex(changeContext, sqlConnection, id))
                .map(ChangeContext::getResult)
                //TODO: make response with deleted flag
                .replaceWithVoid();
    }
}
