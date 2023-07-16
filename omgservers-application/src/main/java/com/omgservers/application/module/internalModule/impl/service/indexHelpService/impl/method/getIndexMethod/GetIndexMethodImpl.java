package com.omgservers.application.module.internalModule.impl.service.indexHelpService.impl.method.getIndexMethod;

import com.omgservers.application.module.internalModule.impl.operation.getIndexOperation.GetIndexOperation;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.request.GetIndexHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.indexHelpService.response.GetIndexHelpResponse;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetIndexMethodImpl implements GetIndexMethod {

    final PgPool pgPool;
    final GetIndexOperation getIndexOperation;

    @Override
    public Uni<GetIndexHelpResponse> getIndex(final GetIndexHelpRequest request) {
        GetIndexHelpRequest.validate(request);

        final var name = request.getName();
        return pgPool.withTransaction(sqlConnection -> getIndexOperation
                        .getIndex(sqlConnection, name))
                .map(GetIndexHelpResponse::new);
    }
}
