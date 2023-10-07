package com.omgservers.model.matchmakerState;

import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchClient.MatchClientModel;
import lombok.Getter;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public class IndexedMatchmakerState {

    final MatchmakerState matchmakerState;

    final Map<Long, MatchModel> matchById;
    final Map<Long, MatchClientModel> matchClientById;

    public IndexedMatchmakerState(MatchmakerState matchmakerState) {
        this.matchmakerState = matchmakerState;

        matchById = matchmakerState.getMatches().stream()
                .collect(Collectors.toMap(MatchModel::getId, Function.identity()));

        matchClientById = matchmakerState.getMatchClients().stream()
                .collect(Collectors.toMap(MatchClientModel::getId, Function.identity()));
    }

    public Optional<MatchModel> getMatch(Long id) {
        return Optional.ofNullable(matchById.get(id));
    }

    public Optional<MatchClientModel> getMatchClient(Long id) {
        return Optional.ofNullable(matchClientById.get(id));
    }
}
