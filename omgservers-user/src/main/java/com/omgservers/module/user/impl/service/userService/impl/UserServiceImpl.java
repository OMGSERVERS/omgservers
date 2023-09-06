package com.omgservers.module.user.impl.service.userService.impl;

import com.omgservers.module.user.impl.service.userService.UserService;
import com.omgservers.module.user.impl.service.userService.impl.method.respondClient.RespondClientMethod;
import com.omgservers.dto.user.RespondClientRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UserServiceImpl implements UserService {

    final RespondClientMethod respondClientMethod;

    @Override
    public Uni<Void> respondClient(RespondClientRequest request) {
        return respondClientMethod.respondClient(request);
    }
}
