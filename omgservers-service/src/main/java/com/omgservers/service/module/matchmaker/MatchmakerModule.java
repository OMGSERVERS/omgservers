package com.omgservers.service.module.matchmaker;

import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.MatchmakerService;

public interface MatchmakerModule {

    MatchmakerService getService();
}
