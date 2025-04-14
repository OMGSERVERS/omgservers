package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtimeCommand;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.runtime.runtimeCommand.DeleteRuntimeCommandRequest;
import com.omgservers.schema.shard.runtime.runtimeCommand.DeleteRuntimeCommandResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.runtime.impl.operation.runtimeCommand.DeleteRuntimeCommandOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteRuntimeCommandMethodImpl implements DeleteRuntimeCommandMethod {

    final DeleteRuntimeCommandOperation deleteRuntimeCommandOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<DeleteRuntimeCommandResponse> execute(final ShardModel shardModel,
                                                     final DeleteRuntimeCommandRequest request) {
        log.trace("{}", request);

        final var runtimeId = request.getRuntimeId();
        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteRuntimeCommandOperation.execute(
                                changeContext,
                                sqlConnection,
                                shardModel.shard(),
                                runtimeId,
                                id))
                .map(ChangeContext::getResult)
                .map(DeleteRuntimeCommandResponse::new);
    }
}
