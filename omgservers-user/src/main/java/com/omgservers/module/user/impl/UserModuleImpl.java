package com.omgservers.module.user.impl;

import com.omgservers.module.user.UserModule;
import com.omgservers.module.user.impl.service.attributeService.AttributeService;
import com.omgservers.module.user.impl.service.clientService.ClientService;
import com.omgservers.module.user.impl.service.objectService.ObjectService;
import com.omgservers.module.user.impl.service.playerService.PlayerService;
import com.omgservers.module.user.impl.service.tokenShardedService.TokenShardedService;
import com.omgservers.module.user.impl.service.userService.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UserModuleImpl implements UserModule {

    final TokenShardedService tokenShardedService;
    final AttributeService attributeService;
    final ClientService clientService;
    final ObjectService objectService;
    final PlayerService playerService;
    final UserService userService;

    public AttributeService getAttributeService() {
        return attributeService;
    }

    public ClientService getClientService() {
        return clientService;
    }

    public ObjectService getObjectService() {
        return objectService;
    }

    public PlayerService getPlayerService() {
        return playerService;
    }

    public TokenShardedService getTokenService() {
        return tokenShardedService;
    }

    public UserService getUserService() {
        return userService;
    }
}
