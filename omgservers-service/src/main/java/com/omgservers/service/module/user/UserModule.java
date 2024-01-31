package com.omgservers.service.module.user;

import com.omgservers.service.module.user.impl.service.playerService.PlayerService;
import com.omgservers.service.module.user.impl.service.shortcutService.ShortcutService;
import com.omgservers.service.module.user.impl.service.tokenService.TokenService;
import com.omgservers.service.module.user.impl.service.userService.UserService;

public interface UserModule {

    TokenService getTokenService();

    PlayerService getPlayerService();

    UserService getUserService();

    ShortcutService getShortcutService();
}
