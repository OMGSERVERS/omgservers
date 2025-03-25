package com.omgservers.service.operation.runtime;

import com.omgservers.schema.model.runtimeMessage.RuntimeMessageModel;
import com.omgservers.schema.module.runtime.runtimeMessage.DeleteRuntimeMessageRequest;
import com.omgservers.schema.module.runtime.runtimeMessage.DeleteRuntimeMessageResponse;
import com.omgservers.schema.module.runtime.runtimeMessage.ViewRuntimeMessagesRequest;
import com.omgservers.schema.module.runtime.runtimeMessage.ViewRuntimeMessagesResponse;
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
class DeleteRuntimeMessagesOperationImpl implements DeleteRuntimeMessagesOperation {

    final RuntimeShard runtimeShard;

    @Override
    public Uni<Void> execute(final Long runtimeId) {
        return viewRuntimeMessages(runtimeId)
                .flatMap(runtimeMessages -> Multi.createFrom().iterable(runtimeMessages)
                        .onItem().transformToUniAndConcatenate(runtimeMessage -> {
                            final var runtimeMessageId = runtimeMessage.getId();
                            return deleteRuntimeMessages(runtimeId, runtimeMessageId)
                                    .onFailure()
                                    .recoverWithItem(t -> {
                                        log.error("Failed to delete, id={}/{}, {}:{}",
                                                runtimeId,
                                                runtimeMessageId,
                                                t.getClass().getSimpleName(),
                                                t.getMessage());
                                        return Boolean.FALSE;
                                    });
                        })
                        .collect().asList()
                )
                .replaceWithVoid();
    }

    Uni<List<RuntimeMessageModel>> viewRuntimeMessages(final Long runtimeId) {
        final var request = new ViewRuntimeMessagesRequest(runtimeId);
        return runtimeShard.getService().execute(request)
                .map(ViewRuntimeMessagesResponse::getRuntimeMessages);
    }

    Uni<Boolean> deleteRuntimeMessages(final Long runtimeId, final Long id) {
        final var request = new DeleteRuntimeMessageRequest(runtimeId, id);
        return runtimeShard.getService().execute(request)
                .map(DeleteRuntimeMessageResponse::getDeleted);
    }
}
