package com.omgservers.service.shard.user.impl;

import com.omgservers.service.shard.user.UserShard;
import com.omgservers.service.shard.user.impl.service.userService.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UserShardImpl implements UserShard {

    final UserService userService;

    public UserService getService() {
        return userService;
    }
}
