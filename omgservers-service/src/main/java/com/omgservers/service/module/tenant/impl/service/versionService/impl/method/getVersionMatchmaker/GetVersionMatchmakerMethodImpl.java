package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.getVersionMatchmaker;

import com.omgservers.model.dto.tenant.GetVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.GetVersionMatchmakerResponse;
import com.omgservers.service.module.tenant.impl.operation.selectVersionMatchmaker.SelectVersionMatchmakerOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetVersionMatchmakerMethodImpl implements GetVersionMatchmakerMethod {

    final SelectVersionMatchmakerOperation selectVersionMatchmakerOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetVersionMatchmakerResponse> getVersionMatchmaker(final GetVersionMatchmakerRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectVersionMatchmakerOperation
                            .selectVersionMatchmaker(sqlConnection, shard, tenantId, id));
                })
                .map(GetVersionMatchmakerResponse::new);
    }
}
