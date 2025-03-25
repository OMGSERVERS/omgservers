package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeMessage;

import com.omgservers.schema.module.runtime.runtimeMessage.DeleteRuntimeMessagesRequest;
import com.omgservers.schema.module.runtime.runtimeMessage.DeleteRuntimeMessagesResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.runtime.impl.operation.runtimeMessage.DeleteRuntimeMessagesByIdsOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteRuntimeMessagesMethodImpl implements DeleteRuntimeMessagesMethod {

    final DeleteRuntimeMessagesByIdsOperation deleteRuntimeMessagesByIdsOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteRuntimeMessagesResponse> execute(final DeleteRuntimeMessagesRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var runtimeId = request.getRuntimeId();
                    final var ids = request.getIds();
                    return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                                    deleteRuntimeMessagesByIdsOperation.execute(changeContext,
                                            sqlConnection,
                                            shardModel.shard(),
                                            runtimeId,
                                            ids
                                    ))
                            .map(ChangeContext::getResult);
                })
                .map(DeleteRuntimeMessagesResponse::new);

    }
}
