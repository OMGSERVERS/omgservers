package com.omgservers.module.version.impl.service.versionShardedService.impl.method.getBytecode;

import com.omgservers.module.version.impl.operation.selectBytecode.SelectBytecodeOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import com.omgservers.dto.version.GetBytecodeShardedRequest;
import com.omgservers.dto.version.GetBytecodeShardedResponse;
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
    public Uni<GetBytecodeShardedResponse> getBytecode(GetBytecodeShardedRequest request) {
        GetBytecodeShardedRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var version = request.getVersionId();
                    return pgPool.withTransaction(sqlConnection -> selectBytecodeOperation
                            .selectBytecode(sqlConnection, shard, version));
                })
                .map(GetBytecodeShardedResponse::new);
    }
}
