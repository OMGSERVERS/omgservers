package com.omgservers.module.user;

import com.omgservers.module.user.impl.service.attributeShardedService.AttributeShardedService;
import com.omgservers.module.user.impl.service.clientShardedService.ClientShardedService;
import com.omgservers.module.user.impl.service.objectShardedService.ObjectShardedService;
import com.omgservers.module.user.impl.service.playerService.PlayerService;
import com.omgservers.module.user.impl.service.playerShardedService.PlayerShardedService;
import com.omgservers.module.user.impl.service.tokenShardedService.TokenShardedService;
import com.omgservers.module.user.impl.service.userInternalService.UserShardedService;
import com.omgservers.module.user.impl.service.userService.UserService;

public interface UserModule {

    AttributeShardedService getAttributeShardedService();

    ClientShardedService getClientShardedService();

    ObjectShardedService getObjectShardedService();

    PlayerShardedService getPlayerShardedService();

    PlayerService getPlayerService();

    TokenShardedService getTokenShardedService();

    UserShardedService getUserShardedService();

    UserService getUserService();
}
