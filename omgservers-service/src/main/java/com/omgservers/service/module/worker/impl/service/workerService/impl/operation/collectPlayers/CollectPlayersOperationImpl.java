package com.omgservers.service.module.worker.impl.service.workerService.impl.operation.collectPlayers;

import com.omgservers.model.client.ClientModel;
import com.omgservers.model.dto.user.GetClientRequest;
import com.omgservers.model.dto.user.GetClientResponse;
import com.omgservers.model.dto.user.GetPlayerRequest;
import com.omgservers.model.dto.user.GetPlayerResponse;
import com.omgservers.model.player.PlayerModel;
import com.omgservers.model.recipient.Recipient;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
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
import java.util.Set;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CollectPlayersOperationImpl implements CollectPlayersOperation {

    static final Set<RuntimeCommandQualifierEnum> PLAYER_RELATED = Set.of(
            RuntimeCommandQualifierEnum.SIGN_IN,
            RuntimeCommandQualifierEnum.CHANGE_PLAYER
    );

    final RuntimeModule runtimeModule;
    final UserModule userModule;

    @Override
    public Uni<List<PlayerModel>> collectPlayers(List<RuntimeCommandModel> runtimeCommands) {
        final var recipients = runtimeCommands.stream()
                .filter(runtimeCommand -> PLAYER_RELATED.contains(runtimeCommand.getQualifier()))
                .map(runtimeCommand -> switch (runtimeCommand.getQualifier()) {
                    case SIGN_IN -> {
                        final var body = (SignInRuntimeCommandBodyModel) runtimeCommand.getBody();
                        yield new Recipient(body.getUserId(), body.getClientId());
                    }
                    case CHANGE_PLAYER -> {
                        final var body = (ChangePlayerRuntimeCommandBodyModel) runtimeCommand.getBody();
                        yield new Recipient(body.getUserId(), body.getClientId());
                    }
                    default -> throw new ServerSideConflictException("internal misconfiguration for " +
                            "qualifier=" + runtimeCommand.getQualifier());
                })
                .toList();

        return Multi.createFrom().iterable(recipients)
                .onItem().transformToUniAndMerge(recipient -> {
                    final var userId = recipient.userId();
                    final var clientId = recipient.clientId();
                    return getClient(userId, clientId)
                            .flatMap(client -> {
                                final var playerId = client.getPlayerId();
                                return getPlayer(userId, playerId);
                            });
                })
                .collect().asList();
    }

    Uni<ClientModel> getClient(final Long userId, final Long clientId) {
        final var request = new GetClientRequest(userId, clientId, false);
        return userModule.getClientService().getClient(request)
                .map(GetClientResponse::getClient);
    }

    Uni<PlayerModel> getPlayer(final Long userId, final Long id) {
        final var request = new GetPlayerRequest(userId, id, false);
        return userModule.getPlayerService().getPlayer(request)
                .map(GetPlayerResponse::getPlayer);
    }
}
