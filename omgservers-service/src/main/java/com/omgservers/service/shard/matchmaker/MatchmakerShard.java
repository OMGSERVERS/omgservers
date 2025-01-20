package com.omgservers.service.shard.matchmaker;

import com.omgservers.service.shard.matchmaker.impl.service.matchmakerService.MatchmakerService;

public interface MatchmakerShard {

    MatchmakerService getService();
}
