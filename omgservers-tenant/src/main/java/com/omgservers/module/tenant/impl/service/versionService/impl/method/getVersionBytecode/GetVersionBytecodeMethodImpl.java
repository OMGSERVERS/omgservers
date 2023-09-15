package com.omgservers.module.tenant.impl.service.versionService.impl.method.getVersionBytecode;

import com.omgservers.dto.tenant.GetVersionBytecodeRequest;
import com.omgservers.dto.tenant.GetVersionBytecodeResponse;
import com.omgservers.module.tenant.impl.operation.selectVersionBytecode.SelectVersionBytecodeOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetVersionBytecodeMethodImpl implements GetVersionBytecodeMethod {

    final SelectVersionBytecodeOperation selectVersionBytecodeOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetVersionBytecodeResponse> getVersionBytecode(GetVersionBytecodeRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var versionId = request.getVersionId();
                    return pgPool.withTransaction(sqlConnection -> selectVersionBytecodeOperation
                            .selectVersionBytecode(sqlConnection, shard, tenantId, versionId));
                })
                .map(GetVersionBytecodeResponse::new);
    }
}
