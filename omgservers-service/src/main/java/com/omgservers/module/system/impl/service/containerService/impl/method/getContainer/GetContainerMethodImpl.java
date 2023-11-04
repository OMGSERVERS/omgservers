package com.omgservers.module.system.impl.service.containerService.impl.method.getContainer;

import com.omgservers.model.dto.system.GetContainerRequest;
import com.omgservers.model.dto.system.GetContainerResponse;
import com.omgservers.module.system.impl.operation.selectContainer.SelectContainerOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class GetContainerMethodImpl implements GetContainerMethod {

    final SelectContainerOperation selectContainerOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetContainerResponse> getContainer(final GetContainerRequest request) {
        final var id = request.getId();
        final var deleted = request.getDeleted();
        return pgPool.withTransaction(sqlConnection -> selectContainerOperation
                        .selectContainer(sqlConnection, id, deleted))
                .map(GetContainerResponse::new);
    }
}
