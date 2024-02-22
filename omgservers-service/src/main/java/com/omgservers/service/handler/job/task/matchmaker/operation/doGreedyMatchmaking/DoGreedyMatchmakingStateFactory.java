package com.omgservers.service.handler.job.task.matchmaker.operation.doGreedyMatchmaking;

import com.omgservers.model.match.MatchConfigModel;
import com.omgservers.model.match.MatchModel;
import com.omgservers.model.matchClient.MatchClientConfigModel;
import com.omgservers.model.matchClient.MatchClientModel;
import com.omgservers.model.request.RequestModel;
import com.omgservers.model.version.VersionGroupModel;
import com.omgservers.model.version.VersionModeModel;
import com.omgservers.service.factory.MatchClientModelFactory;
import com.omgservers.service.factory.MatchModelFactory;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@ApplicationScoped
@AllArgsConstructor
public class DoGreedyMatchmakingStateFactory {

    final MatchClientModelFactory matchClientModelFactory;
    final MatchModelFactory matchModelFactory;

    public DoGreedyMatchmakingState build(final VersionModeModel modeConfig,
                                          final List<MatchModel> matches,
                                          final List<MatchClientModel> matchClients) {
        final var matchmakingMatches = matches.stream()
                .map(MatchmakingMatch::new)
                .toList();

        final var indexedMatches = matchmakingMatches.stream()
                .collect(Collectors.toMap(MatchmakingMatch::getId, Function.identity()));

        // Fill in matches by current match clients
        matchClients.forEach(matchClient -> {
            final var match = indexedMatches.get(matchClient.getMatchId());
            if (match != null) {
                match.addMatchClient(matchClient);
            }
        });

        return new DoGreedyMatchmakingState(modeConfig, matchmakingMatches);
    }

    class DoGreedyMatchmakingState {

        final List<MatchmakingMatch> createdMatches;
        final List<MatchmakingMatch> currentMatches;
        final VersionModeModel config;

        public DoGreedyMatchmakingState(VersionModeModel config, List<MatchmakingMatch> allMatches) {
            this.config = config;
            this.currentMatches = allMatches.stream().filter(match -> match.getSize() > 0).collect(Collectors.toList());
            createdMatches = new ArrayList<>();
        }

        void matchRequest(RequestModel request) {
            // First of all trying to match requests with less filled matches

            final var sortedMatches = currentMatches.stream()
                    .sorted(Comparator.comparing(MatchmakingMatch::getSize))
                    .toList();

            boolean matched = false;

            for (var match : sortedMatches) {
                matched = match.matchRequest(request);
                if (matched) {
                    break;
                }
            }

            if (!matched) {
                // Nothing is matched, creating new match
                final var newMatch = createMatch(request.getMatchmakerId());
                newMatch.matchRequest(request);
            }
        }

        DoGreedyMatchmakingResult getResult() {
            final var resultRequests = currentMatches.stream()
                    .filter(MatchmakingMatch::getReadiness)
                    .flatMap(match -> match.getMatchedRequests().stream())
                    .toList();

            final var resultMatches = createdMatches.stream()
                    .filter(MatchmakingMatch::getReadiness)
                    .map(MatchmakingMatch::getMatch)
                    .toList();

            final var resultMatchClients = currentMatches.stream()
                    .filter(MatchmakingMatch::getReadiness)
                    .flatMap(match -> match.getCreatedMatchClients().stream())
                    .toList();

            return new DoGreedyMatchmakingResult(resultRequests, resultMatches, resultMatchClients);
        }

        MatchmakingMatch createMatch(final Long matchmakerId) {
            final var matchConfig = new MatchConfigModel(config);
            final var matchModel = matchModelFactory.create(matchmakerId, matchConfig);
            final var newMatch = new MatchmakingMatch(matchModel);
            createdMatches.add(newMatch);
            currentMatches.add(newMatch);
            return newMatch;
        }
    }

    class MatchmakingMatch {

        List<RequestModel> matchedRequests;
        List<MatchClientModel> createdMatchClients;
        Map<String, MatchmakingGroup> groups;
        VersionModeModel config;
        MatchModel match;
        int size;

        public MatchmakingMatch(final MatchModel match) {
            this.match = match;
            config = match.getConfig().getModeConfig();
            groups = config.getGroups().stream()
                    .map(groupConfig -> new MatchmakingGroup(match.getId(), groupConfig))
                    .collect(Collectors.toMap(MatchmakingGroup::getName, Function.identity()));
            matchedRequests = new ArrayList<>();
            createdMatchClients = new ArrayList<>();
            size = 0;
        }

        Long getId() {
            return match.getId();
        }

        int getSize() {
            return size;
        }

        public MatchModel getMatch() {
            return match;
        }

        public List<MatchClientModel> getCreatedMatchClients() {
            return createdMatchClients;
        }

        public List<RequestModel> getMatchedRequests() {
            return matchedRequests;
        }

        void addMatchClient(final MatchClientModel matchClient) {
            final var group = groups.get(matchClient.getGroupName());
            if (group != null) {
                group.addMatchClient(matchClient);
                size += 1;
            }
        }

        boolean matchRequest(final RequestModel request) {
            final var maxPlayers = config.getMaxPlayers();
            if (size >= maxPlayers) {
                return false;
            }

            // First of all trying to match requests with less filled group

            final var sortedGroups = groups.values().stream()
                    .sorted(Comparator.comparing(MatchmakingGroup::getSize))
                    .toList();
            for (var group : sortedGroups) {
                final var matchClient = group.matchRequest(request);
                if (matchClient != null) {
                    matchedRequests.add(request);
                    createdMatchClients.add(matchClient);
                    size += 1;
                    return true;
                }
            }

            return false;
        }

        boolean getReadiness() {
            final var minPlayers = config.getMinPlayers();
            final var matchReadiness = size >= minPlayers;
            final var groupsReadiness = groups.values().stream()
                    .allMatch(MatchmakingGroup::getReadiness);
            return matchReadiness && groupsReadiness;
        }
    }

    class MatchmakingGroup {

        List<MatchClientModel> matchClients;
        VersionGroupModel config;
        long matchId;

        public MatchmakingGroup(final long matchId, final VersionGroupModel config) {
            this.matchId = matchId;
            this.config = config;

            matchClients = new ArrayList<>();
        }

        String getName() {
            return config.getName();
        }

        void addMatchClient(final MatchClientModel matchClient) {
            matchClients.add(matchClient);
        }

        MatchClientModel matchRequest(final RequestModel request) {
            final var maxPlayers = config.getMaxPlayers();
            if (matchClients.size() >= maxPlayers) {
                return null;
            }

            return createMatchClient(request);
        }

        boolean getReadiness() {
            final var minPlayers = config.getMinPlayers();
            final var readiness = matchClients.size() >= minPlayers;
            return readiness;
        }

        int getSize() {
            return matchClients.size();
        }

        MatchClientModel createMatchClient(RequestModel request) {
            final var matchmakerId = request.getMatchmakerId();
            final var userId = request.getUserId();
            final var clientId = request.getClientId();
            final var newMatchClient = matchClientModelFactory.create(matchmakerId,
                    matchId,
                    userId,
                    clientId,
                    getName(),
                    new MatchClientConfigModel(request));
            matchClients.add(newMatchClient);
            return newMatchClient;
        }
    }
}
