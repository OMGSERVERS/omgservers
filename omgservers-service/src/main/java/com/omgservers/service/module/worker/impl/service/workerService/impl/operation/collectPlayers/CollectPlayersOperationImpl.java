package com.omgservers.service.module.worker.impl.service.workerService.impl.operation.collectPlayers;

import com.omgservers.model.player.PlayerModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.body.AddClientRuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.ChangePlayerRuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.SignInRuntimeCommandBodyModel;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.user.UserModule;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CollectPlayersOperationImpl implements CollectPlayersOperation {

    static final Set<RuntimeCommandQualifierEnum> CLIENT_RELATED_COMMANDS = Set.of(
            RuntimeCommandQualifierEnum.SIGN_IN,
            RuntimeCommandQualifierEnum.CHANGE_PLAYER,
            RuntimeCommandQualifierEnum.ADD_CLIENT
    );

    final RuntimeModule runtimeModule;
    final UserModule userModule;

    @Override
    public Uni<List<PlayerModel>> collectPlayers(List<RuntimeCommandModel> runtimeCommands) {
        final var clientKeys = runtimeCommands.stream()
                .filter(runtimeCommand -> CLIENT_RELATED_COMMANDS.contains(runtimeCommand.getQualifier()))
                .map(runtimeCommand -> switch (runtimeCommand.getQualifier()) {
                    case SIGN_IN -> {
                        final var body = (SignInRuntimeCommandBodyModel) runtimeCommand.getBody();
                        yield new ClientKey(body.getUserId(), body.getClientId());
                    }
                    case CHANGE_PLAYER -> {
                        final var body = (ChangePlayerRuntimeCommandBodyModel) runtimeCommand.getBody();
                        yield new ClientKey(body.getUserId(), body.getClientId());
                    }
                    case ADD_CLIENT -> {
                        final var body = (AddClientRuntimeCommandBodyModel) runtimeCommand.getBody();
                        yield new ClientKey(body.getUserId(), body.getClientId());
                    }
                    default -> throw new ServerSideConflictException("internal mismatch for " +
                            "qualifier=" + runtimeCommand.getQualifier());
                })
                .distinct()
                .toList();

        return Multi.createFrom().iterable(clientKeys)
                .onItem().transformToUniAndMerge(clientKey -> {
                    final var userId = clientKey.userId();
                    final var clientId = clientKey.clientId();
                    // TODO: do getClient and getPlayer in one request
                    return userModule.getShortcutService().getClient(userId, clientId)
                            .flatMap(client -> {
                                final var playerId = client.getPlayerId();
                                return userModule.getShortcutService().getPlayer(userId, playerId);
                            })
                            .onFailure()
                            .recoverWithItem(t -> {
                                log.warn("Collect player failed, " +
                                                "client={}/{}, " +
                                                "{}:{}",
                                        userId,
                                        clientId,
                                        t.getClass().getSimpleName(),
                                        t.getMessage());
                                return null;
                            });
                })
                .select().where(Objects::nonNull)
                .collect().asList();
    }

    record ClientKey(Long userId, Long clientId) {
    }
}
