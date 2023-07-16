package com.omgservers.application.module.matchmakerModule.impl;

import com.omgservers.application.module.matchmakerModule.MatchmakerModule;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.MatchmakerInternalService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class MatchmakerModuleImpl implements MatchmakerModule {

    final MatchmakerInternalService matchmakerInternalService;

    @Override
    public MatchmakerInternalService getMatchmakerInternalService() {
        return matchmakerInternalService;
    }
}
