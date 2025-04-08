package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtime;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.runtime.runtime.DeleteRuntimeRequest;
import com.omgservers.schema.module.runtime.runtime.DeleteRuntimeResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.runtime.impl.operation.runtime.DeleteRuntimeOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteRuntimeMethodImpl implements DeleteRuntimeMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteRuntimeOperation deleteRuntimeOperation;

    @Override
    public Uni<DeleteRuntimeResponse> execute(final ShardModel shardModel,
                                              final DeleteRuntimeRequest request) {
        log.trace("{}", request);

        final var id = request.getId();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteRuntimeOperation.execute(
                                changeContext,
                                sqlConnection,
                                shardModel.shard(),
                                id))
                .map(ChangeContext::getResult)
                .map(DeleteRuntimeResponse::new);
    }
}
