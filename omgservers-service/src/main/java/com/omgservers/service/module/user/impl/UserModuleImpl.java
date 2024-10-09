package com.omgservers.service.module.user.impl;

import com.omgservers.service.module.user.UserModule;
import com.omgservers.service.module.user.impl.service.userService.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UserModuleImpl implements UserModule {

    final UserService userService;

    public UserService getService() {
        return userService;
    }
}
