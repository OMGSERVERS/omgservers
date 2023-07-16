package com.omgservers.application.module.userModule.impl.service.userHelpService.impl;

import com.omgservers.application.module.userModule.impl.service.userHelpService.UserHelpService;
import com.omgservers.application.module.userModule.impl.service.userHelpService.impl.method.respondClientHelpMethod.RespondClientHelpMethod;
import com.omgservers.application.module.userModule.impl.service.userHelpService.request.RespondClientHelpRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UserHelpServiceImpl implements UserHelpService {

    final RespondClientHelpMethod respondClientMethod;

    @Override
    public Uni<Void> respondClient(RespondClientHelpRequest request) {
        return respondClientMethod.respondClient(request);
    }
}
