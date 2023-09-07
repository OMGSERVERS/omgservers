package com.omgservers.module.matchmaker;

import com.omgservers.module.matchmaker.impl.service.matchmakerService.MatchmakerService;

public interface MatchmakerModule {

    MatchmakerService getMatchmakerService();
}
