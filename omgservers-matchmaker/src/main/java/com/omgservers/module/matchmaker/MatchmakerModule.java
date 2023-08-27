package com.omgservers.module.matchmaker;

import com.omgservers.module.matchmaker.impl.service.matchmakerShardedService.MatchmakerShardedService;

public interface MatchmakerModule {

    MatchmakerShardedService getMatchmakerShardedService();
}
