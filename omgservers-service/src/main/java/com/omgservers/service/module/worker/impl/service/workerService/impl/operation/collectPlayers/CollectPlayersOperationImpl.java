package com.omgservers.service.module.worker.impl.service.workerService.impl.operation.collectPlayers;

import com.omgservers.model.player.PlayerModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.body.AddClientRuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.DeleteClientRuntimeCommandBodyModel;
import com.omgservers.model.runtimeCommand.body.HandleMessageRuntimeCommandBodyModel;
import com.omgservers.service.exception.ServerSideInternalException;
import com.omgservers.service.module.client.ClientModule;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.user.UserModule;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CollectPlayersOperationImpl implements CollectPlayersOperation {

    static final Set<RuntimeCommandQualifierEnum> CLIENT_RELATED_COMMANDS = Set.of(
            RuntimeCommandQualifierEnum.ADD_CLIENT,
            RuntimeCommandQualifierEnum.DELETE_CLIENT,
            RuntimeCommandQualifierEnum.HANDLE_MESSAGE
    );

    final RuntimeModule runtimeModule;
    final ClientModule clientModule;
    final UserModule userModule;

    @Override
    public Uni<Map<Long, PlayerModel>> collectPlayers(List<RuntimeCommandModel> runtimeCommands) {
        final var clients = runtimeCommands.stream()
                .filter(runtimeCommand -> CLIENT_RELATED_COMMANDS.contains(runtimeCommand.getQualifier()))
                .map(runtimeCommand -> switch (runtimeCommand.getQualifier()) {
                    case ADD_CLIENT -> {
                        final var body = (AddClientRuntimeCommandBodyModel) runtimeCommand.getBody();
                        yield body.getClientId();
                    }
                    case DELETE_CLIENT -> {
                        final var body = (DeleteClientRuntimeCommandBodyModel) runtimeCommand.getBody();
                        yield body.getClientId();
                    }
                    case HANDLE_MESSAGE -> {
                        final var body = (HandleMessageRuntimeCommandBodyModel) runtimeCommand.getBody();
                        yield body.getClientId();
                    }
                    default -> throw new ServerSideInternalException("runtime command qualifier mismatch");
                })
                .distinct()
                .toList();

        return Multi.createFrom().iterable(clients)
                .onItem().transformToUniAndMerge(clientId -> {
                    // TODO: do getClient and getPlayer in one request
                    return clientModule.getShortcutService().getClient(clientId)
                            .flatMap(client -> {
                                final var userId = client.getUserId();
                                final var playerId = client.getPlayerId();
                                return userModule.getShortcutService().getPlayer(userId, playerId)
                                        .map(player -> new ClientPlayer(client.getId(), player));
                            })
                            .onFailure()
                            .recoverWithItem(t -> {
                                log.warn("Collect player failed, " +
                                                "clientId={}, " +
                                                "{}:{}",
                                        clientId,
                                        t.getClass().getSimpleName(),
                                        t.getMessage());
                                return null;
                            });
                })
                .select().where(Objects::nonNull)
                .collect().asList()
                .map(list -> list.stream()
                        .collect(Collectors.toMap(ClientPlayer::clientId, ClientPlayer::player)));
    }

    record ClientPlayer(Long clientId, PlayerModel player) {
    }
}
