package com.omgservers.service.handler.client;

import com.omgservers.model.clientMessage.ClientMessageModel;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandRequest;
import com.omgservers.model.dto.matchmaker.SyncMatchmakerCommandResponse;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.ClientDeletedEventBodyModel;
import com.omgservers.model.matchmakerCommand.body.DeleteClientMatchmakerCommandBodyModel;
import com.omgservers.service.factory.EntityModelFactory;
import com.omgservers.service.factory.MatchmakerCommandModelFactory;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.service.module.user.UserModule;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ClientDeletedEventHandlerImpl implements EventHandler {

    final MatchmakerModule matchmakerModule;
    final RuntimeModule runtimeModule;
    final ClientModule clientModule;
    final SystemModule systemModule;
    final UserModule userModule;

    final MatchmakerCommandModelFactory matchmakerCommandModelFactory;
    final EntityModelFactory entityModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.CLIENT_DELETED;
    }

    @Override
    public Uni<Boolean> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (ClientDeletedEventBodyModel) event.getBody();
        final var clientId = body.getId();

        return clientModule.getShortcutService().getClient(clientId)
                .flatMap(client -> {
                    log.info("Client was deleted, clientId={}", clientId);

                    return systemModule.getShortcutService().findAndDeleteEntity(clientId)
                            .flatMap(entityDeleted -> deleteClientMessages(clientId)
                                    .flatMap(voidItem -> deleteClientRuntimes(clientId))
                                    .flatMap(voidItem -> {
                                        final var matchmakerId = client.getMatchmakerId();
                                        return syncDeleteClientMatchmakerCommand(matchmakerId, clientId);

                                    }));
                })
                .replaceWith(true);
    }

    Uni<Void> deleteClientMessages(final Long clientId) {
        return clientModule.getShortcutService().viewClientMessages(clientId)
                .flatMap(clientMessages -> {
                            final var clientMessageIds = clientMessages.stream()
                                    .map(ClientMessageModel::getId)
                                    .toList();
                            return clientModule.getShortcutService().deleteClientMessages(clientId, clientMessageIds)
                                    .replaceWithVoid();
                        }
                );
    }

    Uni<Void> deleteClientRuntimes(final Long clientId) {
        return clientModule.getShortcutService().viewClientRuntimes(clientId)
                .flatMap(clientRuntimes -> Multi.createFrom().iterable(clientRuntimes)
                        .onItem().transformToUniAndConcatenate(clientRuntime ->
                                clientModule.getShortcutService().deleteClientRuntime(clientId, clientRuntime.getId())
                                        .onFailure()
                                        .recoverWithItem(t -> {
                                            log.warn("Delete client runtime failed, " +
                                                            "clientRuntime={}/{}, " +
                                                            "{}:{}",
                                                    clientRuntime.getClientId(),
                                                    clientRuntime.getId(),
                                                    t.getClass().getSimpleName(),
                                                    t.getMessage());
                                            return null;
                                        })
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<Boolean> syncDeleteClientMatchmakerCommand(final Long matchmakerId,
                                                   final Long clientId) {
        final var commandBody = new DeleteClientMatchmakerCommandBodyModel(clientId);
        final var commandModel = matchmakerCommandModelFactory.create(matchmakerId, commandBody);
        final var request = new SyncMatchmakerCommandRequest(commandModel);
        return matchmakerModule.getMatchmakerService().syncMatchmakerCommand(request)
                .map(SyncMatchmakerCommandResponse::getCreated);
    }
}

