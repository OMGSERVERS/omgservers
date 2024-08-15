package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionJenkinsRequest.getVersionJenkinsRequest;

import com.omgservers.schema.module.tenant.versionJenkinsRequest.GetVersionJenkinsRequestRequest;
import com.omgservers.schema.module.tenant.versionJenkinsRequest.GetVersionJenkinsRequestResponse;
import com.omgservers.service.module.tenant.impl.operation.versionJenkinsRequest.selectVersionJenkinsRequest.SelectVersionJenkinsRequestOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetVersionJenkinsRequestMethodImpl implements GetVersionJenkinsRequestMethod {

    final SelectVersionJenkinsRequestOperation selectVersionJenkinsRequestOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetVersionJenkinsRequestResponse> getVersionJenkinsRequest(
            final GetVersionJenkinsRequestRequest request) {
        log.debug("Get version jenkins request, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectVersionJenkinsRequestOperation
                            .selectVersionJenkinsRequest(sqlConnection, shard, tenantId, id));
                })
                .map(GetVersionJenkinsRequestResponse::new);
    }
}
