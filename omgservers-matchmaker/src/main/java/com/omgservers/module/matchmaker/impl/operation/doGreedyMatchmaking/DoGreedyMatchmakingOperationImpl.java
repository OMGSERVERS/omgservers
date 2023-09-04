package com.omgservers.module.matchmaker.impl.operation.doGreedyMatchmaking;

import com.omgservers.model.match.MatchConfigModel;
import com.omgservers.model.match.MatchGroupModel;
import com.omgservers.model.match.MatchModel;
import com.omgservers.model.request.RequestModel;
import com.omgservers.model.version.VersionModeModel;
import com.omgservers.module.matchmaker.factory.MatchClientModelFactory;
import com.omgservers.module.matchmaker.factory.MatchModelFactory;
import com.omgservers.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DoGreedyMatchmakingOperationImpl implements DoGreedyMatchmakingOperation {

    final MatchClientModelFactory matchClientModelFactory;
    final MatchModelFactory matchModelFactory;

    final GenerateIdOperation generateIdOperation;

    @Override
    public DoGreedyMatchmakingResult doGreedyMatchmaking(final Long tenantId,
                                                         final Long stageId,
                                                         final Long versionId,
                                                         final Long matchmakerId,
                                                         final VersionModeModel modeConfig,
                                                         final List<RequestModel> matchmakerRequests,
                                                         final List<MatchModel> matchmakerMatches) {
        final var currentMatches = new ArrayList<>(matchmakerMatches);
        final var createdMatches = new HashSet<MatchModel>();
        final var updatedMatches = new HashSet<MatchModel>();

        final var matchedRequests = new HashMap<RequestModel, MatchModel>();
        final var completedRequests = new ArrayList<RequestModel>();

        matchmakerRequests.forEach(request -> {
            boolean matched = false;

            final var sortedMatches = currentMatches.stream()
                    .sorted(Comparator.comparing(this::countMatchRequests)).toList();
            for (var match : sortedMatches) {
                matched = matchRequestWithMatch(request, match);
                if (matched) {
                    if (!createdMatches.contains(match)) {
                        updatedMatches.add(match);
                    }
                    matchedRequests.put(request, match);
                    break;
                }
            }

            if (!matched) {
                final var matchConfig = MatchConfigModel.create(tenantId, stageId, versionId, modeConfig);
                final var newMatch = matchModelFactory.create(matchmakerId, matchConfig);
                currentMatches.add(newMatch);

                matched = matchRequestWithMatch(request, newMatch);
                if (matched) {
                    createdMatches.add(newMatch);
                    matchedRequests.put(request, newMatch);
                } else {
                    log.warn("Request can't be matched event with new match, request={}", request);
                    completedRequests.add(request);
                }
            }
        });

        final var resultCreatedMatches = createdMatches.stream()
                .filter(this::checkMatchReadiness)
                .sorted(Comparator.comparing(MatchModel::getId))
                .toList();

        final var resultUpdatedMatches = updatedMatches.stream()
                .filter(this::checkMatchReadiness)
                .sorted(Comparator.comparing(MatchModel::getId))
                .toList();

        final var resultMatchedClients = Stream.concat(
                        resultCreatedMatches.stream(),
                        resultUpdatedMatches.stream())
                .flatMap(match -> getMatchRequests(match).stream())
                .filter(matchedRequests::containsKey)
                .map(request -> {
                    completedRequests.add(request);
                    final var match = matchedRequests.get(request);
                    final var matchClient = matchClientModelFactory.create(
                            matchmakerId,
                            match.getId(),
                            request.getUserId(),
                            request.getClientId());
                    return matchClient;
                })
                .toList();

        // failed + matched requests
        final var resultCompletedRequests = completedRequests.stream().toList();

        return new DoGreedyMatchmakingResult(
                resultCreatedMatches,
                resultUpdatedMatches,
                resultMatchedClients,
                resultCompletedRequests);
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
                .sorted(Comparator.comparing(g -> g.getRequests().size())).toList();
        for (var group : sortedGroups) {
            if (matchRequestWithGroup(request, group)) {
                log.info("RequestId={} was matched with matchId={}, groupId={}",
                        request.getId(), match.getId(), group.getConfig().getName());
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
