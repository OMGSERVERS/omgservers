package com.omgservers.service.module.system.impl.service.jobService.impl.method.findJob;

import com.omgservers.model.dto.system.FindJobRequest;
import com.omgservers.model.dto.system.FindJobResponse;
import com.omgservers.service.module.system.impl.operation.selectJobByShardKeyAndEntityIdAndQualifierOperation.SelectJobByShardKeyAndEntityIdAndQualifierOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindJobMethodImpl implements FindJobMethod {

    final SelectJobByShardKeyAndEntityIdAndQualifierOperation selectJobByShardKeyAndEntityIdAndQualifierOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindJobResponse> findJob(final FindJobRequest request) {
        log.debug("Find job, request={}", request);

        final var shardKey = request.getShardKey();
        final var entityId = request.getEntityId();
        final var qualifier = request.getQualifier();
        return pgPool.withTransaction(sqlConnection -> selectJobByShardKeyAndEntityIdAndQualifierOperation
                        .selectJobByShardKeyAndEntityIdAndQualifier(sqlConnection, shardKey, entityId, qualifier))
                .map(FindJobResponse::new);
    }
}
