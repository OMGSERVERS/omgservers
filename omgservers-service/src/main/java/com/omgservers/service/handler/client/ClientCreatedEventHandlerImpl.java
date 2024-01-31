package com.omgservers.service.handler.client;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.entitiy.EntityQualifierEnum;
import com.omgservers.model.event.EventModel;
import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.event.body.ClientCreatedEventBodyModel;
import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.WelcomeMessageBodyModel;
import com.omgservers.service.factory.ClientMessageModelFactory;
import com.omgservers.service.factory.ClientRuntimeModelFactory;
import com.omgservers.service.factory.EntityModelFactory;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.system.impl.service.handlerService.impl.EventHandler;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ClientCreatedEventHandlerImpl implements EventHandler {

    final TenantModule tenantModule;
    final ClientModule clientModule;
    final SystemModule systemModule;

    final ClientMessageModelFactory clientMessageModelFactory;
    final ClientRuntimeModelFactory clientRuntimeModelFactory;
    final EntityModelFactory entityModelFactory;

    @Override
    public EventQualifierEnum getQualifier() {
        return EventQualifierEnum.CLIENT_CREATED;
    }

    @Override
    public Uni<Boolean> handle(final EventModel event) {
        log.debug("Handle event, {}", event);

        final var body = (ClientCreatedEventBodyModel) event.getBody();
        final var clientId = body.getId();

        return clientModule.getShortcutService().getClient(clientId)
                .flatMap(client -> {
                    final var tenantId = client.getTenantId();
                    final var versionId = client.getVersionId();

                    log.info("Client was created, clientId={}, version={}/{}",
                            clientId, tenantId, versionId);

                    return syncEntity(clientId)
                            .flatMap(entityCreated -> syncWelcomeMessage(client))
                            .flatMap(welcomeMessageCreated -> {
                                return tenantModule.getShortcutService().selectRandomVersionRuntime(tenantId, versionId)
                                        .flatMap(versionRuntime -> {
                                            final var runtimeId = versionRuntime.getRuntimeId();
                                            return syncClientRuntime(clientId, runtimeId);
                                        });
                            });
                })
                .replaceWith(true);
    }

    Uni<Boolean> syncEntity(final Long clientId) {
        final var entity = entityModelFactory.create(clientId, EntityQualifierEnum.CLIENT);
        return systemModule.getShortcutService().syncEntity(entity);
    }

    Uni<Boolean> syncWelcomeMessage(final ClientModel client) {
        final var clientId = client.getId();
        final var tenantId = client.getTenantId();
        final var versionId = client.getVersionId();

        final var messageBody = new WelcomeMessageBodyModel(tenantId, versionId);
        final var clientMessage = clientMessageModelFactory.create(clientId,
                MessageQualifierEnum.WELCOME_MESSAGE,
                messageBody);
        return clientModule.getShortcutService().syncClientMessage(clientMessage);
    }

    Uni<Boolean> syncClientRuntime(final Long clientId, final Long runtimeId) {
        final var clientRuntime = clientRuntimeModelFactory.create(clientId, runtimeId);
        return clientModule.getShortcutService().syncClientRuntime(clientRuntime);
    }
}

