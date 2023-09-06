package com.omgservers.module.user;

import com.omgservers.module.user.impl.service.attributeService.AttributeService;
import com.omgservers.module.user.impl.service.clientService.ClientService;
import com.omgservers.module.user.impl.service.objectService.ObjectService;
import com.omgservers.module.user.impl.service.playerService.PlayerService;
import com.omgservers.module.user.impl.service.tokenShardedService.TokenShardedService;
import com.omgservers.module.user.impl.service.userService.UserService;

public interface UserModule {

    AttributeService getAttributeService();

    TokenShardedService getTokenService();

    ClientService getClientService();

    ObjectService getObjectService();

    PlayerService getPlayerService();

    UserService getUserService();
}
