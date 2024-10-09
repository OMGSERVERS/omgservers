package com.omgservers.service.entrypoint.server.impl;

import com.omgservers.service.entrypoint.server.ServerEntrypoint;
import com.omgservers.service.entrypoint.server.impl.service.serverService.ServerService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ServerEntrypointImpl implements ServerEntrypoint {

    final ServerService serverService;

    @Override
    public ServerService getService() {
        return serverService;
    }
}
