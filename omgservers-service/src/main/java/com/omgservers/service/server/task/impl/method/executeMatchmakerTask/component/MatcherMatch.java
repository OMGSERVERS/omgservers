package com.omgservers.service.server.task.impl.method.executeMatchmakerTask.component;

import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceModel;
import com.omgservers.schema.model.matchmakerMatchResource.MatchmakerMatchResourceStatusEnum;
import com.omgservers.schema.model.matchmakerMatchAssignment.MatchmakerMatchAssignmentModel;
import com.omgservers.schema.model.matchmakerRequest.MatchmakerRequestModel;
import com.omgservers.schema.model.tenantVersion.TenantVersionModeDto;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class MatcherMatch {

    @Getter
    final MatchmakerMatchResourceModel matchmakerMatchResource;
    final TenantVersionModeDto config;

    final List<MatchmakerMatchAssignmentModel> matchmakerMatchAssignments;
    final List<MatchmakerMatchAssignmentModel> newlyCreatedAssignments;

    final TreeSet<MatcherGroup> groupsSortedSet;
    final Map<String, MatcherGroup> groupByName;

    boolean completed;

    public MatcherMatch(final TenantVersionModeDto config,
                        final MatchmakerMatchResourceModel matchmakerMatchResource) {
        this.config = config;
        this.matchmakerMatchResource = matchmakerMatchResource;

        matchmakerMatchAssignments = new ArrayList<>();
        newlyCreatedAssignments = new ArrayList<>();

        groupsSortedSet = new TreeSet<>(Comparator.comparingInt(MatcherGroup::getSize)
                .thenComparing(MatcherGroup::getName));

        groupByName = new HashMap<>();
        config.getGroups().forEach(groupConfig -> {
            final var groupName = groupConfig.getName();
            final var matcherGroup = new MatcherGroup(groupConfig);
            groupsSortedSet.add(matcherGroup);
            groupByName.put(groupName, matcherGroup);
        });

        completed = false;
    }

    void addAssignment(final MatchmakerMatchAssignmentModel matchmakerMatchAssignment,
                       final boolean newlyCreated) {
        final var groupName = matchmakerMatchAssignment.getGroupName();
        final var matcherGroup = groupByName.get(groupName);
        if (Objects.nonNull(matcherGroup)) {
            matcherGroup.addAssignment(matchmakerMatchAssignment);
            matchmakerMatchAssignments.add(matchmakerMatchAssignment);
            if (newlyCreated) {
                newlyCreatedAssignments.add(matchmakerMatchAssignment);
            }
        } else {
            log.error("Failed to add assignment, group \"{}\" was not found", groupName);
        }
    }

    Optional<MatcherGroup> matchRequest(final MatchmakerRequestModel matchmakerRequest) {
        final var maxPlayers = config.getMaxPlayers();
        final var matchSize = getSize();
        if (matchSize >= maxPlayers) {
            return Optional.empty();
        }

        return groupsSortedSet.stream()
                .filter(matcherGroup -> matcherGroup.matchRequest(matchmakerRequest))
                .findFirst();
    }

    boolean checkReadiness() {
        if (!completed) {
            final var minPlayers = config.getMinPlayers();
            final var matchSize = getSize();
            final var matchReadiness = matchSize >= minPlayers;
            final var groupsReadiness = groupByName.values().stream()
                    .allMatch(MatcherGroup::checkReadiness);
            completed = matchReadiness && groupsReadiness;
        }

        return completed;
    }

    int getSize() {
        return matchmakerMatchAssignments.size();
    }

    Long getMatchId() {
        return matchmakerMatchResource.getMatchId();
    }

    List<MatchmakerMatchAssignmentModel> getMatchmakerMatchAssignmentToSync() {
        // Sync assignments only for matches that are already fully created
        if (matchmakerMatchResource.getStatus().equals(MatchmakerMatchResourceStatusEnum.CREATED)) {
            return newlyCreatedAssignments;
        } else {
            return List.of();
        }
    }
}
