package com.omgservers.service.module.user;

import com.omgservers.service.module.user.impl.service.clientService.ClientService;
import com.omgservers.service.module.user.impl.service.playerService.PlayerService;
import com.omgservers.service.module.user.impl.service.tokenService.TokenService;
import com.omgservers.service.module.user.impl.service.userService.UserService;

public interface UserModule {

    TokenService getTokenService();

    ClientService getClientService();

    PlayerService getPlayerService();

    UserService getUserService();
}
