package com.omgservers.module.user.impl.service.playerService.impl;

import com.omgservers.module.user.impl.service.playerService.PlayerService;
import com.omgservers.module.user.impl.service.playerService.impl.method.getOrCreatePlayer.GetOrCreatePlayerHelpMethod;
import com.omgservers.module.user.impl.service.playerService.request.GetOrCreatePlayerHelpRequest;
import com.omgservers.module.user.impl.service.playerService.response.GetOrCreatePlayerHelpResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PlayerServiceImpl implements PlayerService {

    final GetOrCreatePlayerHelpMethod getOrCreatePlayerHelpMethod;

    @Override
    public Uni<GetOrCreatePlayerHelpResponse> getOrCreatePlayer(GetOrCreatePlayerHelpRequest request) {
        return getOrCreatePlayerHelpMethod.getOrCreatePlayer(request);
    }
}
