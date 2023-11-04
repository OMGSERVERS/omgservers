package com.omgservers.module.matchmaker.impl;

import com.omgservers.module.matchmaker.MatchmakerModule;
import com.omgservers.module.matchmaker.impl.service.matchmakerService.MatchmakerService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class MatchmakerModuleImpl implements MatchmakerModule {

    final MatchmakerService matchmakerService;

    public MatchmakerService getMatchmakerService() {
        return matchmakerService;
    }
}
