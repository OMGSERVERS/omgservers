package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl;

import com.omgservers.application.module.matchmakerModule.model.match.MatchModel;
import com.omgservers.application.module.matchmakerModule.model.request.RequestModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerInMemoryCache {

    final Map<UUID, RequestModel> requests;
    final Map<UUID, MatchModel> matches;

    public MatchmakerInMemoryCache() {
        requests = new ConcurrentHashMap<>();
        matches = new ConcurrentHashMap<>();
    }

    public void addRequest(RequestModel request) {
        final var uuid = request.getUuid();
        requests.put(uuid, request);
    }

    public boolean removeRequest(UUID uuid) {
        return requests.remove(uuid) != null;
    }

    public void addMatch(MatchModel match) {
        final var uuid = match.getUuid();
        matches.put(uuid, match);
    }

    public boolean removeMatch(UUID uuid) {
        return matches.remove(uuid) != null;
    }

    public List<RequestModel> getRequests(UUID matchmaker) {
        return requests.values().stream()
                .filter(request -> request.getMatchmaker().equals(matchmaker))
                .toList();
    }

    public List<MatchModel> getMatches(UUID matchmaker) {
        return matches.values().stream()
                .filter(request -> request.getMatchmaker().equals(matchmaker))
                .toList();
    }
}
