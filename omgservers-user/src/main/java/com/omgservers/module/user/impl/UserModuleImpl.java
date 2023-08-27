package com.omgservers.module.user.impl;

import com.omgservers.module.user.UserModule;
import com.omgservers.module.user.impl.service.attributeShardedService.AttributeShardedService;
import com.omgservers.module.user.impl.service.clientShardedService.ClientShardedService;
import com.omgservers.module.user.impl.service.objectShardedService.ObjectShardedService;
import com.omgservers.module.user.impl.service.playerService.PlayerService;
import com.omgservers.module.user.impl.service.playerShardedService.PlayerShardedService;
import com.omgservers.module.user.impl.service.tokenShardedService.TokenShardedService;
import com.omgservers.module.user.impl.service.userInternalService.UserShardedService;
import com.omgservers.module.user.impl.service.userService.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UserModuleImpl implements UserModule {

    final AttributeShardedService attributeShardedService;
    final ClientShardedService clientShardedService;
    final ObjectShardedService objectShardedService;
    final PlayerShardedService playerShardedService;
    final TokenShardedService tokenShardedService;
    final UserShardedService userShardedService;
    final PlayerService playerService;
    final UserService userService;

    public AttributeShardedService getAttributeShardedService() {
        return attributeShardedService;
    }

    public ClientShardedService getClientShardedService() {
        return clientShardedService;
    }

    public ObjectShardedService getObjectShardedService() {
        return objectShardedService;
    }

    public PlayerShardedService getPlayerShardedService() {
        return playerShardedService;
    }

    public PlayerService getPlayerService() {
        return playerService;
    }

    public TokenShardedService getTokenShardedService() {
        return tokenShardedService;
    }

    public UserShardedService getUserShardedService() {
        return userShardedService;
    }

    public UserService getUserService() {
        return userService;
    }
}
