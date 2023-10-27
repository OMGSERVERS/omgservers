package com.omgservers.module.user;

import com.omgservers.module.user.impl.service.clientService.ClientService;
import com.omgservers.module.user.impl.service.playerService.PlayerService;
import com.omgservers.module.user.impl.service.tokenService.TokenService;
import com.omgservers.module.user.impl.service.userService.UserService;

public interface UserModule {

    TokenService getTokenService();

    ClientService getClientService();

    PlayerService getPlayerService();

    UserService getUserService();
}
