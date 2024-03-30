package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.getVersionMatchmakerRequest;

import com.omgservers.model.dto.tenant.GetVersionMatchmakerRequestRequest;
import com.omgservers.model.dto.tenant.GetVersionMatchmakerRequestResponse;
import com.omgservers.service.module.tenant.impl.operation.versionMatchmakerRequest.selectVersionMatchmakerRequest.SelectVersionMatchmakerRequestOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetVersionMatchmakerRequestMethodImpl implements GetVersionMatchmakerRequestMethod {

    final SelectVersionMatchmakerRequestOperation selectVersionMatchmakerRequestOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetVersionMatchmakerRequestResponse> getVersionMatchmakerRequest(
            final GetVersionMatchmakerRequestRequest request) {
        log.debug("Get version matchmaker request, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectVersionMatchmakerRequestOperation
                            .selectVersionMatchmakerRequest(sqlConnection, shard, tenantId, id));
                })
                .map(GetVersionMatchmakerRequestResponse::new);
    }
}
