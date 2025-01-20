package com.omgservers.service.shard.matchmaker.impl;

import com.omgservers.service.shard.matchmaker.MatchmakerShard;
import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.MatchmakerService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class MatchmakerShardImpl implements MatchmakerShard {

    final MatchmakerService matchmakerService;

    public MatchmakerService getService() {
        return matchmakerService;
    }

}
