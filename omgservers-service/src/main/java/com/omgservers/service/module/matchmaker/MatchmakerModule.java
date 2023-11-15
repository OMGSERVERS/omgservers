package com.omgservers.service.module.matchmaker;

import com.omgservers.service.module.matchmaker.impl.service.matchmakerService.MatchmakerService;
import com.omgservers.service.module.matchmaker.impl.service.shortcutService.ShortcutService;

public interface MatchmakerModule {

    MatchmakerService getMatchmakerService();

    ShortcutService getShortcutService();
}
