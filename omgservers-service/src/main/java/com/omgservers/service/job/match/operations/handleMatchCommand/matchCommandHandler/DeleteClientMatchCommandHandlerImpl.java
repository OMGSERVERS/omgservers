package com.omgservers.service.job.match.operations.handleMatchCommand.matchCommandHandler;

import com.omgservers.model.matchCommand.MatchCommandModel;
import com.omgservers.model.matchCommand.MatchCommandQualifierEnum;
import com.omgservers.model.matchCommand.body.DeleteClientMatchCommandBodyModel;
import com.omgservers.service.factory.ClientRuntimeModelFactory;
import com.omgservers.service.factory.RuntimeCommandModelFactory;
import com.omgservers.service.job.match.operations.handleMatchCommand.MatchCommandHandler;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteClientMatchCommandHandlerImpl implements MatchCommandHandler {

    final MatchmakerModule matchmakerModule;
    final RuntimeModule runtimeModule;
    final ClientModule clientModule;
    final TenantModule tenantModule;

    final RuntimeCommandModelFactory runtimeCommandModelFactory;
    final ClientRuntimeModelFactory clientRuntimeModelFactory;

    @Override
    public MatchCommandQualifierEnum getQualifier() {
        return MatchCommandQualifierEnum.DELETE_CLIENT;
    }

    @Override
    public Uni<Void> handle(MatchCommandModel matchCommand) {
        log.debug("Handle match command, {}", matchCommand);

        final var body = (DeleteClientMatchCommandBodyModel) matchCommand.getBody();
        final var clientId = body.getClientId();

        return clientModule.getShortcutService().getClient(clientId)
                .flatMap(client -> {
                    if (client.getDeleted()) {
                        log.warn("Client was already deleted, " +
                                "client runtime won't be created, clientId={}", clientId);
                        return Uni.createFrom().voidItem();
                    }

                    final var tenantId = client.getTenantId();
                    final var versionId = client.getVersionId();

                    return tenantModule.getShortcutService().selectRandomVersionRuntime(tenantId, versionId)
                            .flatMap(versionRuntime -> {
                                final var runtimeId = versionRuntime.getRuntimeId();
                                return syncClientRuntime(clientId, runtimeId);
                            });
                })
                .replaceWithVoid();
    }

    Uni<Boolean> syncClientRuntime(final Long clientId, final Long runtimeId) {
        final var clientRuntime = clientRuntimeModelFactory.create(clientId, runtimeId);
        return clientModule.getShortcutService().syncClientRuntime(clientRuntime);
    }
}
