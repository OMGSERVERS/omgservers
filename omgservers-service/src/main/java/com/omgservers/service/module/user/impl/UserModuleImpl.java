package com.omgservers.service.module.user.impl;

import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.module.user.impl.service.playerService.PlayerService;
import com.omgservers.service.module.user.impl.service.shortcutService.ShortcutService;
import com.omgservers.service.module.user.impl.service.tokenService.TokenService;
import com.omgservers.service.module.user.impl.service.userService.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UserModuleImpl implements UserModule {

    final ShortcutService shortcutService;
    final PlayerService playerService;
    final TokenService tokenService;
    final UserService userService;

    public PlayerService getPlayerService() {
        return playerService;
    }

    public TokenService getTokenService() {
        return tokenService;
    }

    public UserService getUserService() {
        return userService;
    }

    @Override
    public ShortcutService getShortcutService() {
        return shortcutService;
    }
}
