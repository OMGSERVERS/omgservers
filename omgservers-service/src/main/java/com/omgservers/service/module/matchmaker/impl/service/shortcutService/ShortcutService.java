package com.omgservers.service.module.matchmaker.impl.service.shortcutService;

import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.model.matchCommand.MatchCommandModel;
import com.omgservers.model.matchmaker.MatchmakerModel;
import com.omgservers.model.matchmakerCommand.MatchmakerCommandModel;
import com.omgservers.model.request.RequestModel;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface ShortcutService {

    Uni<MatchmakerModel> getMatchmaker(Long matchmakerId);

    Uni<Boolean> deleteMatchmaker(Long matchmakerId);

    Uni<List<MatchmakerCommandModel>> viewMatchmakerCommands(Long matchmakerId);

    Uni<Boolean> deleteMatchmakerCommand(Long matchmakerId, Long id);

    Uni<Void> deleteMatchmakerCommands(Long matchmakerId);

    Uni<MatchModel> getMatch(Long matchmakerId, Long matchId);

    Uni<List<MatchModel>> viewMatches(Long matchmakerId);

    Uni<Boolean> deleteMatch(Long matchmakerId, Long id);

    Uni<Void> deleteMatches(Long matchmakerId);

    Uni<List<MatchCommandModel>> viewMatchCommands(Long matchmakerId, Long matchId);

    Uni<Boolean> deleteMatchCommand(Long matchmakerId, Long id);

    Uni<Void> deleteMatchCommands(Long matchmakerId, Long matchId);

    Uni<MatchClientModel> getMatchClient(Long matchmakerId, Long id);

    Uni<List<MatchClientModel>> viewMatchClients(Long matchmakerId, Long matchId);

    Uni<Boolean> deleteMatchClient(Long matchmakerId, Long id);

    Uni<Void> deleteMatchClients(Long matchmakerId, Long matchId);

    Uni<List<RequestModel>> viewRequests(Long matchmakerId);

    Uni<Boolean> deleteRequest(Long matchmakerId, Long id);

    Uni<Void> deleteRequests(Long matchmakerId);

}
