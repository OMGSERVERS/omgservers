package com.omgservers.service.handler.runtime;

import com.omgservers.model.clientMessage.ClientMessageModel;
import com.omgservers.model.dto.client.SyncClientMessageRequest;
import com.omgservers.model.dto.client.SyncClientMessageResponse;
import com.omgservers.model.dto.client.SyncClientRuntimeRefRequest;
import com.omgservers.model.dto.client.SyncClientRuntimeRefResponse;
import com.omgservers.model.dto.runtime.GetRuntimeClientRequest;
import com.omgservers.model.dto.runtime.GetRuntimeClientResponse;
import com.omgservers.model.dto.runtime.GetRuntimeRequest;
import com.omgservers.model.dto.runtime.GetRuntimeResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.RuntimeClientCreatedEventBodyModel;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.AssignmentMessageBodyModel;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtimeClient.RuntimeClientModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.body.AddClientRuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.AddMatchClientRuntimeCommandBodyModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.ClientMessageModelFactory;
import com.omgservers.service.factory.ClientRuntimeRefModelFactory;
import com.omgservers.service.factory.RuntimeCommandModelFactory;
import com.omgservers.service.handler.EventHandler;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RuntimeClientCreatedEventHandlerImpl implements EventHandler {

    final RuntimeModule runtimeModule;
    final ClientModule clientModule;

    final ClientRuntimeRefModelFactory clientRuntimeRefModelFactory;
    final RuntimeCommandModelFactory runtimeCommandModelFactory;
    final ClientMessageModelFactory clientMessageModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.RUNTIME_CLIENT_CREATED;
    }

    @Override
    public Uni<Void> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (RuntimeClientCreatedEventBodyModel) event.getBody();
        final var runtimeId = body.getRuntimeId();
        final var id = body.getId();

        return getRuntimeClient(runtimeId, id)
                .flatMap(runtimeClient -> {
                    final var clientId = runtimeClient.getClientId();

                    log.info("Runtime client was created, id={}, runtimeId={}, clientId={}",
                            runtimeClient.getId(), runtimeId, clientId);

                    return getRuntime(runtimeId)
                            .flatMap(runtime -> syncAssignmentMessage(runtime, clientId)
                                    .flatMap(created -> syncAddClientRuntimeCommand(runtimeClient))
                                    .flatMap(created -> syncClientRuntimeRef(clientId, runtimeId)));
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

    Uni<Boolean> syncAssignmentMessage(final RuntimeModel runtime,
                                       final Long clientId) {
        final var messageBody = new AssignmentMessageBodyModel(runtime.getId(),
                runtime.getQualifier(),
                runtime.getConfig());
        final var clientMessage = clientMessageModelFactory.create(clientId,
                MessageQualifierEnum.ASSIGNMENT_MESSAGE,
                messageBody);
        return syncClientMessage(clientMessage);
    }

    Uni<Boolean> syncClientMessage(final ClientMessageModel clientMessage) {
        final var request = new SyncClientMessageRequest(clientMessage);
        return clientModule.getClientService().syncClientMessage(request)
                .map(SyncClientMessageResponse::getCreated);
    }

    Uni<Boolean> syncAddClientRuntimeCommand(final RuntimeClientModel runtimeClient) {
        final var clientId = runtimeClient.getClientId();
        final var runtimeId = runtimeClient.getRuntimeId();

        if (Objects.nonNull(runtimeClient.getConfig().getMatchClient())) {
            final var groupName = runtimeClient.getConfig().getMatchClient().getGroupName();
            final var runtimeCommandBody = new AddMatchClientRuntimeCommandBodyModel(clientId, groupName);
            final var runtimeCommand = runtimeCommandModelFactory.create(runtimeId, runtimeCommandBody);
            return syncRuntimeCommand(runtimeCommand);
        } else {
            final var runtimeCommandBody = new AddClientRuntimeCommandBodyModel(clientId);
            final var runtimeCommand = runtimeCommandModelFactory.create(runtimeId, runtimeCommandBody);
            return syncRuntimeCommand(runtimeCommand);
        }
    }

    Uni<Boolean> syncRuntimeCommand(final RuntimeCommandModel runtimeCommand) {
        final var request = new SyncRuntimeCommandRequest(runtimeCommand);
        return runtimeModule.getRuntimeService().syncRuntimeCommand(request)
                .map(SyncRuntimeCommandResponse::getCreated);
    }

    Uni<Boolean> syncClientRuntimeRef(final Long clientId, final Long runtimeId) {
        final var clientRuntimeRef = clientRuntimeRefModelFactory.create(clientId, runtimeId);
        final var request = new SyncClientRuntimeRefRequest(clientRuntimeRef);
        return clientModule.getClientService().syncClientRuntimeRef(request)
                .map(SyncClientRuntimeRefResponse::getCreated)
                .onFailure(ServerSideNotFoundException.class)
                .recoverWithItem(Boolean.FALSE);
    }
}
