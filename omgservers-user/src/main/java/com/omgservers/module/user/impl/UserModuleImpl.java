package com.omgservers.module.user.impl;

import com.omgservers.module.user.UserModule;
import com.omgservers.module.user.impl.service.clientService.ClientService;
import com.omgservers.module.user.impl.service.playerService.PlayerService;
import com.omgservers.module.user.impl.service.tokenService.TokenService;
import com.omgservers.module.user.impl.service.userService.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UserModuleImpl implements UserModule {

    final ClientService clientService;
    final PlayerService playerService;
    final TokenService tokenService;
    final UserService userService;

    public ClientService getClientService() {
        return clientService;
    }

    public PlayerService getPlayerService() {
        return playerService;
    }

    public TokenService getTokenService() {
        return tokenService;
    }

    public UserService getUserService() {
        return userService;
    }
}
