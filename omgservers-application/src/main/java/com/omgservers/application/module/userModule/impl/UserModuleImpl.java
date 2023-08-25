package com.omgservers.application.module.userModule.impl;

import com.omgservers.application.module.userModule.UserModule;
import com.omgservers.application.module.userModule.impl.service.attributeInternalService.AttributeInternalService;
import com.omgservers.application.module.userModule.impl.service.clientInternalService.ClientInternalService;
import com.omgservers.application.module.userModule.impl.service.objectInternalService.ObjectInternalService;
import com.omgservers.application.module.userModule.impl.service.playerHelpService.PlayerHelpService;
import com.omgservers.application.module.userModule.impl.service.playerInternalService.PlayerInternalService;
import com.omgservers.application.module.userModule.impl.service.tokenInternalService.TokenInternalService;
import com.omgservers.application.module.userModule.impl.service.userHelpService.UserHelpService;
import com.omgservers.application.module.userModule.impl.service.userInternalService.UserInternalService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UserModuleImpl implements UserModule {

    final AttributeInternalService attributeInternalService;
    final ClientInternalService clientInternalService;
    final ObjectInternalService objectInternalService;
    final PlayerInternalService playerInternalService;
    final TokenInternalService tokenInternalService;
    final UserInternalService userInternalService;
    final PlayerHelpService playerHelpService;
    final UserHelpService userHelpService;

    @Override
    public AttributeInternalService getAttributeInternalService() {
        return attributeInternalService;
    }

    @Override
    public ClientInternalService getClientInternalService() {
        return clientInternalService;
    }

    @Override
    public ObjectInternalService getObjectInternalService() {
        return objectInternalService;
    }

    @Override
    public PlayerInternalService getPlayerInternalService() {
        return playerInternalService;
    }

    @Override
    public PlayerHelpService getPlayerHelpService() {
        return playerHelpService;
    }

    @Override
    public TokenInternalService getTokenInternalService() {
        return tokenInternalService;
    }

    @Override
    public UserInternalService getUserInternalService() {
        return userInternalService;
    }

    @Override
    public UserHelpService getUserHelpService() {
        return userHelpService;
    }
}
