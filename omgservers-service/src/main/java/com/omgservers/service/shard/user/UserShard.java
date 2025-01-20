package com.omgservers.service.shard.user;

import com.omgservers.service.shard.user.impl.service.userService.UserService;

public interface UserShard {

    UserService getService();
}
