package com.omgservers.service.module.user;

import com.omgservers.service.module.user.impl.service.userService.UserService;

public interface UserModule {

    UserService getService();
}
