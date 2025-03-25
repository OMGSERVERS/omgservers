package com.omgservers.service.shard.match;

import com.omgservers.service.shard.match.impl.service.matchService.MatchService;

public interface MatchShard {

    MatchService getService();
}
