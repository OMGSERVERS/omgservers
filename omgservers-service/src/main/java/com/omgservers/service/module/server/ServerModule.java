package com.omgservers.service.module.server;

import com.omgservers.service.module.server.impl.service.serverService.ServerService;

public interface ServerModule {
    ServerService getServerService();
}
