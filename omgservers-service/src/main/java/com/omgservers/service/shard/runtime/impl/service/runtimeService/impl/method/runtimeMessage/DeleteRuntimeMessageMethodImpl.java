package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeMessage;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.runtime.runtimeMessage.DeleteRuntimeMessageRequest;
import com.omgservers.schema.shard.runtime.runtimeMessage.DeleteRuntimeMessageResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.runtime.impl.operation.runtimeMessage.DeleteRuntimeMessageOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteRuntimeMessageMethodImpl implements DeleteRuntimeMessageMethod {

    final DeleteRuntimeMessageOperation deleteRuntimeMessageOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<DeleteRuntimeMessageResponse> execute(final ShardModel shardModel,
                                                     final DeleteRuntimeMessageRequest request) {
        log.trace("{}", request);

        final var runtimeId = request.getRuntimeId();
        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteRuntimeMessageOperation.execute(
                                changeContext,
                                sqlConnection,
                                shardModel.slot(),
                                runtimeId,
                                id))
                .map(ChangeContext::getResult)
                .map(DeleteRuntimeMessageResponse::new);
    }
}
