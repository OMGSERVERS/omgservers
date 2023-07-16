package com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.getBytecodeMethod;

import com.omgservers.application.module.versionModule.impl.operation.selectBytecodeOperation.SelectBytecodeOperation;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.request.GetBytecodeInternalRequest;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.response.GetBytecodeInternalResponse;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetBytecodeMethodImpl implements GetBytecodeMethod {

    final CheckShardOperation checkShardOperation;
    final SelectBytecodeOperation selectBytecodeOperation;
    final PgPool pgPool;

    @Override
    public Uni<GetBytecodeInternalResponse> getBytecode(GetBytecodeInternalRequest request) {
        GetBytecodeInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var version = request.getVersion();
                    return pgPool.withTransaction(sqlConnection -> selectBytecodeOperation
                            .selectBytecode(sqlConnection, shard, version));
                })
                .map(GetBytecodeInternalResponse::new);
    }
}
