package com.omgservers.service.operation.runtime;

import com.omgservers.schema.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.schema.shard.runtime.runtimeCommand.DeleteRuntimeCommandRequest;
import com.omgservers.schema.shard.runtime.runtimeCommand.DeleteRuntimeCommandResponse;
import com.omgservers.schema.shard.runtime.runtimeCommand.ViewRuntimeCommandsRequest;
import com.omgservers.schema.shard.runtime.runtimeCommand.ViewRuntimeCommandsResponse;
import com.omgservers.service.shard.runtime.RuntimeShard;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteRuntimeCommandsOperationImpl implements DeleteRuntimeCommandsOperation {

    final RuntimeShard runtimeShard;

    @Override
    public Uni<Void> execute(final Long runtimeId) {
        return viewRuntimeCommands(runtimeId)
                .flatMap(runtimeCommands -> Multi.createFrom().iterable(runtimeCommands)
                        .onItem().transformToUniAndConcatenate(runtimeCommand -> {
                            final var runtimeCommandId = runtimeCommand.getId();
                            return deleteRuntimeCommands(runtimeId, runtimeCommandId)
                                    .onFailure()
                                    .recoverWithItem(t -> {
                                        log.error("Failed to delete, id={}/{}, {}:{}",
                                                runtimeId,
                                                runtimeCommandId,
                                                t.getClass().getSimpleName(),
                                                t.getMessage());
                                        return Boolean.FALSE;
                                    });
                        })
                        .collect().asList()
                )
                .replaceWithVoid();
    }

    Uni<List<RuntimeCommandModel>> viewRuntimeCommands(final Long runtimeId) {
        final var request = new ViewRuntimeCommandsRequest(runtimeId);
        return runtimeShard.getService().execute(request)
                .map(ViewRuntimeCommandsResponse::getRuntimeCommands);
    }

    Uni<Boolean> deleteRuntimeCommands(final Long runtimeId, final Long id) {
        final var request = new DeleteRuntimeCommandRequest(runtimeId, id);
        return runtimeShard.getService().execute(request)
                .map(DeleteRuntimeCommandResponse::getDeleted);
    }
}
