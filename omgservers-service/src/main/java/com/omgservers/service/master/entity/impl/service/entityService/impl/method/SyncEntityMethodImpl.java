package com.omgservers.service.master.entity.impl.service.entityService.impl.method;

import com.omgservers.schema.master.entity.SyncEntityRequest;
import com.omgservers.schema.master.entity.SyncEntityResponse;
import com.omgservers.service.master.entity.impl.operation.UpsertEntityOperation;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncEntityMethodImpl implements SyncEntityMethod {

    final UpsertEntityOperation upsertEntityOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<SyncEntityResponse> execute(final SyncEntityRequest request) {
        log.debug("{}", request);

        final var entity = request.getEntity();

        return changeWithContextOperation.<Boolean>changeWithContext((context, sqlConnection) ->
                        upsertEntityOperation.execute(
                                context,
                                sqlConnection,
                                entity))
                .map(ChangeContext::getResult)
                .map(SyncEntityResponse::new);
    }
}
