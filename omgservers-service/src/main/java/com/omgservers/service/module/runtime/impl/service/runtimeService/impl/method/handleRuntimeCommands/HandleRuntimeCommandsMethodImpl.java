package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.handleRuntimeCommands;

import com.omgservers.model.dto.runtime.HandleRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.HandleRuntimeCommandsResponse;
import com.omgservers.service.module.runtime.impl.operation.deleteRuntimeCommandsByIds.DeleteRuntimeCommandByIdsOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import com.omgservers.service.operation.upsertEvent.UpsertEventOperation;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleRuntimeCommandsMethodImpl implements HandleRuntimeCommandsMethod {

    final DeleteRuntimeCommandByIdsOperation deleteRuntimeCommandByIdsOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertEventOperation upsertEventOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<HandleRuntimeCommandsResponse> handleRuntimeCommands(final HandleRuntimeCommandsRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var runtimeId = request.getRuntimeId();
                    final var ids = request.getIds();
                    final var events = request.getEvents();
                    return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                                    deleteRuntimeCommandByIdsOperation.deleteRuntimeCommandByIds(changeContext,
                                                    sqlConnection,
                                                    shardModel.shard(),
                                                    runtimeId,
                                                    ids)
                                            .call(deleted -> Multi.createFrom().iterable(events)
                                                    .onItem().transformToUniAndConcatenate(event ->
                                                            upsertEventOperation.upsertEvent(changeContext,
                                                                    sqlConnection, event))
                                                    .collect().asList()
                                                    .replaceWithVoid())
                            )
                            .map(ChangeContext::getResult);
                })
                .map(HandleRuntimeCommandsResponse::new);
    }
}
