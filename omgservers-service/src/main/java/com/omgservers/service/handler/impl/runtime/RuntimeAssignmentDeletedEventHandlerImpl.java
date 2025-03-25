package com.omgservers.service.handler.impl.runtime;

import com.omgservers.schema.message.body.ClientRemovedMessageBodyDto;
import com.omgservers.schema.model.runtimeAssignment.RuntimeAssignmentModel;
import com.omgservers.schema.model.runtimeMessage.RuntimeMessageModel;
import com.omgservers.schema.module.runtime.runtimeAssignment.GetRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.runtimeAssignment.GetRuntimeAssignmentResponse;
import com.omgservers.schema.module.runtime.runtimeMessage.SyncRuntimeMessageRequest;
import com.omgservers.schema.module.runtime.runtimeMessage.SyncRuntimeMessageResponse;
import com.omgservers.service.event.EventModel;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.event.body.module.runtime.RuntimeAssignmentDeletedEventBodyModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.runtime.RuntimeMessageModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.operation.client.FindAndDeleteClientRuntimeRefOperation;
import com.omgservers.service.shard.client.ClientShard;
import com.omgservers.service.shard.runtime.RuntimeShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RuntimeAssignmentDeletedEventHandlerImpl implements EventHandler {

    final RuntimeShard runtimeShard;
    final ClientShard clientShard;

    final FindAndDeleteClientRuntimeRefOperation findAndDeleteClientRuntimeRefOperation;

    final RuntimeMessageModelFactory runtimeMessageModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.RUNTIME_ASSIGNMENT_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.trace("Handle event, {}", event);

        final var body = (RuntimeAssignmentDeletedEventBodyModel) event.getBody();
        final var runtimeId = body.getRuntimeId();
        final var id = body.getId();

        final var idempotencyKey = event.getId().toString();

        return getRuntimeAssignment(runtimeId, id)
                .flatMap(runtimeAssignment -> {
                    log.debug("Deleted, {}", runtimeAssignment);

                    final var clientId = runtimeAssignment.getClientId();

                    return findAndDeleteClientRuntimeRefOperation.execute(clientId, runtimeId)
                            .flatMap(voidItem -> {
                                final var messageBody = new ClientRemovedMessageBodyDto();
                                messageBody.setClientId(clientId);

                                final var runtimeMessage = runtimeMessageModelFactory.create(runtimeId,
                                        messageBody,
                                        idempotencyKey);

                                return syncRuntimeMessage(runtimeMessage);
                            });
                })
                .replaceWithVoid();
    }

    Uni<RuntimeAssignmentModel> getRuntimeAssignment(final Long runtimeId, final Long id) {
        final var request = new GetRuntimeAssignmentRequest(runtimeId, id);
        return runtimeShard.getService().execute(request)
                .map(GetRuntimeAssignmentResponse::getRuntimeAssignment);
    }

    Uni<Boolean> syncRuntimeMessage(final RuntimeMessageModel runtimeMessage) {
        final var request = new SyncRuntimeMessageRequest(runtimeMessage);
        return runtimeShard.getService().executeWithIdempotency(request)
                .map(SyncRuntimeMessageResponse::getCreated)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithItem(Boolean.FALSE);
    }
}
