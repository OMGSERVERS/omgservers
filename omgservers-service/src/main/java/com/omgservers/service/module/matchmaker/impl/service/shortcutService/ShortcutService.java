package com.omgservers.service.module.matchmaker.impl.service.shortcutService;

import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.model.matchCommand.MatchCommandModel;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface ShortcutService {

    Uni<MatchModel> getMatch(Long matchmakerId, Long matchId);

    Uni<List<MatchCommandModel>> viewMatchCommands(Long matchmakerId, Long matchId);

    Uni<Boolean> deleteMatchCommand(Long matchmakerId, Long id);

    Uni<Void> deleteMatchCommands(Long matchmakerId, Long matchId);

    Uni<List<MatchClientModel>> viewMatchClients(Long matchmakerId, Long matchId);

    Uni<Boolean> deleteMatchClient(Long matchmakerId, Long id);

    Uni<Void> deleteMatchClients(Long matchmakerId, Long matchId);
}
