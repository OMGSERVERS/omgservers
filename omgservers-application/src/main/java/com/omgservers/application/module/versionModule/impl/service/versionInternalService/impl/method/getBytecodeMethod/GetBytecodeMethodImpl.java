package com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.getBytecodeMethod;

import com.omgservers.application.module.versionModule.impl.operation.selectBytecodeOperation.SelectBytecodeOperation;
import com.omgservers.base.operation.checkShard.CheckShardOperation;
import com.omgservers.dto.versionModule.GetBytecodeRoutedRequest;
import com.omgservers.dto.versionModule.GetBytecodeInternalResponse;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetBytecodeMethodImpl implements GetBytecodeMethod {

    final CheckShardOperation checkShardOperation;
    final SelectBytecodeOperation selectBytecodeOperation;
    final PgPool pgPool;

    @Override
    public Uni<GetBytecodeInternalResponse> getBytecode(GetBytecodeRoutedRequest request) {
        GetBytecodeRoutedRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var version = request.getVersionId();
                    return pgPool.withTransaction(sqlConnection -> selectBytecodeOperation
                            .selectBytecode(sqlConnection, shard, version));
                })
                .map(GetBytecodeInternalResponse::new);
    }
}
