package com.omgservers.service.module.matchmaker.impl;

import com.omgservers.service.module.matchmaker.MatchmakerModule;
import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.MatchmakerService;
import com.omgservers.service.module.matchmaker.impl.service.shortcutService.ShortcutService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class MatchmakerModuleImpl implements MatchmakerModule {

    final MatchmakerService matchmakerService;
    final ShortcutService shortcutService;

    public MatchmakerService getMatchmakerService() {
        return matchmakerService;
    }

    public ShortcutService getShortcutService() {
        return shortcutService;
    }
}
