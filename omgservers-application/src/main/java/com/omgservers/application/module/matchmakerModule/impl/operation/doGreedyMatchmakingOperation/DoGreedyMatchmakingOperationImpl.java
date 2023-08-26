package com.omgservers.application.module.matchmakerModule.impl.operation.doGreedyMatchmakingOperation;

import com.omgservers.application.factory.MatchModelFactory;
import com.omgservers.base.operation.generateId.GenerateIdOperation;
import com.omgservers.model.match.MatchConfigModel;
import com.omgservers.model.match.MatchGroupModel;
import com.omgservers.model.match.MatchModel;
import com.omgservers.model.request.RequestModel;
import com.omgservers.model.version.VersionModeModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DoGreedyMatchmakingOperationImpl implements DoGreedyMatchmakingOperation {

    final MatchModelFactory matchModelFactory;
    final GenerateIdOperation generateIdOperation;

    @Override
    public GreedyMatchmakingResult doGreedyMatchmaking(final Long tenantId,
                                                       final Long stageId,
                                                       final Long versionId,
                                                       final Long matchmakerId,
                                                       final VersionModeModel modeConfig,
                                                       final List<RequestModel> activeRequests,
                                                       final List<MatchModel> launchedMatches) {
        // Temporary do not update already launched matches
        // List<MatchModel> temporaryMatches = new ArrayList<>(launchedMatches);
        List<MatchModel> temporaryMatches = new ArrayList<>();
        List<RequestModel> failedRequests = new ArrayList<>();

        activeRequests.forEach(request -> {
            boolean matched = false;

            final var sortedMatches = temporaryMatches.stream()
                    .sorted(Comparator.comparingInt(this::countMatchRequests)).toList();
            for (var match : sortedMatches) {
                matched = matchRequestWithMatch(request, match);
                if (matched) {
                    break;
                }
            }

            if (!matched) {
                final var matchConfig = MatchConfigModel.create(tenantId, stageId, versionId, modeConfig);
                final var newMatch = matchModelFactory.create(matchmakerId, generateIdOperation.generateId(), matchConfig);
                temporaryMatches.add(newMatch);

                if (!matchRequestWithMatch(request, newMatch)) {
                    // Request can't be matched even with new match
                    failedRequests.add(request);
                }
            }
        });

        final var preparedMatches = temporaryMatches.stream()
                .filter(this::checkMatchReadiness)
                .toList();

        final var matchedRequests = preparedMatches.stream()
                .flatMap(match -> getMatchRequests(match).stream())
                .toList();

        return new GreedyMatchmakingResult(matchedRequests, failedRequests, preparedMatches);
    }

    boolean matchRequestWithMatch(RequestModel request, MatchModel match) {
        final var matchGroups = match.getConfig().getGroups();
        final var modeConfig = match.getConfig().getModeConfig();

        final var maxPlayers = modeConfig.getMaxPlayers();
        final var countRequests = countMatchRequests(match);
        if (countRequests >= maxPlayers) {
            return false;
        }

        final var sortedGroups = matchGroups.stream()
                .sorted(Comparator.comparingInt(g -> g.getRequests().size())).toList();
        for (var group : sortedGroups) {
            if (matchRequestWithGroup(request, group)) {
                return true;
            }
        }

        return false;
    }

    boolean matchRequestWithGroup(RequestModel request, MatchGroupModel group) {
        final var groupConfig = group.getConfig();
        final var groupRequests = group.getRequests();

        final var maxPlayers = groupConfig.getMaxPlayers();
        final var countRequests = groupRequests.size();
        if (countRequests >= maxPlayers) {
            return false;
        }

        groupRequests.add(request);
        return true;
    }

    int countMatchRequests(MatchModel match) {
        return match.getConfig().getGroups().stream()
                .map(group -> group.getRequests().size())
                .reduce(0, Integer::sum);
    }

    List<RequestModel> getMatchRequests(MatchModel match) {
        final var matchRequests = new ArrayList<RequestModel>();
        match.getConfig().getGroups().stream()
                .flatMap(group -> group.getRequests().stream())
                .forEach(matchRequests::add);
        return matchRequests;
    }

    boolean checkMatchReadiness(MatchModel match) {
        final var minPlayers = match.getConfig().getModeConfig().getMinPlayers();
        final var countMatchRequests = countMatchRequests(match);
        final var matchReadiness = countMatchRequests >= minPlayers;
        final var groupsReadiness = match.getConfig().getGroups().stream()
                .allMatch(this::checkGroupReadiness);
        return matchReadiness && groupsReadiness;
    }

    boolean checkGroupReadiness(MatchGroupModel group) {
        final var groupConfig = group.getConfig();
        final var groupRequests = group.getRequests();
        final var minPlayers = groupConfig.getMinPlayers();
        final var groupReadiness = groupRequests.size() >= minPlayers;
        return groupReadiness;
    }
}
