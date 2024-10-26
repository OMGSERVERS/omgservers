package com.omgservers.service.service.task.impl.method.executeMatchmakerTask.operation.executeGreedyMatchmaking;

import com.omgservers.schema.model.matchmakerMatch.MatchmakerMatchModel;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.schema.model.request.MatchmakerRequestModel;
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
public class GreedyMatch {

    @Getter
    final MatchmakerMatchModel matchmakerMatch;

    final TenantVersionModeDto config;

    @Getter
    final List<MatchmakerMatchAssignmentModel> matchmakerMatchAssignments;

    final Map<String, GreedyGroup> greedyGroupIndexByName;
    final TreeSet<GreedyGroup> greedyGroupsSortedSet;

    public GreedyMatch(final TenantVersionModeDto config,
                       final MatchmakerMatchModel matchmakerMatch) {
        this.matchmakerMatch = matchmakerMatch;
        this.config = config;

        matchmakerMatchAssignments = new ArrayList<>();

        greedyGroupsSortedSet = new TreeSet<>(Comparator.comparingInt(GreedyGroup::getSize)
                .thenComparing(GreedyGroup::getName));

        greedyGroupIndexByName = new HashMap<>();
        config.getGroups().forEach(groupConfig -> {
            final var groupName = groupConfig.getName();
            final var greedyGroup = new GreedyGroup(groupConfig);
            greedyGroupIndexByName.put(groupName, greedyGroup);
            greedyGroupsSortedSet.add(greedyGroup);
        });
    }

    void addAssignment(final MatchmakerMatchAssignmentModel matchmakerMatchAssignment) {
        final var groupName = matchmakerMatchAssignment.getGroupName();
        final var greedyGroup = greedyGroupIndexByName.get(groupName);
        if (Objects.nonNull(greedyGroup)) {
            greedyGroup.addAssignment(matchmakerMatchAssignment);
            matchmakerMatchAssignments.add(matchmakerMatchAssignment);
        } else {
            log.error("Failed to add assignment, group was not found, {}", matchmakerMatchAssignment);
        }
    }

    Optional<GreedyGroup> matchRequest(final MatchmakerRequestModel matchmakerRequest) {
        final var maxPlayers = config.getMaxPlayers();
        final var matchSize = getSize();
        if (matchSize >= maxPlayers) {
            return Optional.empty();
        }

        return greedyGroupsSortedSet.stream()
                .filter(greedyGroup -> greedyGroup.matchRequest(matchmakerRequest))
                .findFirst();
    }

    boolean checkReadiness() {
        final var minPlayers = config.getMinPlayers();
        final var matchSize = getSize();
        final var matchReadiness = matchSize >= minPlayers;
        final var groupsReadiness = greedyGroupIndexByName.values().stream()
                .allMatch(GreedyGroup::checkReadiness);
        return matchReadiness && groupsReadiness;
    }

    int getSize() {
        return matchmakerMatchAssignments.size();
    }

    Long getId() {
        return matchmakerMatch.getId();
    }
}
