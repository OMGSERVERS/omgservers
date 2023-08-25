package com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.impl;

import com.omgservers.model.match.MatchModel;
import com.omgservers.model.request.RequestModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class MatchmakerInMemoryCache {

    final Map<Long, RequestModel> requests;
    final Map<Long, MatchModel> matches;

    public MatchmakerInMemoryCache() {
        requests = new ConcurrentHashMap<>();
        matches = new ConcurrentHashMap<>();
    }

    public void addRequest(RequestModel request) {
        final var id = request.getId();
        requests.put(id, request);
    }

    public boolean removeRequest(Long id) {
        return requests.remove(id) != null;
    }

    public void addMatch(MatchModel match) {
        final var id = match.getId();
        matches.put(id, match);
    }

    public boolean removeMatch(Long id) {
        return matches.remove(id) != null;
    }

    public List<RequestModel> getRequests(Long matchmakerId) {
        return requests.values().stream()
                .filter(request -> request.getMatchmakerId().equals(matchmakerId))
                .toList();
    }

    public List<MatchModel> getMatches(Long matchmakerId) {
        return matches.values().stream()
                .filter(request -> request.getMatchmakerId().equals(matchmakerId))
                .toList();
    }
}
