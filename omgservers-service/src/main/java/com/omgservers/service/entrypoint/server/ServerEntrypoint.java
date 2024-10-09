package com.omgservers.service.entrypoint.server;

import com.omgservers.service.entrypoint.server.impl.service.serverService.ServerService;

public interface ServerEntrypoint {

    ServerService getService();
}