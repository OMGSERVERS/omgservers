package com.omgservers.service.service.index.impl.method.deleteIndex;

import com.omgservers.service.service.index.dto.DeleteIndexRequest;
import com.omgservers.service.service.index.dto.DeleteIndexResponse;
import com.omgservers.service.service.index.operation.deleteIndex.DeleteIndexOperation;
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
    public Uni<DeleteIndexResponse> deleteIndex(final DeleteIndexRequest request) {
        log.trace("Requested, {}", request);

        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteIndexOperation.deleteIndex(changeContext, sqlConnection, id))
                .map(ChangeContext::getResult)
                .map(DeleteIndexResponse::new);
    }
}
