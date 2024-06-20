package com.omgservers.service.handler.job.matchmaker.operation.doGreedyMatchmaking;

import com.omgservers.model.matchmakerMatch.MatchmakerMatchConfigModel;
import com.omgservers.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.model.matchmakerMatchClient.MatchmakerMatchClientConfigModel;
import com.omgservers.model.matchmakerMatchClient.MatchmakerMatchClientModel;
import com.omgservers.model.request.MatchmakerRequestModel;
import com.omgservers.model.version.VersionGroupModel;
import com.omgservers.model.version.VersionModeModel;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchClientModelFactory;
import com.omgservers.service.factory.matchmaker.MatchmakerMatchModelFactory;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
@AllArgsConstructor
public class DoGreedyMatchmakingStateFactory {

    final MatchmakerMatchClientModelFactory matchmakerMatchClientModelFactory;
    final MatchmakerMatchModelFactory matchmakerMatchModelFactory;

    public DoGreedyMatchmakingState build(final Long matchmakerId,
                                          final VersionModeModel config,
                                          final List<MatchmakerMatchModel> matches,
                                          final List<MatchmakerMatchClientModel> clients) {
        final var matchmakingMatches = matches.stream()
                .map(MatchmakingMatch::new)
                .toList();

        final var indexedMatches = matchmakingMatches.stream()
                .collect(Collectors.toMap(MatchmakingMatch::getId, Function.identity()));

        // Fill in matches by current match clients
        clients.forEach(matchClient -> {
            final var match = indexedMatches.get(matchClient.getMatchId());
            if (match != null) {
                match.addClient(matchClient);
            }
        });

        return new DoGreedyMatchmakingState(matchmakerId, config, matchmakingMatches);
    }

    class DoGreedyMatchmakingState {

        final List<MatchmakerRequestModel> failedRequests;
        final List<MatchmakingMatch> currentMatches;
        final List<MatchmakingMatch> createdMatches;
        final VersionModeModel modeConfig;
        final Long matchmakerId;

        public DoGreedyMatchmakingState(final Long matchmakerId,
                                        final VersionModeModel modeConfig,
                                        final List<MatchmakingMatch> currentMatches) {
            this.currentMatches = currentMatches;
            this.matchmakerId = matchmakerId;
            this.modeConfig = modeConfig;

            createdMatches = new ArrayList<>();
            failedRequests = new ArrayList<>();
        }

        void matchRequest(MatchmakerRequestModel request) {
            final var allMatches = Stream.concat(currentMatches.stream(), createdMatches.stream())
                    .toList();

            // First of all trying to match requests with less filled matches

            final var sortedMatches = allMatches.stream()
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
                // Nothing was matched, creating new match
                final var newMatch = createMatch(matchmakerId);
                if (!newMatch.matchRequest(request)) {
                    // It wasn't matched with new empty match
                    failedRequests.add(request);
                }
            }
        }

        DoGreedyMatchmakingResult getResult() {
            // Matched requests
            final var resultRequests = currentMatches.stream()
                    .filter(MatchmakingMatch::getReadiness)
                    .flatMap(match -> match.getMatchedRequests().stream())
                    .toList();

            // Created matches
            final var resultMatches = createdMatches.stream()
                    .filter(MatchmakingMatch::getReadiness)
                    .map(MatchmakingMatch::getModel)
                    .toList();

            // Created clients
            final var resultClients = currentMatches.stream()
                    .filter(MatchmakingMatch::getReadiness)
                    .flatMap(match -> match.getCreatedClients().stream())
                    .toList();

            return new DoGreedyMatchmakingResult(resultRequests, resultMatches, resultClients);
        }

        MatchmakingMatch createMatch(final Long matchmakerId) {
            final var matchConfig = new MatchmakerMatchConfigModel(modeConfig);
            final var matchModel = matchmakerMatchModelFactory.create(matchmakerId, matchConfig);
            final var newMatch = new MatchmakingMatch(matchModel);
            createdMatches.add(newMatch);
            return newMatch;
        }
    }

    class MatchmakingMatch {

        final List<MatchmakerMatchClientModel> createdClients;
        final List<MatchmakerRequestModel> matchedRequests;
        final MatchmakerMatchModel matchmakerMatch;
        final Map<String, MatchmakingGroup> groups;
        final VersionModeModel modeConfig;

        int size;

        public MatchmakingMatch(final MatchmakerMatchModel matchmakerMatch) {
            this.matchmakerMatch = matchmakerMatch;

            modeConfig = matchmakerMatch.getConfig().getModeConfig();
            groups = modeConfig.getGroups().stream()
                    .map(groupConfig -> new MatchmakingGroup(matchmakerMatch.getId(), groupConfig))
                    .collect(Collectors.toMap(MatchmakingGroup::getName, Function.identity()));
            matchedRequests = new ArrayList<>();
            createdClients = new ArrayList<>();
            size = 0;
        }

        Long getId() {
            return matchmakerMatch.getId();
        }

        int getSize() {
            return size;
        }

        public MatchmakerMatchModel getModel() {
            return matchmakerMatch;
        }

        public List<MatchmakerMatchClientModel> getCreatedClients() {
            return createdClients;
        }

        public List<MatchmakerRequestModel> getMatchedRequests() {
            return matchedRequests;
        }

        void addClient(final MatchmakerMatchClientModel client) {
            final var group = groups.get(client.getGroupName());
            if (group != null) {
                group.addClient(client);
                size += 1;
            }
        }

        boolean matchRequest(final MatchmakerRequestModel request) {
            final var maxPlayers = modeConfig.getMaxPlayers();
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
                    createdClients.add(matchClient);
                    size += 1;
                    return true;
                }
            }

            return false;
        }

        boolean getReadiness() {
            final var minPlayers = modeConfig.getMinPlayers();
            final var matchReadiness = size >= minPlayers;
            final var groupsReadiness = groups.values().stream()
                    .allMatch(MatchmakingGroup::getReadiness);
            return matchReadiness && groupsReadiness;
        }
    }

    class MatchmakingGroup {

        List<MatchmakerMatchClientModel> clients;
        VersionGroupModel config;
        long matchId;

        public MatchmakingGroup(final long matchId, final VersionGroupModel config) {
            this.matchId = matchId;
            this.config = config;

            clients = new ArrayList<>();
        }

        String getName() {
            return config.getName();
        }

        void addClient(final MatchmakerMatchClientModel matchClient) {
            clients.add(matchClient);
        }

        MatchmakerMatchClientModel matchRequest(final MatchmakerRequestModel request) {
            final var maxPlayers = config.getMaxPlayers();
            if (clients.size() >= maxPlayers) {
                return null;
            }

            return createMatchClient(request);
        }

        boolean getReadiness() {
            final var minPlayers = config.getMinPlayers();
            final var readiness = clients.size() >= minPlayers;
            return readiness;
        }

        int getSize() {
            return clients.size();
        }

        MatchmakerMatchClientModel createMatchClient(final MatchmakerRequestModel request) {
            final var matchmakerId = request.getMatchmakerId();
            final var userId = request.getUserId();
            final var clientId = request.getClientId();
            final var newMatchClient = matchmakerMatchClientModelFactory.create(matchmakerId,
                    matchId,
                    userId,
                    clientId,
                    config.getName(),
                    new MatchmakerMatchClientConfigModel(request));
            clients.add(newMatchClient);
            return newMatchClient;
        }
    }
}
