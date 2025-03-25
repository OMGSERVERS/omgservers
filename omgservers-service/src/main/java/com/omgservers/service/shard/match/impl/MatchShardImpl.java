package com.omgservers.service.shard.match.impl;

import com.omgservers.service.shard.match.MatchShard;
import com.omgservers.service.shard.match.impl.service.matchService.MatchService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class MatchShardImpl implements MatchShard {

    final MatchService matchService;

    public MatchService getService() {
        return matchService;
    }
}
