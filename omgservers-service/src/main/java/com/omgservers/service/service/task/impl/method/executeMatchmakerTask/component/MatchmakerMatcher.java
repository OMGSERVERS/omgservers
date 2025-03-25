package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.component;

import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentConfigDto;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import com.omgservers.schema.model.matchmakerRequest.MatchmakerRequestModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionModeDto;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeSet;

@Slf4j
public class MatchmakerMatcher {

    @Getter
    final TenantVersionModeDto config;

    final TreeSet<MatcherMatch> matchesSortedSet;
    final List<MatcherMatch> newlyCreatedMatches;
    final Map<Long, MatcherMatch> matchById;

    public MatchmakerMatcher(final TenantVersionModeDto config) {
        this.config = config;

        matchesSortedSet = new TreeSet<>(Comparator.comparing(MatcherMatch::getSize)
                // To get predictable and reproducible matchmaking results
                .thenComparingLong(MatcherMatch::getMatchId));
        newlyCreatedMatches = new ArrayList<>();
        matchById = new HashMap<>();
    }

    public void addMatchmakerMatchResource(final MatchmakerMatchResourceModel matchmakerMatchResource,
                                           final boolean newlyCreated) {
        final var matchId = matchmakerMatchResource.getMatchId();
        final var matcherMatch = new MatcherMatch(config, matchmakerMatchResource);
        matchById.put(matchId, matcherMatch);
        matchesSortedSet.add(matcherMatch);
        if (newlyCreated) {
            newlyCreatedMatches.add(matcherMatch);
        }
    }

    public void addMatchmakerMatchResource(final MatchmakerMatchResourceModel matchmakerMatchResource) {
        addMatchmakerMatchResource(matchmakerMatchResource, false);
    }

    public void addMatchmakerMatchAssignment(final MatchmakerMatchAssignmentModel matchmakerMatchAssignment,
                                             final boolean newlyCreated) {
        final var matchId = matchmakerMatchAssignment.getMatchId();
        final var matcherMatch = matchById.get(matchId);
        if (Objects.nonNull(matcherMatch)) {
            matcherMatch.addAssignment(matchmakerMatchAssignment, newlyCreated);
        } else {
            log.error("Failed to add assignment, match \"{}\" was not found", matchId);
        }
    }

    public void addMatchmakerMatchAssignment(final MatchmakerMatchAssignmentModel matchmakerMatchAssignment) {
        addMatchmakerMatchAssignment(matchmakerMatchAssignment, false);
    }

    public Optional<MatchingResult> matchRequest(final MatchmakerRequestModel matchmakerRequest) {
        for (final var matcherMatch : matchesSortedSet) {
            final var matcherGroupOptional = matcherMatch.matchRequest(matchmakerRequest);
            if (matcherGroupOptional.isPresent()) {
                final var matcherGroup = matcherGroupOptional.get();
                return Optional.of(new MatchingResult(matcherMatch, matcherGroup));
            }
        }

        return Optional.empty();
    }

    public List<MatchmakerMatchResourceModel> getMatchmakerMatchResourcesToSync() {
        return newlyCreatedMatches.stream()
                .filter(MatcherMatch::checkReadiness)
                .map(MatcherMatch::getMatchmakerMatchResource)
                .toList();
    }

    public List<MatchmakerMatchAssignmentModel> getMatchmakerMatchAssignmentsToSync() {
        return matchesSortedSet.stream()
                .filter(MatcherMatch::checkReadiness)
                .flatMap(matcherMatch -> matcherMatch.getMatchmakerMatchAssignmentToSync().stream())
                .toList();
    }

    public List<Long> getMatchmakerRequestsToDelete() {
        return matchesSortedSet.stream()
                .filter(MatcherMatch::checkReadiness)
                .flatMap(matcherMatch -> matcherMatch.getMatchmakerMatchAssignmentToSync().stream())
                .map(MatchmakerMatchAssignmentModel::getConfig)
                .map(MatchmakerMatchAssignmentConfigDto::getMatchmakerRequest)
                .map(MatchmakerRequestModel::getId)
                .toList();
    }
}
