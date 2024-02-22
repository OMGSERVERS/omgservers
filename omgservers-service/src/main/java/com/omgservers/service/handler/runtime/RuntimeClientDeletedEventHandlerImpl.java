package com.omgservers.service.handler.runtime;

import com.omgservers.model.clientRuntime.ClientRuntimeRefModel;
import com.omgservers.model.dto.client.DeleteClientRuntimeRefRequest;
import com.omgservers.model.dto.client.DeleteClientRuntimeRefResponse;
import com.omgservers.model.dto.client.FindClientRuntimeRefRequest;
import com.omgservers.model.dto.client.FindClientRuntimeRefResponse;
import com.omgservers.model.dto.runtime.GetRuntimeClientRequest;
import com.omgservers.model.dto.runtime.GetRuntimeClientResponse;
import com.omgservers.model.dto.runtime.GetRuntimeRequest;
import com.omgservers.model.dto.runtime.GetRuntimeResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.RuntimeClientDeletedEventBodyModel;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtimeClient.RuntimeClientModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.body.DeleteClientRuntimeCommandBodyModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.RuntimeCommandModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RuntimeClientDeletedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;
    final ClientModule clientModule;

    final RuntimeCommandModelFactory runtimeCommandModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.RUNTIME_CLIENT_DELETED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (RuntimeClientDeletedEventBodyModel) event.getBody();
        final var runtimeId = body.getRuntimeId();
        final var id = body.getId();

        return getRuntimeClient(runtimeId, id)
                .flatMap(runtimeClient -> {
                    final var clientId = runtimeClient.getClientId();

                    log.info("Runtime client was deleted, runtimeId={}, clientId={}",
                            runtimeId, clientId);

                    return findAndDeleteClientRuntimeRef(clientId, runtimeId)
                            .flatMap(voidItem -> getRuntime(runtimeId)
                                    .flatMap(runtime -> {
                                        if (runtime.getDeleted()) {
                                            log.info("Runtime was already deleted, " +
                                                            "DeleteClientRuntimeCommand won't be created, runtimeId={}",
                                                    runtimeId);
                                            return Uni.createFrom().voidItem();
                                        }

                                        return syncDeleteClientRuntimeCommand(runtimeId, clientId);
                                    }));
                })
                .replaceWithVoid();
    }

    Uni<RuntimeClientModel> getRuntimeClient(final Long runtimeId, final Long id) {
        final var request = new GetRuntimeClientRequest(runtimeId, id);
        return runtimeModule.getRuntimeService().getRuntimeClient(request)
                .map(GetRuntimeClientResponse::getRuntimeClient);
    }

    Uni<RuntimeModel> getRuntime(final Long runtimeId) {
        final var request = new GetRuntimeRequest(runtimeId);
        return runtimeModule.getRuntimeService().getRuntime(request)
                .map(GetRuntimeResponse::getRuntime);
    }

    Uni<Boolean> syncDeleteClientRuntimeCommand(final Long runtimeId,
                                                final Long clientId) {
        final var runtimeCommandBody = new DeleteClientRuntimeCommandBodyModel(clientId);
        final var runtimeCommand = runtimeCommandModelFactory.create(runtimeId, runtimeCommandBody);
        return syncRuntimeCommand(runtimeCommand);
    }

    Uni<Boolean> syncRuntimeCommand(final RuntimeCommandModel runtimeCommand) {
        final var request = new SyncRuntimeCommandRequest(runtimeCommand);
        return runtimeModule.getRuntimeService().syncRuntimeCommand(request)
                .map(SyncRuntimeCommandResponse::getCreated);
    }

    Uni<Void> findAndDeleteClientRuntimeRef(final Long clientId, final Long runtimeId) {
        return findClientRuntimeRef(clientId, runtimeId)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithNull()
                .onItem().ifNotNull().transformToUni(clientRuntimeRef ->
                        deleteClientRuntimeRef(clientId, clientRuntimeRef.getId()))
                .replaceWithVoid();
    }

    Uni<ClientRuntimeRefModel> findClientRuntimeRef(final Long clientId, final Long runtimeId) {
        final var request = new FindClientRuntimeRefRequest(clientId, runtimeId);
        return clientModule.getClientService().findClientRuntimeRef(request)
                .map(FindClientRuntimeRefResponse::getClientRuntimeRef);
    }

    Uni<Boolean> deleteClientRuntimeRef(final Long clientId, final Long id) {
        final var request = new DeleteClientRuntimeRefRequest(clientId, id);
        return clientModule.getClientService().deleteClientRuntimeRef(request)
                .map(DeleteClientRuntimeRefResponse::getDeleted);
    }
}
