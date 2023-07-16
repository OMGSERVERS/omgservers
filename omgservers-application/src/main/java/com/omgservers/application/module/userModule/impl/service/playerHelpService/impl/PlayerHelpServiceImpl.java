package com.omgservers.application.module.userModule.impl.service.playerHelpService.impl;

import com.omgservers.application.module.userModule.impl.service.playerHelpService.PlayerHelpService;
import com.omgservers.application.module.userModule.impl.service.playerHelpService.impl.method.getOrCreatePlayerHelpMethod.GetOrCreatePlayerHelpMethod;
import com.omgservers.application.module.userModule.impl.service.playerHelpService.request.GetOrCreatePlayerHelpRequest;
import com.omgservers.application.module.userModule.impl.service.playerHelpService.response.GetOrCreatePlayerHelpResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PlayerHelpServiceImpl implements PlayerHelpService {

    final GetOrCreatePlayerHelpMethod getOrCreatePlayerHelpMethod;

    @Override
    public Uni<GetOrCreatePlayerHelpResponse> getOrCreatePlayer(GetOrCreatePlayerHelpRequest request) {
        return getOrCreatePlayerHelpMethod.getOrCreatePlayer(request);
    }
}
